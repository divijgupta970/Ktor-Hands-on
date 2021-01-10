package com.divij.dao

import org.jetbrains.exposed.sql.Table


object Customers : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val firstName = varchar("firstName", 100)
    val lastName = varchar("lastName", 100)
    val email = varchar("email", 500)
}