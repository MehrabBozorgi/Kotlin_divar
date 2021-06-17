package Sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val dbName = "divar"
val tblName = "favorite"


val colId = "id"
val colAdsId = "adsid"
val colTitle = "title"
val colDesc = "description"
val colImage = "image"
val colCity = "city"
val colDate = "date"
val colCat = "cat"
val colPhone = "phone"
val colPrice = "price"
val colOption = "option"
val dbVersion = 1

val createDatabaseQuery = "CREATE TABLE IF NOT EXISTS " +
        tblName + " (" +
        colId + " INTEGER PRIMARY KEY, " +
        colAdsId + " INTEGER " +
        colTitle + " TEXT, " +
        colDesc + " TEXT, " +
        colImage + " TEXT, " +
        colCity + " TEXT, " +
        colDate + " TEXT, " +
        colCat + " TEXT, " +
        colPhone + " TEXT, " +
        colPrice + " TEXT, " +
        colOption + " TEXT);"

class MyDatabase(context: Context) : SQLiteOpenHelper(context, dbName, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createDatabaseQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS" + tblName)

    }

    fun insertData(values: ContentValues): Long {

        var db = this.writableDatabase
        var id = db!!.insert(tblName, "", values)
        return id

    }


    fun getAllData(): Cursor {
        var db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("SELECT * FROM " + tblName, null)
        } catch (e: Exception) {
            Log.i("kotlin", e.toString())
        }

        return cursor!!

    }


    //متدیی باهاش می تونیم اون اگهی هایی که میخوایم رو انتخواب کنیم
    fun getUniqeData(id: Int): Cursor {
        var db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor =
                db.rawQuery("SELECT * FROM " + tblName + " WHERE " + colAdsId + " = " + id, null)
        } catch (e: Exception) {
            Log.i("kotlin", e.toString())
        }

        return cursor!!
    }

    fun delete(id: Int): Int {

        var db = this.writableDatabase
        var selectionArgs = arrayOf(id.toString())
        var id = db.delete(tblName, "adsid=?", selectionArgs)

        return id
    }


}










