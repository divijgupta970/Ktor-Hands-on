package com.divij.dao

import com.divij.models.Customer
import io.ktor.utils.io.core.*

interface DAOInterface : Closeable {
    fun init()
    fun createCustomer(firstName: String, lastName: String, email: String)
    fun updateCustomer(id: Int, firstName: String, lastName: String, email: String)
    fun deleteCustomer(id: Int)
    fun getCustomer(id: Int): Customer?
    fun getAllCustomers(): List<Customer>
}