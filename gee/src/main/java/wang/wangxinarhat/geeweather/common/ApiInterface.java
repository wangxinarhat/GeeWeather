package wang.wangxinarhat.geeweather.common;


import retrofit.http.GET;
import retrofit.http.Query;
import wang.wangxinarhat.geeweather.model.VersionAPI;
import wang.wangxinarhat.geeweather.model.WeatherInfo;

/**
 * Created by hugo on 2016/2/16 0016.
 */
public interface ApiInterface {



    @GET("weather")
    rx.Observable<WeatherInfo> mWeatherAPI(@Query("city") String city, @Query("key") String key);

    //而且在Retrofit 2.0中我们还可以在@Url里面定义完整的URL：这种情况下Base URL会被忽略。
    @GET("http://api.fir.im/apps/latest/5630e5f1f2fc425c52000006")
    rx.Observable<VersionAPI> mVersionAPI(
            @Query("api_token") String api_token);
}
