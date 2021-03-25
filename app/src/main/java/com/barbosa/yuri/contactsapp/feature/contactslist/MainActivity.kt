package com.barbosa.yuri.contactsapp.feature.contactslist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.barbosa.yuri.contactsapp.R
import com.barbosa.yuri.contactsapp.application.ContactApplication
import com.barbosa.yuri.contactsapp.bases.BaseActivity
import com.barbosa.yuri.contactsapp.feature.contact.ContactActivity
import com.barbosa.yuri.contactsapp.feature.contactslist.adapter.ContactAdapter
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import com.barbosa.yuri.contactsapp.feature.contactslist.presentation.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainPresenter.Companion.MainPresenterContract {
    lateinit var adapter: ContactAdapter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpActivity()
    }

    override fun onResume() {
        super.onResume()

        //Atualiza a tela ao iniciar
        onClickGetContacts()
    }

    private fun setUpActivity(){
        setUpToolBar(toolbar = toolBar, title = "Contacts", false)
        ivBuscar.setOnClickListener{ onClickGetContacts() }
        fab.setOnClickListener { startContactActivity() }
        ContactApplication.instance.helper?.let {
            presenter = MainPresenter(this, it)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun onClickGetContacts(){
        progress.visibility = View.VISIBLE
        presenter.getContacts("")
    }


    private fun startContactActivity(){
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun startContactActivityWith(id: Int): Unit{
        val intent = Intent(this, ContactActivity::class.java)
        intent.putExtra(ContactActivity.ID_KEY, id)
        startActivity(intent)
    }

    override fun onGetContacts(contacts: List<ContactVO>) {
        runOnUiThread {
            adapter = ContactAdapter(::startContactActivityWith, contacts)
            recyclerView.adapter = adapter
            progress.visibility = View.GONE
        }
    }
}