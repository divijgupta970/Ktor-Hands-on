package com.divij.dao

import com.divij.models.Customer
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CustomersDAO(val db: Database) : DAOInterface {
    override fun init() {
        transaction(db){
            SchemaUtils.create(Customers)
        }
    }

    override fun createCustomer(firstName: String, lastName: String, email: String) = transaction(db) {
        Customers.insert {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        }
        Unit
    }

    override fun updateCustomer(id: Int, firstName: String, lastName: String, email: String) = transaction(db) {
        Customers.update({Customers.id eq id}) {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        }
        Unit
    }

    override fun deleteCustomer(id: Int) = transaction(db){
        Customers.deleteWhere { Customers.id eq id }
        Unit
    }

    override fun getCustomer(id: Int): Customer? {
        return transaction(db) {
            Customers.select { Customers.id eq id }.map {
                Customer(
                    it[Customers.id], it[Customers.firstName], it[Customers.lastName], it[Customers.email]
                )
            }.singleOrNull()
        }
    }

    override fun getAllCustomers(): List<Customer> = transaction(db) {
        Customers.selectAll().map {
            Customer(
                it[Customers.id], it[Customers.firstName], it[Customers.lastName], it[Customers.email]
            )
        }
    }

    override fun close() {
    }
}