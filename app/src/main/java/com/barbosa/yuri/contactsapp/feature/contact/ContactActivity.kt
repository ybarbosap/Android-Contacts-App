package com.barbosa.yuri.contactsapp.feature.contact

import android.os.Bundle
import android.view.View
import com.barbosa.yuri.contactsapp.R
import com.barbosa.yuri.contactsapp.application.ContactApplication
import com.barbosa.yuri.contactsapp.bases.BaseActivity
import com.barbosa.yuri.contactsapp.feature.contact.presentation.ContactPresenter
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import kotlinx.android.synthetic.main.activity_contact.*

class ContactActivity : BaseActivity(), ContactPresenter.Companion.ContactPresenterContract {
    var contactID: Int? = null
    lateinit var presenter: ContactPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        setUpActivity()
    }

    private fun setUpActivity(){
        setUpToolBar(contact_toolBar, "Contato", true)
        btn_save.setOnClickListener { saveContact() }

        ContactApplication.instance.helper?.let {
            presenter = ContactPresenter(this, it)
        }

        intent.extras?.let {
            contactID = it.getInt(ID_KEY)
        }
    }

    override fun onResume() {
        super.onResume()
        contactID?.let {
            contact_progress.visibility = View.VISIBLE
            presenter.setUpContact(it)
        }

    }

    private fun saveContact() {
        contact_progress.visibility = View.VISIBLE
        val name = et_name.text.toString()
        val phone = et_phone.text.toString()
        if(contactID != null){
            presenter.saveContact(name, phone, contactID)
        }else{
            presenter.saveContact(name, phone, null)
        }

    }

    override fun onDeleteContact() = finish()

    override fun onSaveContact(){
        runOnUiThread {
            contact_progress.visibility = View.GONE
            finish()
        }
    }

    override fun startContact(contactVO: ContactVO) {
        runOnUiThread {
            contactVO.let {
                et_name.setText(contactVO.name)
                et_phone.setText(contactVO.phone)
            }
            btn_delete.visibility = View.VISIBLE
            btn_delete.setOnClickListener { presenter.deleteContact(contactVO.id) }
            contact_progress.visibility = View.GONE
        }
    }

    companion object {
        const val ID_KEY = "ID"
    }
}