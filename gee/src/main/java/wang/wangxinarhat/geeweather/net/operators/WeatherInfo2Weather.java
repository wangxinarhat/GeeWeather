package wang.wangxinarhat.geeweather.net.operators;

import rx.functions.Func1;
import wang.wangxinarhat.geeweather.model.Weather;
import wang.wangxinarhat.geeweather.model.WeatherInfo;

/**
 * Created by wang on 2016/4/7.
 */
public class WeatherInfo2Weather implements Func1<WeatherInfo,Weather> {

    public static WeatherInfo2Weather newInstance(){
        return new WeatherInfo2Weather();
    }
    @Override
    public Weather call(WeatherInfo weatherInfo) {

        return weatherInfo.mHeWeatherDataService30.get(0);
    }
}
