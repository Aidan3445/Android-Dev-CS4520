package com.example.a1ticketingsystemmanagement

interface Ticket {
    /**
     * The value of the ticket
     */
    val price: Int

    /**
     * Print the ticket details
     */
    fun printTicket() {
        println("Price: $price")
    }
}
