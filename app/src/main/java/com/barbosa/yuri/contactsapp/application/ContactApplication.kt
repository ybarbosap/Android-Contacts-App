package com.barbosa.yuri.contactsapp.application

import android.app.Application
import com.barbosa.yuri.contactsapp.helpers.DBHelper

// A classe application é a primeira a ser chamada quando o app está em execução
// Seja em primeio ou segundo plano. Muito utilizada para criar instâncias de
// escopo global e/ou classes que precisam ser inializadas no inicio da aplicação

class ContactApplication : Application() {
    var helper: DBHelper? = null

    override fun onCreate() {
        super.onCreate()
        instance = this
        helper = DBHelper(this)
    }

    companion object {
        lateinit var instance: ContactApplication
    }
}