
>学习了Rxjava、Retrofit，做一个小demo实践下，使用了和风天气API

## 效果
![img](/gee/geeweather-sketch.gif)
	
<!-- more -->

## 实现技术
+ RxJava
+ Retrofit
+ GreenDao
+ Glide
+ EventBus
+ 高德定位
    

## GreenDao使用
>因为和风天气提供的接口需要地理位置的编号作为参数，但是高德定位返回的结果并没有地理位置编号
所以需要本地数据库存储所有的地理位置编号，利用高德定位的位置查询出编号
项目选择GreenDao简化开发流程

1. 在 .src/main 目录下新建一个与 java 同层级的「java-gen」目录，用于存放由 greenDAO 生成的 Bean、DAO、DaoMaster、DaoSession 等类。
    
2. 配置 Android 工程（app）的 build.gradle，添加 sourceSets，dependencies就不用说了
```gradle
sourceSets {
        main {
            java.srcDirs = ['src/main/java', 'src/main/java-gen']
        }
    }
```
3. 新建「greenDAO Generator」模块 (Java 工程）
通过 File -> New -> New Module -> Java Library -> 填写相应的包名与类名 -> Finish
       
4. 生成 DAO 文件（数据库）
执行 generator 工程，你将会在控制台看到如下日志，并且在主工程「java-gen」下会发现生成了DaoMaster、DaoSession、NoteDao、Note共4个类文件
    
5. 使用GreenDao的查询功能

- 初始化

```java
private void setupDatabase() {
    // 通过 DaoMaster 的内部类 DevOpenHelper，可以得到一个便利的 SQLiteOpenHelper 对象。
    // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
    // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
    // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
    helper = new DaoMaster.DevOpenHelper(this, Constants.DB_NAME, null);
    db = helper.getWritableDatabase();
    // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
    daoMaster = new DaoMaster(db);
    daoSession = daoMaster.newSession();
}
```

- 获取DAO
    
```java
private CITYDao getCityInfoDao() {
    // 通过 BaseApplication 类提供的 getDaoSession() 获取具体 Dao
    return daoSession.getCITYDao();
}
``` 

- 通过高德定位获取的信息查询数据库
    
```java
private CITY search(String city_province, String city_town, String city_area) {

    if (city_province != null) {
        city_province = city_province.replace("市", "")
                .replace("省", "")
                .replace("自治区", "")
                .replace("特别行政区", "")
                .replace("地区", "")
                .replace("区", "")
                .replace("盟", "");
    }
    if (city_town != null) {
        city_town = city_town.replace("市", "")
                .replace("省", "")
                .replace("自治区", "")
                .replace("特别行政区", "")
                .replace("地区", "")
                .replace("区", "")
                .replace("盟", "");
    }
    if (city_area != null) {
        city_area = city_area.replace("市", "")
                .replace("省", "")
                .replace("自治区", "")
                .replace("特别行政区", "")
                .replace("地区", "")
                .replace("区", "")
                .replace("盟", "");
    }
    //在 QueryBuilder 类中内置两个 Flag 用于方便输出执行的 SQL 语句与传递参数的值
    QueryBuilder.LOG_SQL = true;
    QueryBuilder.LOG_VALUES = true;

    return  getCityInfoDao().queryBuilder().where(CITYDao.Properties.City_town.eq(city_town)).unique();
}
```  

     
     
## Retrofit & Gson & RxJava实现网络请求，数据解析


- 接口

```java
public interface WeatherApi {

    @GET("weather")
    rx.Observable<WeatherInfo> queryWeather(@Query("city") String city, @Query("key") String key);

    @GET("http://api.fir.im/apps/latest/和风天气key")
    rx.Observable<VersionAPI> mVersionAPI(
            @Query("api_token") String api_token);
}
```

- 数据转换
    
```java
public class WeatherInfo2Weather implements Func1<WeatherInfo,Weather> {

    public static WeatherInfo2Weather newInstance(){
        return new WeatherInfo2Weather();
    }
    @Override
    public Weather call(WeatherInfo weatherInfo) {

        return weatherInfo.mHeWeatherDataService30.get(0);
    }
}
```


- 网络请求
 
    
```java
private Observer<? super Weather> getObserver() {

        if (null == observer) {
            observer = new Observer<Weather>() {
                @Override
                public void onCompleted() {

                    mFlyLayout.onRefreshFinish();

                    if (SomeUtils.isNetworkConnected(MainActivity.this)) {

                        Snackbar.make(mRecyclerview, "加载完毕，(*^▽^*)", Snackbar.LENGTH_SHORT).show();
                    } else {
                        Snackbar.make(mRecyclerview, "网络异常，(ಥ _ ಥ)", Snackbar.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                }

                @Override
                public void onError(Throwable e) {
                    mFlyLayout.onRefreshFinish();

                    Snackbar.make(mRecyclerview, "网络异常，(ಥ _ ಥ)", Snackbar.LENGTH_SHORT).show();
                    LogUtils.LOGE(TAG, e.getMessage());

                    isLoading = false;
                }

                @Override
                public void onNext(Weather weather) {
                    if (null == mAdapter) {
                        mAdapter = new WeatherAdapter();
                    }
                    mRecyclerview.setAdapter(mAdapter);
                    mAdapter.setData(weather);
                }
            };
        }
        return observer;
    }
```


## EventBus使用

定位位置改变后查询位置编码，发送事件，重新请求网络

```java
@Override
 public void onLocationChanged(AMapLocation aMapLocation) {
     if (aMapLocation != null) {
         if (aMapLocation.getErrorCode() == 0) {
             //定位成功回调信息，设置相关消息
             if (StringUtils.hasMeaningful(aMapLocation.getDistrict())) {
                 SPUtils.setCityInfo(aMapLocation.getCity(), aMapLocation.getDistrict());

                 if (!StringUtils.hasMeaningful(SPUtils.getDistrict())) {
                     search(aMapLocation.getProvince(), aMapLocation.getCity(), aMapLocation.getDistrict());
                     EventBus.getDefault().post(new MessageEvent());
                 }
             }

         } else {
             //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
             Log.e("AmapError", "location Error, ErrCode:"
                     + aMapLocation.getErrorCode() + ", errInfo:"
                     + aMapLocation.getErrorInfo());
         }
     }
 }
```