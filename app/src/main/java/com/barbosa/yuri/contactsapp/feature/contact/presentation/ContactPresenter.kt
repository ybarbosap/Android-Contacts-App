package com.barbosa.yuri.contactsapp.feature.contact.presentation

import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import com.barbosa.yuri.contactsapp.helpers.DBHelper

class ContactPresenter(
    private val listeners: ContactPresenterContract,
    private val dbHelper: DBHelper
) {
    fun saveContact(name: String, phone: String, id: Int?){
        Thread {
            try {
                if (id != null) {
                    val contactVO = ContactVO(id, name, phone)
                    dbHelper.updateContact(contactVO)
                } else {
                    val contactVO = ContactVO(0, name, phone)
                    dbHelper.saveContact(contactVO)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            listeners.onSaveContact()
        }.start()
    }

    fun deleteContact(id: Int){
        Thread {
            dbHelper.deleteContact(id)
            listeners.onDeleteContact()
        }.start()
    }

   fun setUpContact(id: Int){
       Thread {
           Thread.sleep(500)
           val data = dbHelper.getContacts(id.toString(), true)
           listeners.startContact(data.first())
       }.start()
   }

    companion object {
        interface ContactPresenterContract {
            fun onDeleteContact()
            fun onSaveContact()
            fun startContact(contactVO: ContactVO)
        }
    }
}