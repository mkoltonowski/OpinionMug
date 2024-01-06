package com.example.mypub.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.mypub.database.entity.Location

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "test", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_LOCATION_TABLE = "CREATE TABLE location (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT)"

        val CREATE_GRADE_TABLE = "CREATE TABLE grade (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "locationId INTEGER, " +
                "value INTEGER, " +
                "FOREIGN KEY(locationId) REFERENCES location(id) ON DELETE CASCADE)"

        val CREATE_HISTORY_TABLE = "CREATE TABLE history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "locationId INTEGER, " +
                "date TEXT, " +
                "details TEXT, " +
                "FOREIGN KEY(locationId) REFERENCES location(id) ON DELETE CASCADE)"

        db.execSQL(CREATE_LOCATION_TABLE)
        db.execSQL(CREATE_GRADE_TABLE)
        db.execSQL(CREATE_HISTORY_TABLE)

        Log.d("DatabaseHelper", "Database created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }

    fun clearDatabase() {
        val db = this.writableDatabase
        db.execSQL("DELETE FROM location")
        db.execSQL("DELETE FROM grade")
        db.execSQL("DELETE FROM history")
        db.close()

        Log.d("DatabaseHelper", "Database cleared")
    }

    fun dropDatabase() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS location")
        db.execSQL("DROP TABLE IF EXISTS grade")
        db.execSQL("DROP TABLE IF EXISTS history")
        db.close()

        Log.d("DatabaseHelper", "Database dropped")
    }

    fun addLocation(location: Location) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", location.name)
            put("description", location.description)
        }
        db.insert("location", null, values)
        db.close()

        Log.d("DatabaseHelper", "Location added with name: ${location.name}")
    }

    fun getLocations(): List<Location> {
        val db = this.readableDatabase
        val query = "SELECT * FROM location"
        val cursor = db.rawQuery(query, null)
        val locations = mutableListOf<Location>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val description = cursor.getString(2)
                locations.add(Location(id, name, description))
                Log.d("DatabaseHelper", "Location: $id, $name, $description")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return locations
    }

    fun getLocation(id: Int): Location? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM location WHERE id = $id", null)
        var location: Location? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(1)
            val description = cursor.getString(2)
            location = Location(id, name, description)
            Log.d("DatabaseHelper", "Location: $id, $name, $description")
        }
        cursor.close()
        db.close()
        return location
    }

    fun updateLocation(location: Location): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("name", location.name)
        values.put("description", location.description)

        val success = db.update("location", values, "id = ?", arrayOf(location.id.toString()))
        db.close()

        Log.d("DatabaseHelper", "Location updated with name: ${location.name}")
        return success > 0
    }

    fun deleteLocation(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete("location", "id = ?", arrayOf(id.toString()))
        db.close()

        Log.d("DatabaseHelper", "Location deleted with id: $id")
        return success > 0
    }

    fun addGrade(locationId: Int, value: Int) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("locationId", locationId)
            put("value", value)
        }
        db.insert("grade", null, values)
        db.close()

        Log.d("DatabaseHelper", "Grade added with value: $value")
    }

    fun getGrades(locationId: Int): List<Int> {
        val db = this.readableDatabase
        val query = "SELECT * FROM grade WHERE locationId = $locationId"
        val cursor = db.rawQuery(query, null)
        val grades = mutableListOf<Int>()
        if (cursor.moveToFirst()) {
            do {
                val value = cursor.getInt(2)
                grades.add(value)
                Log.d("DatabaseHelper", "Grade: $value")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return grades
    }

    fun addHistory(locationId: Int, date: String, details: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("locationId", locationId)
            put("date", date)
            put("details", details)
        }
        db.insert("history", null, values)
        db.close()

        Log.d("DatabaseHelper", "History added with date: $date")
    }

    fun getHistory(locationId: Int): List<String> {
        val db = this.readableDatabase
        val query = "SELECT * FROM history WHERE locationId = $locationId"
        val cursor = db.rawQuery(query, null)
        val history = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(2)
                val details = cursor.getString(3)
                history.add("$date: $details")
                Log.d("DatabaseHelper", "History: $date, $details")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return history
    }

    fun getAverageGrade(locationId: Int): Double {
        val db = this.readableDatabase
        val query = "SELECT AVG(value) FROM grade WHERE locationId = $locationId"
        val cursor = db.rawQuery(query, null)
        var average = 0.0
        if (cursor.moveToFirst()) {
            do {
                average = cursor.getDouble(0)
                Log.d("DatabaseHelper", "Average grade: $average")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return average
    }

    fun getAverageGrade(): Double {
        val db = this.readableDatabase
        val query = "SELECT AVG(value) FROM grade"
        val cursor = db.rawQuery(query, null)
        var average = 0.0
        if (cursor.moveToFirst()) {
            do {
                average = cursor.getDouble(0)
                Log.d("DatabaseHelper", "Average grade: $average")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return average
    }

    fun getBestGrade(locationId: Int): Int {
        val db = this.readableDatabase
        val query = "SELECT MAX(value) FROM grade WHERE locationId = $locationId"
        val cursor = db.rawQuery(query, null)
        var best = 0
        if (cursor.moveToFirst()) {
            do {
                best = cursor.getInt(0)
                Log.d("DatabaseHelper", "Best grade: $best")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return best
    }

    fun getBestGrade(): Int {
        val db = this.readableDatabase
        val query = "SELECT MAX(value) FROM grade"
        val cursor = db.rawQuery(query, null)
        var best = 0
        if (cursor.moveToFirst()) {
            do {
                best = cursor.getInt(0)
                Log.d("DatabaseHelper", "Best grade: $best")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return best
    }

    fun getWorstGrade(locationId: Int): Int {
        val db = this.readableDatabase
        val query = "SELECT MIN(value) FROM grade WHERE locationId = $locationId"
        val cursor = db.rawQuery(query, null)
        var worst = 0
        if (cursor.moveToFirst()) {
            do {
                worst = cursor.getInt(0)
                Log.d("DatabaseHelper", "Worst grade: $worst")
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return worst
    }

}