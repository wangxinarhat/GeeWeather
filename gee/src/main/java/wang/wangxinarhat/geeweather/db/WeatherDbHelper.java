package wang.wangxinarhat.geeweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Molo on 2015.12.20.19.05
 */
public class WeatherDbHelper extends SQLiteOpenHelper {
    public static final String DB_WEATHER_CITY_ID_NAME = "CityId";
    public static final String DB_WEATHER_CITY_ID_TABLE_NAME = "CITY";//表名

    public static final String DB_WEATHER_CITY_ID_UNIQUE = "_ID";
    public static final String DB_WEATHER_CITY_ID = "city_id";
    public static final String DB_WEATHER_CITY_SPELL_ZH = "city_spell_zh";
    public static final String DB_WEATHER_CITY_CITY_AREA = "city_area";
    public static final String DB_WEATHER_CITY_CITY_TOWN = "city_town";
    public static final String DB_WEATHER_CITY_CITY_PROVINCE = "city_province";


   /* private final String CREATE_CITY_ID_TABLE = "CREATE TABLE "
            + DB_WEATHER_CITY_ID_TABLE_NAME
            + "(_id integer primary key autoincrement," + DB_WEATHER_CITY_ID + "text,"
            + DB_WEATHER_CITY_SPELL_ZH + " text,"
            + DB_WEATHER_CITY_CITY_AREA + " text,"
            + DB_WEATHER_CITY_CITY_TOWN + " text,"
            + DB_WEATHER_CITY_CITY_PROVINCE + " text)";

*/

    private final String CREATE_CITY_ID_TABLE = "CREATE TABLE CITY(id integer primary key autoincrement,city_id text,city_spell_zh text,city_area text,city_town text,city_province text)";
    public WeatherDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CITY_ID_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
