package de.cicero_interactive.practiceofprogrammingexamapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;

public class MyDatabase extends SQLiteOpenHelper {
    static final String DB_NAME = "Main";
    static final String DB_TABLE_LOGIN = "Login";
    static final String DB_TABLE_HEALTH = "Health";
    static final String DB_TABLE_CHAT = "Chat";
    static final String DB_TABLE_STATISTICS = "Statistics ";
    static final int DB_VERSION = 13;

    Context context;
    SQLiteDatabase database;

    final String correct_username = "Max";
    final String correct_password = "123456";


    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_LOGIN + "(id integer primary key autoincrement, username text, password text)");
        sqLiteDatabase.execSQL("INSERT INTO " + DB_TABLE_LOGIN + "(username, password) VALUES('" + correct_username +"', '" + correct_password + "')");

        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_HEALTH + "(id integer primary key autoincrement, json_array text)");

        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_CHAT + "(id integer primary key autoincrement, json_array text)");

        sqLiteDatabase.execSQL("CREATE TABLE " + DB_TABLE_STATISTICS + "(id integer primary key autoincrement, name text, value int)");
        sqLiteDatabase.execSQL("INSERT INTO " + DB_TABLE_STATISTICS + "(name, value) VALUES('correct_quizzes', 0)");
        sqLiteDatabase.execSQL("INSERT INTO " + DB_TABLE_STATISTICS + "(name, value) VALUES('wrong_quizzes', 0)");

        ArrayList<ChatMessage> messages = new ArrayList<ChatMessage>();
        messages.add(new ChatMessage("self", "Hello Mr. Jones, I have a question. I've read an article on the Internet lately that states ticks would be very dangerous this year. Is that right?", new Date(-1900 + 2020, -1 + 07, 17)));
        messages.add(new ChatMessage("Dr. Jones", "Yes, that's true, unfortunately. One in every ninety ticks can transmit the Lyme disease - watch out this summer!", new Date(-1900 + 2020, -1 + 07, 18)));
        Gson gson = new Gson();
        String inputString = gson.toJson(messages);
        sqLiteDatabase.execSQL("INSERT INTO " + DB_TABLE_CHAT + "(json_array) VALUES('" + inputString +"')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_LOGIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_HEALTH);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CHAT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_STATISTICS);

        onCreate(sqLiteDatabase);
    }




    public String getUsername() {
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT username FROM " + DB_TABLE_LOGIN, null);

        String username = "";
        while (cursor.moveToNext()) {
            username = cursor.getString(0);
        }

        return username;
    }
    public String getPassword() {
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT password FROM " + DB_TABLE_LOGIN, null);

        String password = "";
        while (cursor.moveToNext()) {
            password = cursor.getString(0);
        }

        return password;
    }


    public int getCorrectQuizNumber() {
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT value FROM " + DB_TABLE_STATISTICS + " WHERE name = 'correct_quizzes' ", null);

        String correct_quizzes = "";
        while (cursor.moveToNext()) {
            correct_quizzes = cursor.getString(0);
        }

        if (!correct_quizzes.equals("")) {
            return Integer.parseInt(correct_quizzes);
        } else {
            return 0;
        }
    }
    public void incrementCorrectQuizNumber() {
        database = getWritableDatabase();
        database.execSQL("UPDATE " + DB_TABLE_STATISTICS + " SET value = value + 1 WHERE name = 'correct_quizzes'");
    }


    public int getWrongQuizNumber() {
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT value FROM " + DB_TABLE_STATISTICS + " WHERE name = 'wrong_quizzes' ", null);

        String wrong_quizzes = "";
        while (cursor.moveToNext()) {
            wrong_quizzes = cursor.getString(0);
        }

        if (!wrong_quizzes.equals("")) {
            return Integer.parseInt(wrong_quizzes);
        } else {
            return 0;
        }
    }
    public void incrementWrongQuizNumber() {
        database = getWritableDatabase();
        database.execSQL("UPDATE " + DB_TABLE_STATISTICS + " SET value = value + 1 WHERE name = 'wrong_quizzes'");
    }


    public void insertMessages(ArrayList<ChatMessage> messages) throws JSONException {
        Gson gson = new Gson();
        database = getWritableDatabase();

        database.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CHAT);
        database.execSQL("CREATE TABLE " + DB_TABLE_CHAT + "(id integer primary key autoincrement, json_array text)");

        String inputString = gson.toJson(messages);

        database.execSQL("INSERT INTO " + DB_TABLE_CHAT + "(json_array) VALUES('" + inputString +"')");
    }

    public ArrayList<ChatMessage> getMessages() throws JSONException {
        Gson gson = new Gson();
        database = getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + DB_TABLE_CHAT, null);

        String outputString = "";
        while (cursor.moveToNext()) {
            outputString = cursor.getString(1);
            Log.d("test", outputString);
        }

        Type type = new TypeToken<ArrayList<ChatMessage>>(){}.getType();
        ArrayList<ChatMessage> messages = gson.fromJson(outputString, type);

        return messages;
    }
}
