package com.barbosa.yuri.contactsapp.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO

class DBHelper(
    context: Context
) : SQLiteOpenHelper(context, NAME, null, VERSION) {


    companion object {
        private val NAME = "contacts.db"
        private val VERSION = 1
    }

    private val TABLE_NAME = "contact"
    private val COLLUMN_ID = "id"
    private val COLLUMN_NAME = "name"
    private val COLLUMN_PHONE = "phone"

    private val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    private val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ("+
            "$COLLUMN_ID INTEGER NOT NULL,"+
            "$COLLUMN_NAME TEXT NOT NULL,"+
            "$COLLUMN_PHONE TEXT NOT NULL,"+
            " "+
            "PRIMARY KEY ($COLLUMN_ID AUTOINCREMENT)"+
            ")"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion) db?.execSQL(DROP_TABLE)
    }

    //Pure sql example
    fun getContacts(find: String, isFindById: Boolean = false): List<ContactVO>{
        val db = readableDatabase
        val list = mutableListOf<ContactVO>()
        val cursor = if(isFindById){
            val sql = "SELECT * FROM $TABLE_NAME WHERE $COLLUMN_ID = ?"
            db.rawQuery(sql, arrayOf(find))
        }else{
            val sql = "SELECT * FROM $TABLE_NAME WHERE $COLLUMN_NAME LIKE ?"
            db.rawQuery(sql, arrayOf("%$find%"))
        }

        if(cursor == null){
            db.close()
            return  list
        }

        while (cursor.moveToNext()){
            val contact = ContactVO(
                id = cursor.getInt(cursor.getColumnIndex(COLLUMN_ID)),
                name = cursor.getString(cursor.getColumnIndex(COLLUMN_NAME)),
                phone = cursor.getString(cursor.getColumnIndex(COLLUMN_PHONE))
            )
            list.add(contact)
        }
        db.close()
        return  list
    }

    //using sqlHelper function example
    fun saveContact(contactVO: ContactVO){
        val db = writableDatabase ?: return

        val content = ContentValues()
        content.put(COLLUMN_NAME, contactVO.name)
        content.put(COLLUMN_PHONE, contactVO.phone)

        db.insert(TABLE_NAME, null, content)
        db.close()
    }

    fun deleteContact(id: Int){
        val db = writableDatabase
        db.delete(TABLE_NAME, "id = ?", arrayOf("$id"))
        db.close()
    }

    fun updateContact(contact: ContactVO){
        val db = writableDatabase

        val content = ContentValues()
        content.put(COLLUMN_ID, contact.id)
        content.put(COLLUMN_NAME, contact.name)
        content.put(COLLUMN_PHONE, contact.phone)

        db.update(TABLE_NAME, content, "id = ?", arrayOf(contact.id.toString()))
        db.close()
    }
}