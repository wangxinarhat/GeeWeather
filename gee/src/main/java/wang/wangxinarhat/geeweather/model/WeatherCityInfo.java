package wang.wangxinarhat.geeweather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by wang on 2016/4/6.
 */
//    HeWeather data service 3.0
public class WeatherCityInfo {


    /**
     * aqi : {"city":{"aqi":"253","co":"2","no2":"50","o3":"113","pm10":"232","pm25":"202","qlty":"重度污染","so2":"26"}}
     * basic : {"city":"北京","cnty":"中国","id":"CN101010100","lat":"39.904000","lon":"116.391000","update":{"loc":"2016-04-06 17:52","utc":"2016-04-06 09:52"}}
     * daily_forecast : [{"astro":{"sr":"05:49","ss":"18:43"},"cond":{"code_d":"300","code_n":"300","txt_d":"阵雨","txt_n":"阵雨"},"date":"2016-04-06","hum":"34","pcpn":"0.0","pop":"0","pres":"1007","tmp":{"max":"22","min":"12"},"vis":"10","wind":{"deg":"203","dir":"无持续风向","sc":"微风","spd":"2"}},{"astro":{"sr":"05:48","ss":"18:44"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-04-07","hum":"10","pcpn":"0.0","pop":"0","pres":"1017","tmp":{"max":"24","min":"8"},"vis":"10","wind":{"deg":"325","dir":"北风","sc":"3-4","spd":"16"}},{"astro":{"sr":"05:46","ss":"18:45"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-04-08","hum":"7","pcpn":"0.0","pop":"0","pres":"1014","tmp":{"max":"25","min":"11"},"vis":"10","wind":{"deg":"258","dir":"南风","sc":"3-4","spd":"15"}},{"astro":{"sr":"05:45","ss":"18:46"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-04-09","hum":"6","pcpn":"0.0","pop":"0","pres":"1008","tmp":{"max":"26","min":"10"},"vis":"10","wind":{"deg":"301","dir":"无持续风向","sc":"微风","spd":"4"}},{"astro":{"sr":"05:43","ss":"18:47"},"cond":{"code_d":"100","code_n":"100","txt_d":"晴","txt_n":"晴"},"date":"2016-04-10","hum":"15","pcpn":"0.0","pop":"0","pres":"1017","tmp":{"max":"22","min":"9"},"vis":"10","wind":{"deg":"155","dir":"无持续风向","sc":"微风","spd":"0"}},{"astro":{"sr":"05:42","ss":"18:48"},"cond":{"code_d":"100","code_n":"104","txt_d":"晴","txt_n":"阴"},"date":"2016-04-11","hum":"26","pcpn":"1.8","pop":"51","pres":"1010","tmp":{"max":"25","min":"12"},"vis":"10","wind":{"deg":"198","dir":"南风","sc":"3-4","spd":"13"}},{"astro":{"sr":"05:40","ss":"18:49"},"cond":{"code_d":"104","code_n":"104","txt_d":"阴","txt_n":"阴"},"date":"2016-04-12","hum":"22","pcpn":"0.0","pop":"5","pres":"1006","tmp":{"max":"23","min":"11"},"vis":"10","wind":{"deg":"66","dir":"无持续风向","sc":"微风","spd":"3"}}]
     * hourly_forecast : [{"date":"2016-04-06 19:00","hum":"29","pop":"0","pres":"1005","tmp":"27","wind":{"deg":"233","dir":"西南风","sc":"微风","spd":"13"}},{"date":"2016-04-06 22:00","hum":"25","pop":"0","pres":"1007","tmp":"24","wind":{"deg":"274","dir":"西风","sc":"微风","spd":"16"}}]
     * now : {"cond":{"code":"300","txt":"阵雨"},"fl":"19","hum":"60","pcpn":"0","pres":"1004","tmp":"19","vis":"4","wind":{"deg":"190","dir":"西南风","sc":"4-5","spd":"17"}}
     * status : ok
     * suggestion : {"comf":{"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"},"cw":{"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"},"drsg":{"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"},"flu":{"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"},"sport":{"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。"},"trav":{"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"},"uv":{"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}}
     */

    @SerializedName("HeWeather data service 3.0")
    public List<HeWeatherdataservice30Entity> HeWeatherdataservice30;

    public static class HeWeatherdataservice30Entity {
        /**
         * city : {"aqi":"253","co":"2","no2":"50","o3":"113","pm10":"232","pm25":"202","qlty":"重度污染","so2":"26"}
         */

        @SerializedName("aqi")
        public AqiEntity aqi;
        /**
         * city : 北京
         * cnty : 中国
         * id : CN101010100
         * lat : 39.904000
         * lon : 116.391000
         * update : {"loc":"2016-04-06 17:52","utc":"2016-04-06 09:52"}
         */

        @SerializedName("basic")
        public BasicEntity basic;
        /**
         * cond : {"code":"300","txt":"阵雨"}
         * fl : 19
         * hum : 60
         * pcpn : 0
         * pres : 1004
         * tmp : 19
         * vis : 4
         * wind : {"deg":"190","dir":"西南风","sc":"4-5","spd":"17"}
         */

        @SerializedName("now")
        public NowEntity now;
        @SerializedName("status")
        public String status;
        /**
         * comf : {"brf":"舒适","txt":"白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。"}
         * cw : {"brf":"不宜","txt":"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"}
         * drsg : {"brf":"较舒适","txt":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。"}
         * flu : {"brf":"较易发","txt":"天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。"}
         * sport : {"brf":"较不宜","txt":"有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。"}
         * trav : {"brf":"适宜","txt":"有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"}
         * uv : {"brf":"弱","txt":"紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"}
         */

        @SerializedName("suggestion")
        public SuggestionEntity suggestion;
        /**
         * astro : {"sr":"05:49","ss":"18:43"}
         * cond : {"code_d":"300","code_n":"300","txt_d":"阵雨","txt_n":"阵雨"}
         * date : 2016-04-06
         * hum : 34
         * pcpn : 0.0
         * pop : 0
         * pres : 1007
         * tmp : {"max":"22","min":"12"}
         * vis : 10
         * wind : {"deg":"203","dir":"无持续风向","sc":"微风","spd":"2"}
         */

        @SerializedName("daily_forecast")
        public List<DailyForecastEntity> dailyForecast;
        /**
         * date : 2016-04-06 19:00
         * hum : 29
         * pop : 0
         * pres : 1005
         * tmp : 27
         * wind : {"deg":"233","dir":"西南风","sc":"微风","spd":"13"}
         */

        @SerializedName("hourly_forecast")
        public List<HourlyForecastEntity> hourlyForecast;

        public static class AqiEntity {
            /**
             * aqi : 253
             * co : 2
             * no2 : 50
             * o3 : 113
             * pm10 : 232
             * pm25 : 202
             * qlty : 重度污染
             * so2 : 26
             */

            @SerializedName("city")
            public CityEntity city;

            public static class CityEntity {
                @SerializedName("aqi")
                public String aqi;
                @SerializedName("co")
                public String co;
                @SerializedName("no2")
                public String no2;
                @SerializedName("o3")
                public String o3;
                @SerializedName("pm10")
                public String pm10;
                @SerializedName("pm25")
                public String pm25;
                @SerializedName("qlty")
                public String qlty;
                @SerializedName("so2")
                public String so2;
            }
        }

        public static class BasicEntity {
            @SerializedName("city")
            public String city;
            @SerializedName("cnty")
            public String cnty;
            @SerializedName("id")
            public String id;
            @SerializedName("lat")
            public String lat;
            @SerializedName("lon")
            public String lon;
            /**
             * loc : 2016-04-06 17:52
             * utc : 2016-04-06 09:52
             */

            @SerializedName("update")
            public UpdateEntity update;

            public static class UpdateEntity {
                @SerializedName("loc")
                public String loc;
                @SerializedName("utc")
                public String utc;
            }
        }

        public static class NowEntity {
            /**
             * code : 300
             * txt : 阵雨
             */

            @SerializedName("cond")
            public CondEntity cond;
            @SerializedName("fl")
            public String fl;
            @SerializedName("hum")
            public String hum;
            @SerializedName("pcpn")
            public String pcpn;
            @SerializedName("pres")
            public String pres;
            @SerializedName("tmp")
            public String tmp;
            @SerializedName("vis")
            public String vis;
            /**
             * deg : 190
             * dir : 西南风
             * sc : 4-5
             * spd : 17
             */

            @SerializedName("wind")
            public WindEntity wind;

            public static class CondEntity {
                @SerializedName("code")
                public String code;
                @SerializedName("txt")
                public String txt;
            }

            public static class WindEntity {
                @SerializedName("deg")
                public String deg;
                @SerializedName("dir")
                public String dir;
                @SerializedName("sc")
                public String sc;
                @SerializedName("spd")
                public String spd;
            }
        }

        public static class SuggestionEntity {
            /**
             * brf : 舒适
             * txt : 白天不太热也不太冷，风力不大，相信您在这样的天气条件下，应会感到比较清爽和舒适。
             */

            @SerializedName("comf")
            public ComfEntity comf;
            /**
             * brf : 不宜
             * txt : 不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。
             */

            @SerializedName("cw")
            public CwEntity cw;
            /**
             * brf : 较舒适
             * txt : 建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。
             */

            @SerializedName("drsg")
            public DrsgEntity drsg;
            /**
             * brf : 较易发
             * txt : 天气转凉，空气湿度较大，较易发生感冒，体质较弱的朋友请注意适当防护。
             */

            @SerializedName("flu")
            public FluEntity flu;
            /**
             * brf : 较不宜
             * txt : 有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意保暖并携带雨具。
             */

            @SerializedName("sport")
            public SportEntity sport;
            /**
             * brf : 适宜
             * txt : 有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。
             */

            @SerializedName("trav")
            public TravEntity trav;
            /**
             * brf : 弱
             * txt : 紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。
             */

            @SerializedName("uv")
            public UvEntity uv;

            public static class ComfEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class CwEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class DrsgEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class FluEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class SportEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class TravEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }

            public static class UvEntity {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;
            }
        }

        public static class DailyForecastEntity {
            /**
             * sr : 05:49
             * ss : 18:43
             */

            @SerializedName("astro")
            public AstroEntity astro;
            /**
             * code_d : 300
             * code_n : 300
             * txt_d : 阵雨
             * txt_n : 阵雨
             */

            @SerializedName("cond")
            public CondEntity cond;
            @SerializedName("date")
            public String date;
            @SerializedName("hum")
            public String hum;
            @SerializedName("pcpn")
            public String pcpn;
            @SerializedName("pop")
            public String pop;
            @SerializedName("pres")
            public String pres;
            /**
             * max : 22
             * min : 12
             */

            @SerializedName("tmp")
            public TmpEntity tmp;
            @SerializedName("vis")
            public String vis;
            /**
             * deg : 203
             * dir : 无持续风向
             * sc : 微风
             * spd : 2
             */

            @SerializedName("wind")
            public WindEntity wind;

            public static class AstroEntity {
                @SerializedName("sr")
                public String sr;
                @SerializedName("ss")
                public String ss;
            }

            public static class CondEntity {
                @SerializedName("code_d")
                public String codeD;
                @SerializedName("code_n")
                public String codeN;
                @SerializedName("txt_d")
                public String txtD;
                @SerializedName("txt_n")
                public String txtN;
            }

            public static class TmpEntity {
                @SerializedName("max")
                public String max;
                @SerializedName("min")
                public String min;
            }

            public static class WindEntity {
                @SerializedName("deg")
                public String deg;
                @SerializedName("dir")
                public String dir;
                @SerializedName("sc")
                public String sc;
                @SerializedName("spd")
                public String spd;
            }
        }

        public static class HourlyForecastEntity {
            @SerializedName("date")
            public String date;
            @SerializedName("hum")
            public String hum;
            @SerializedName("pop")
            public String pop;
            @SerializedName("pres")
            public String pres;
            @SerializedName("tmp")
            public String tmp;
            /**
             * deg : 233
             * dir : 西南风
             * sc : 微风
             * spd : 13
             */

            @SerializedName("wind")
            public WindEntity wind;

            public static class WindEntity {
                @SerializedName("deg")
                public String deg;
                @SerializedName("dir")
                public String dir;
                @SerializedName("sc")
                public String sc;
                @SerializedName("spd")
                public String spd;
            }
        }
    }
}
