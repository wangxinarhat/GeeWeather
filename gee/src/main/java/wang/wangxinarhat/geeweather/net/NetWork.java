package wang.wangxinarhat.geeweather.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.Executors;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import wang.wangxinarhat.geeweather.net.api.WeatherApi;

/**
 * Created by wang on 2016/4/7.
 */
public class NetWork {

    public static String HOST = "https://api.heweather.com/x3/";

    private static WeatherApi weatherApi;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    public static WeatherApi getWeatherApi() {


        if (weatherApi == null) {
            Gson gson = new GsonBuilder().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(HOST)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .callbackExecutor(Executors.newCachedThreadPool())
                    .build();
            weatherApi = retrofit.create(WeatherApi.class);
        }
        return weatherApi;
    }


}
