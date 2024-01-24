package com.example.a1ticketingsystemmanagement

data class VipTicket(
    private val vipName: String,
    override val price: Int,
) : Ticket {
    override fun printTicket() {
        println("*** VIP Ticket ***")
        println("Name: $vipName")
        super.printTicket()
    }
}
