package com.example.a1ticketingsystemmanagement

class RegularTicket(
    private val name: String,
    override val price: Int,
    private val isDiscounted: Boolean,
) : Ticket {
    override fun printTicket() {
        println("*** VIP Ticket ***")
        println("Name: $name")
        super.printTicket()
        println(if (isDiscounted) "Discounted!" else "No Discount")
    }
}
