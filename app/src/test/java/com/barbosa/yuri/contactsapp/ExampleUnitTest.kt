package com.barbosa.yuri.contactsapp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.barbosa.yuri.contactsapp.feature.contactslist.models.ContactVO
import com.barbosa.yuri.contactsapp.helpers.DBHelper
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering
import org.robolectric.RobolectricTestRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * Escrever testes para as demais funcionalidades do helper e subir para o git
 */
@RunWith(RobolectricTestRunner::class)
class DBHelperUnitTest {
    // Context Mock
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val dbHelper = DBHelper(context)

    @Test
    fun `Quando chamar o mpetodo getContacts espera uma mutableListOf com 1 contato`() {
        //Prepara
        listOneContact()

        //valida
        val list = dbHelper.getContacts("")
        assertEquals(1, list.size)
        assertEquals("Yuri", list[0].name)
    }

    //Cria uma lista do helper para teste
    private fun listOneContact(){
        dbHelper.saveContact(
            ContactVO(
                0,
                "Yuri",
                "987 021 458"
            )
        )
    }

    @Test
    fun `Quando chamar deve deletar o contato do banco`(){
        listOneContact()

        dbHelper.deleteContact(1)
        val list = dbHelper.getContacts(1.toString(), true)
        assertEquals(0, list.size)
    }

    @Test
    fun `Quando chamar deve alterar nome e telefone do usuário cadastrado`(){
        listOneContact()

        dbHelper.updateContact(ContactVO(1, "Yuri Barbosa", "999 999 999"))
        val contact = (dbHelper.getContacts(1.toString(), true)).first()
        assertEquals("Yuri Barbosa", contact.name)
        assertEquals("999 999 999", contact.phone)
    }
}