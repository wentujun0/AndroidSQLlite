package com.example.jk.sqllites;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {
    private static final String DB_NAME = "users.db";
    private static final String TABLE_NAME ="users";
    private static final int DB_VERSION = 1;

    private static final String ID = "id";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "userpassword";
    private static final String NAME = "name";
    private static final String AGE = "age";

    private SQLiteDatabase database;  //创建的SQLlite数据库
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBAdapter(Context context) {
        this.context = context;
    }
    //打开数据库 函数
    public void open() throws SQLiteException{
        //创建一个DataOpenHelper对象

        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        //只有调用getWritableDatabase()才会调用DBOpenhelper的onCreate（）方法

            database = dbOpenHelper.getWritableDatabase();//创建可读写数据库
            System.out.println("测试"+database);

    }
    //关闭数据库 函数
    public void close() throws SQLiteException{
        if (database != null) {
            database.close();
            //不要有空的指针
            //database = null;
        }
    }
    //删除数据库 函数
    public void delete_table() throws SQLiteException {
        context.deleteDatabase(DB_NAME);
    }




    //插入 数据库记录
    public long insert(Users users) throws SQLiteException {

        ContentValues contentValues = new ContentValues();
        //插入数据

        contentValues.put(USER_NAME, users.UserName);//用户名
        contentValues.put(USER_PASSWORD, users.UserPassword);//密码
        contentValues.put(NAME, users.Name);//姓名
        contentValues.put(AGE, users.Age);//年龄

//        contentValues.put("username", "12");//用户名
//        contentValues.put("userpassword", "12");//密码
//        contentValues.put("name", "12");//姓名
//        contentValues.put("age", 1);//年龄

        System.out.println("数据库"+database);
        System.out.println("数"+contentValues);

        //
        return database.insert(TABLE_NAME, null, contentValues);
    }


    //删除所有数据库 记录
    public long deleteAllData(){
        return database.delete(DB_NAME,null,null);
    }
    //删除一个数据库的 记录
    public long deleteOnewData(String id){

        return database.delete(DB_NAME,ID+"=" + id,null);
    }


    private Users[] convertToUsers(Cursor cursor) {
        int resultSize = cursor.getCount();
        if (resultSize == 0 || !cursor.moveToFirst()) {
            return null;
        }

        Users[] users = new Users[resultSize];
        for (int i = 0; i < resultSize; i++) {
            users[i] = new Users();
            users[i].Id = cursor.getInt(0);
            users[i].UserName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            users[i].UserPassword = cursor.getString(cursor.getColumnIndex(USER_PASSWORD));
            users[i].Name = cursor.getString(cursor.getColumnIndex(NAME));
            users[i].Age = cursor.getInt(cursor.getColumnIndex(AGE));

            cursor.moveToNext();//游标向下移动一位
        }

        return users;
    }
        //查询所有的记录
    public Users[] selectAllData() {
        Cursor cursor = database.query(TABLE_NAME, new String[]{ID, USER_NAME, USER_PASSWORD,NAME,AGE},
                null, null, null, null,null);
        return this.convertToUsers(cursor);
    }
    //查询单独的一个记录
    public Users[] selectOneData(String username){
        Cursor cursor = database.query(TABLE_NAME,new String[]{ID,USER_NAME,USER_PASSWORD,NAME,AGE},
               USER_NAME + "=" +username,null,null,null,null);
        return this.convertToUsers(cursor);
    }




    static class DBOpenHelper extends SQLiteOpenHelper {

        private static final String DB_CREATE = "create table " + TABLE_NAME
                + " (" + ID + " integer primary key autoincrement, "
                + USER_NAME + " text not null, "
                +USER_PASSWORD + " text, "
                +NAME + " text, "
                + AGE + " integer);";
        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(DB_CREATE);
            System.out.print("创建数据库成功!");

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);

        }
    }
}
