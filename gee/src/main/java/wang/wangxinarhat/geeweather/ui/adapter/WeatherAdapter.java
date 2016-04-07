package wang.wangxinarhat.geeweather.ui.adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import wang.wangxinarhat.geeweather.R;
import wang.wangxinarhat.geeweather.common.PLog;
import wang.wangxinarhat.geeweather.model.Setting;
import wang.wangxinarhat.geeweather.model.Weather;

/**
 * Created by hugo on 2016/1/31 0031.
 */
public class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String TAG = WeatherAdapter.class.getSimpleName();

    //    private Context mContext;
    private final int TYPE_WEATHER_NOW = 0;
    private final int TYPE_WEATHER_HOURS = 1;
    private final int TYPE_WEATHER_SUGGESTION = 2;
    private final int TYPE_WEATHER_HISTORY = 3;

    private Weather mWeatherData;
    private Setting mSetting;


    public void setData(Weather data) {
        this.mWeatherData = data;
        notifyDataSetChanged();
    }

    public WeatherAdapter() {
        mSetting = Setting.getInstance();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == TYPE_WEATHER_NOW) {
            return TYPE_WEATHER_NOW;
        }
        if (position == TYPE_WEATHER_HOURS) {
            return TYPE_WEATHER_HOURS;
        }
        if (position == TYPE_WEATHER_SUGGESTION) {
            return TYPE_WEATHER_SUGGESTION;
        }
        if (position == TYPE_WEATHER_HISTORY) {
            return TYPE_WEATHER_HISTORY;
        }
        return super.getItemViewType(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_WEATHER_NOW) {
            return new NowWeatherViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temperature, parent, false));
        }
        if (viewType == TYPE_WEATHER_HOURS) {
            return new HoursWeatherViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour_info, parent, false));
        }
        if (viewType == TYPE_WEATHER_SUGGESTION) {
            return new SuggestionViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false));
        }
        if (viewType == TYPE_WEATHER_HISTORY) {
            return new ForecastViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false));
        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int ItemViewType = getItemViewType(position);

        if (ItemViewType == TYPE_WEATHER_NOW) {
            try {
                ((NowWeatherViewHolder) holder).tempFlu.setText(mWeatherData.now.tmp + "℃");
                ((NowWeatherViewHolder) holder).tempMax.setText("↑ " + mWeatherData.dailyForecast.get(0).tmp.max + "°");
                ((NowWeatherViewHolder) holder).tempMin.setText("↓ " + mWeatherData.dailyForecast.get(0).tmp.min + "°");
                if (mWeatherData.aqi != null) {
                    ((NowWeatherViewHolder) holder).tempPm.setText("PM25： " + mWeatherData.aqi.city.pm25);
                    ((NowWeatherViewHolder) holder).tempQuality.setText("空气质量： " + mWeatherData.aqi.city.qlty);
                }
                Glide.with(holder.itemView.getContext())
                        .load(mSetting.getInt(mWeatherData.now.cond.txt, R.mipmap.none))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(((NowWeatherViewHolder) holder).weatherIcon);
            } catch (Exception e) {
                Snackbar.make(holder.itemView, R.string.network_error, Snackbar.LENGTH_SHORT).show();

                PLog.e(TAG, e.toString() + "                 TYPE_WEATHER_NOW         ");
            }

        }
        if (ItemViewType == TYPE_WEATHER_HOURS) {
            try {
                for (int i = 0; i < mWeatherData.hourlyForecast.size(); i++) {
                    //s.subString(s.length-3,s.length);
                    //第一个参数是开始截取的位置，第二个是结束位置。
                    String mDate = mWeatherData.hourlyForecast.get(i).date;
                    ((HoursWeatherViewHolder) holder).mClock[i].setText(
                            mDate.substring(mDate.length() - 5, mDate.length()));
                    ((HoursWeatherViewHolder) holder).mTemp[i].setText(mWeatherData.hourlyForecast.get(i).tmp + "°");
                    ((HoursWeatherViewHolder) holder).mHumidity[i].setText(
                            mWeatherData.hourlyForecast.get(i).hum + "%");
                    ((HoursWeatherViewHolder) holder).mWind[i].setText(
                            mWeatherData.hourlyForecast.get(i).wind.spd + "Km");
                }
            } catch (Exception e) {
                Snackbar.make(holder.itemView, R.string.network_error, Snackbar.LENGTH_SHORT).show();

                PLog.e(TAG, e.toString() + "                 TYPE_WEATHER_HOURS         ");
            }
        }
        if (ItemViewType == TYPE_WEATHER_SUGGESTION) {
            try {

                ((SuggestionViewHolder) holder).clothBrief.setText("穿衣指数---" + mWeatherData.suggestion.drsg.brf);
                ((SuggestionViewHolder) holder).clothTxt.setText(mWeatherData.suggestion.drsg.txt);

                ((SuggestionViewHolder) holder).sportBrief.setText("运动指数---" + mWeatherData.suggestion.sport.brf);
                ((SuggestionViewHolder) holder).sportTxt.setText(mWeatherData.suggestion.sport.txt);

                ((SuggestionViewHolder) holder).travelBrief.setText("旅游指数---" + mWeatherData.suggestion.trav.brf);
                ((SuggestionViewHolder) holder).travelTxt.setText(mWeatherData.suggestion.trav.txt);

                ((SuggestionViewHolder) holder).fluBrief.setText("感冒指数---" + mWeatherData.suggestion.flu.brf);
                ((SuggestionViewHolder) holder).fluTxt.setText(mWeatherData.suggestion.flu.txt);
            } catch (Exception e) {
                Snackbar.make(holder.itemView, R.string.network_error, Snackbar.LENGTH_SHORT).show();

                PLog.e(TAG, e.toString() + "                 TYPE_WEATHER_SUGGESTION         ");
            }
        }

        if (ItemViewType == TYPE_WEATHER_HISTORY) {
            try {
                //今日 明日
//            ((ForecastViewHolder) holder).forecastDate[0].setText("今日");
//            ((ForecastViewHolder) holder).forecastDate[1].setText("明日");
                for (int i = 0; i < mWeatherData.dailyForecast.size(); i++) {
                    if (i > 1) {
                        try {
                            ((ForecastViewHolder) holder).forecastDate[i].setText(
                                    dayForWeek(mWeatherData.dailyForecast.get(i).date));
                        } catch (Exception e) {
                            PLog.e(TAG, e.toString());
                        }
                    }

                    Glide.with(holder.itemView.getContext())
                            .load(mSetting.getInt(mWeatherData.dailyForecast.get(i).cond.txtD, R.mipmap.none))
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(((ForecastViewHolder) holder).forecastIcon[i]);

                    ((ForecastViewHolder) holder).forecastTemp[i].setText(
                            mWeatherData.dailyForecast.get(i).tmp.min + "° " +
                                    mWeatherData.dailyForecast.get(i).tmp.max + "°");
                    ((ForecastViewHolder) holder).forecastTxt[i].setText(
                            mWeatherData.dailyForecast.get(i).cond.txtD + "。 最高" +
                                    mWeatherData.dailyForecast.get(i).tmp.max + "℃。 " +
                                    mWeatherData.dailyForecast.get(i).wind.sc + " " +
                                    mWeatherData.dailyForecast.get(i).wind.dir + " " +
                                    mWeatherData.dailyForecast.get(i).wind.spd + " km/h。 " +
                                    "降水几率 " +
                                    "" + mWeatherData.dailyForecast.get(i).pop + "%。");
                }
            } catch (Exception e) {
                Snackbar.make(holder.itemView, R.string.network_error, Snackbar.LENGTH_SHORT).show();
                PLog.e(TAG, e.toString() + "                 TYPE_WEATHER_HISTORY         ");
            }
        }
    }


    @Override
    public int getItemCount() {
        return 4;
    }


    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }


    /**
     * 当前天气情况
     */
    class NowWeatherViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView weatherIcon;
        private TextView tempFlu;
        private TextView tempMax;
        private TextView tempMin;

        private TextView tempPm;
        private TextView tempQuality;


        public NowWeatherViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            tempFlu = (TextView) itemView.findViewById(R.id.temp_flu);
            tempMax = (TextView) itemView.findViewById(R.id.temp_max);
            tempMin = (TextView) itemView.findViewById(R.id.temp_min);

            tempPm = (TextView) itemView.findViewById(R.id.temp_pm);
            tempQuality = (TextView) itemView.findViewById(R.id.temp_quality);
        }
    }

    /**
     * 当日小时预告
     */
    class HoursWeatherViewHolder extends RecyclerView.ViewHolder {


        private LinearLayout itemHourInfoLinearlayout;
        private TextView[] mClock;
        private TextView[] mTemp;
        private TextView[] mHumidity;
        private TextView[] mWind;


        public HoursWeatherViewHolder(View itemView) {
            super(itemView);

            try {

                if (null != mWeatherData) {
                    itemHourInfoLinearlayout = (LinearLayout) itemView.findViewById(R.id.item_hour_info_linearlayout);

                    mClock = new TextView[mWeatherData.hourlyForecast.size()];
                    mTemp = new TextView[mWeatherData.hourlyForecast.size()];
                    mHumidity = new TextView[mWeatherData.hourlyForecast.size()];
                    mWind = new TextView[mWeatherData.hourlyForecast.size()];


                    for (int i = 0; i < mWeatherData.hourlyForecast.size(); i++) {
                        View view = View.inflate(itemView.getContext(), R.layout.item_hour_info_line, null);
                        mClock[i] = (TextView) view.findViewById(R.id.one_clock);
                        mTemp[i] = (TextView) view.findViewById(R.id.one_temp);
                        mHumidity[i] = (TextView) view.findViewById(R.id.one_humidity);
                        mWind[i] = (TextView) view.findViewById(R.id.one_wind);
                        itemHourInfoLinearlayout.addView(view);
                    }
                }
            } catch (Exception e) {
                PLog.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * 当日建议
     */
    class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView clothBrief;
        private TextView clothTxt;
        private TextView sportBrief;
        private TextView sportTxt;
        private TextView travelBrief;
        private TextView travelTxt;
        private TextView fluBrief;
        private TextView fluTxt;


        public SuggestionViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.cardView);
            clothBrief = (TextView) itemView.findViewById(R.id.cloth_brief);
            clothTxt = (TextView) itemView.findViewById(R.id.cloth_txt);
            sportBrief = (TextView) itemView.findViewById(R.id.sport_brief);
            sportTxt = (TextView) itemView.findViewById(R.id.sport_txt);
            travelBrief = (TextView) itemView.findViewById(R.id.travel_brief);
            travelTxt = (TextView) itemView.findViewById(R.id.travel_txt);
            fluBrief = (TextView) itemView.findViewById(R.id.flu_brief);
            fluTxt = (TextView) itemView.findViewById(R.id.flu_txt);
        }
    }

    /**
     * 未来天气
     */
    class ForecastViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout forecastLinear;
        private TextView[] forecastDate;
        private TextView[] forecastTemp;
        private TextView[] forecastTxt;
        private ImageView[] forecastIcon;


        public ForecastViewHolder(View itemView) {
            super(itemView);

            try {

                if (null != mWeatherData) {
                    forecastLinear = (LinearLayout) itemView.findViewById(R.id.forecast_linear);

                    forecastDate = new TextView[mWeatherData.dailyForecast.size()];
                    forecastTemp = new TextView[mWeatherData.dailyForecast.size()];
                    forecastTxt = new TextView[mWeatherData.dailyForecast.size()];
                    forecastIcon = new ImageView[mWeatherData.dailyForecast.size()];
                    for (int i = 0; i < mWeatherData.dailyForecast.size(); i++) {
                        View view = View.inflate(itemView.getContext(), R.layout.item_forecast_line, null);
                        forecastDate[i] = (TextView) view.findViewById(R.id.forecast_date);
                        forecastTemp[i] = (TextView) view.findViewById(R.id.forecast_temp);
                        forecastTxt[i] = (TextView) view.findViewById(R.id.forecast_txt);
                        forecastIcon[i] = (ImageView) view.findViewById(R.id.forecast_icon);
                        forecastLinear.addView(view);
                    }
                }
            } catch (Exception e) {
                PLog.e(TAG, e.getMessage());
            }
        }
    }
}
