package cn.ucai.superwechat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cn.ucai.superwechat.I;
import cn.ucai.superwechat.bean.User;

/**
 * Created by Administrator on 2016/5/19 0019.
 */
public class UserDao extends SQLiteOpenHelper{
    public static final String Id="_id";
    public static final String TABLE_NAME = "user";
    public static final int VERSION = 1;
    public static final String DB_NAME = "user.db";
    public UserDao(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" ("+
                I.User.USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                I.User.USER_NAME+" varchar unique not null," +
                I.User.NICK+" varchar,"+
                I.User.PASSWORD+" varchar,"+
                I.User.UN_READ_MSG_COUNT+" int default(0)"+
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(I.User.USER_ID,user.getMUserId());
        values.put(I.User.USER_NAME, user.getMUserName());
        values.put(I.User.NICK,user.getMUserNick());
        values.put(I.User.PASSWORD,user.getMUserPassword());
        values.put(I.User.UN_READ_MSG_COUNT,user.getMUserUnreadMsgCount());
        SQLiteDatabase db = getWritableDatabase();
        long intsert = db.insert(TABLE_NAME, null, values);
        return intsert>0;
    }

    public User findUserByUserName(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + I.User.USER_NAME + "=?";
        Cursor c = db.rawQuery(sql,new String[]{userName});
        if (c.moveToNext()) {
            int uid = c.getInt(c.getColumnIndex(I.User.USER_ID));
            String nike = c.getString(c.getColumnIndex(I.User.NICK));
            String password = c.getString(c.getColumnIndex(I.User.PASSWORD));
            int unReaderMsgCount = c.getInt(c.getColumnIndex(I.User.UN_READ_MSG_COUNT));
            return new User(uid,userName,password,nike,unReaderMsgCount);
        }
        c.close();
        return null;
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put(I.User.USER_ID,user.getMUserId());
        values.put(I.User.USER_NAME, user.getMUserName());
        values.put(I.User.NICK,user.getMUserNick());
        values.put(I.User.PASSWORD,user.getMUserPassword());
        values.put(I.User.UN_READ_MSG_COUNT,user.getMUserUnreadMsgCount());
        SQLiteDatabase db = getWritableDatabase();
        long insert = db.update(TABLE_NAME, values, I.User.USER_NAME + "=?", new String[]{user.getMUserName()});
        return insert > 0;
    }
}
