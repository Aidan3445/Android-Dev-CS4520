package com.example.a1ticketingsystemmanagement

class StudentTicket(
    private val uniName: String,
    private val studentFirstName: String,
    private val studentLastName: String,
    override val price: Int,
) : Ticket {
    override fun printTicket() {
        println("*** VIP Ticket ***")
        println("Name: $studentFirstName $studentLastName")
        println("University: $uniName")
        super.printTicket()
    }

    /**
     * Is the ticket for a given @param university
     * @return true if the ticket is for the given university, false otherwise
     */
    fun isUni(university: String): Boolean {
        return this.uniName == university
    }
}
