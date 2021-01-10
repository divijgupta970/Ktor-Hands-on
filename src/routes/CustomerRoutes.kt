package com.divij.routes


import com.divij.dao.CustomersDAO
import com.divij.models.Customer
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.customerRouting(dao: CustomersDAO) {
    route("/customers") {
        get {
            call.respond(mapOf("posts" to dao.getAllCustomers()))
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                dao.getCustomer(id.toInt()) ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val customer = call.receive<Customer>()
            dao.createCustomer(customer.firstName, customer.lastName, customer.email)
            call.respondText("Customer stored correctly", status = HttpStatusCode.Accepted)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            dao.deleteCustomer(id.toInt())
            call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
        }
    }
}

fun Application.registerCustomerRoutes(dao: CustomersDAO) {
    routing {
        customerRouting(dao)
    }
}