package com.barbosa.yuri.contactsapp.feature.contactslist.presentation

import android.view.View
import com.barbosa.yuri.contactsapp.application.ContactApplication
import com.barbosa.yuri.contactsapp.feature.contactslist.adapter.ContactAdapter
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import com.barbosa.yuri.contactsapp.helpers.DBHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainPresenter(
    val listener: MainPresenterContract,
    val helper: DBHelper
) {

    fun getContacts(arg: String){
        Thread {
            Thread.sleep(1000)
            var list: List<ContactVO> = mutableListOf()
            try {
                list = helper.getContacts(arg) ?: mutableListOf()
                listener.onGetContacts(list)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }.start()
    }


    companion object {
        interface MainPresenterContract {
            fun onGetContacts(contacts: List<ContactVO>)
        }
    }
}