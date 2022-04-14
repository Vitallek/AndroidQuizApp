package com.example.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "quizDb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "quizPlayers";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "playerName";

    // below variable id for our course duration column.
    private static final String SCORE_COL = "playerScore";

    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating 
        // an sqlite query and we are 
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + SCORE_COL + " TEXT)";

        // at last we are calling a exec sql 
        // method to execute above sql query
        db.execSQL(query);
    }

    public List<PlayerDBmodel> getPlayers(){
        List<PlayerDBmodel> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE_COL + " LIMIT 100" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int playerID = cursor.getInt(0);
                String playerName = cursor.getString(1);
                int playerScore = cursor.getInt(2);
                PlayerDBmodel player = new PlayerDBmodel(playerID,playerName,playerScore);
                returnList.add(player);
            } while (cursor.moveToNext());
        }
        Collections.sort(returnList, new Comparator<PlayerDBmodel>() {
            @Override
            public int compare(PlayerDBmodel p1, PlayerDBmodel p2) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return p2.getScore() - p1.getScore();
            }
        });
        cursor.close();
        db.close();
        return returnList;
    };
    // this method is use to add new player to our sqlite database.
    public void addNewField(String playerName, int score) {

        // on below line we are creating a variable for 
        // our sqlite database and calling writable method 
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a 
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values 
        // along with its key and value pair.
        values.put(NAME_COL, playerName);
        values.put(SCORE_COL, score);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}