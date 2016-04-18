package wang.wangxinarhat.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import wang.wangxinarhat.geeweather.utils.ThreadManger;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * Master of DAO (schema version 1): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 1;

    /**
     * Creates underlying database table using DAOs.
     */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CITYDao.createTable(db, ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CITYDao.dropTable(db, ifExists);
    }

    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     */
    public static class DevOpenHelper extends OpenHelper {

        private static String DB_NAME = "CityId";


        private static Context mContext;

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
            mContext = context;
        }

        public SQLiteDatabase openDatabase() {
            File dbFile = mContext.getDatabasePath(DB_NAME);

            if (!dbFile.exists()) {
                try {
                    copyDatabase(dbFile);
                } catch (IOException e) {
                    throw new RuntimeException("Error creating source database", e);
                }
            }

            return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
        }

        private void copyDatabase(final File dbFile) throws IOException {


            ThreadManger.getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = mContext.getAssets().open(DB_NAME);
                        OutputStream os = new FileOutputStream(dbFile);

                        byte[] buffer = new byte[1024];
                        while (is.read(buffer) > 0) {
                            os.write(buffer);
                        }

                        os.flush();
                        os.close();
                        is.close();
                    } catch (Exception e) {

                    }
                }
            });

        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(CITYDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

}
