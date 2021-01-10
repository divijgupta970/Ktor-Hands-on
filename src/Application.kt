package com.divij

import com.divij.dao.CustomersDAO
import com.divij.routes.registerCustomerRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    val dao = CustomersDAO(Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver"))
    dao.init()
    registerCustomerRoutes(dao)
}

