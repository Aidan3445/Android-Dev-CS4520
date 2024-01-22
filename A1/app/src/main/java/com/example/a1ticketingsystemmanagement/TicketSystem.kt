package com.example.a1ticketingsystemmanagement

class TicketSystem {
    private val processedTickets: List<Ticket> = mutableListOf()

    /**
     * Process a list of [tickets]
     * @param tickets the list of tickets to process
     */
    fun processTickets(tickets: List<Ticket>) {
        // For each ticket,
        // 1. log ALL the params
        // 2. print name
        // anything else necessary
        for (ticket in tickets) {
            ticket.printTicket()
            this.processedTickets.plus(ticket)
        }
    }

    /**
     * Create a new VIP Ticket with [vipName] and [price]
     * @param vipName the name of the VIP
     * @param price the price of the ticket
     * @return the new VIP Ticket
     */
    fun generateVipTicket(
        vipName: String,
        price: Int,
    ): Ticket {
        return VipTicket(vipName, price)
    }

    /**
     * Create a new Regular Ticket with [name], [price] and [isDiscounted]
     * @param name the name of the ticket
     * @param price the price of the ticket
     * @param isDiscounted is the ticket discounted
     * @return the new Regular Ticket
     */
    fun generateRegularTicket(
        name: String,
        price: Int,
        isDiscounted: Boolean,
    ): Ticket {
        return RegularTicket(name, price, isDiscounted)
    }

    /**
     * Create a new Student Ticket with [uniName], [studentFirstName], [studentLastName] and [price]
     * @param uniName the name of the university
     * @param studentFirstName the first name of the student
     * @param studentLastName the last name of the student
     * @param price the price of the ticket
     * @return the new Student Ticket
     */
    fun generateStudentTicket(
        uniName: String,
        studentFirstName: String,
        studentLastName: String,
        price: Int,
    ): Ticket {
        return StudentTicket(uniName, studentFirstName, studentLastName, price)
    }

    /**
     * Get all tickets that are over a given [amount]
     * @param amount the amount to compare to
     // @param ticket the ticket to compare to
     * @return the list of tickets
     */
    fun getTicketsOverAmount(
        amount: Int,
        // ticket: Ticket,
    ): List<Ticket> {
        return processedTickets.filter { it.price > amount }
    }

    /**
     * Get all StudentTicket that are not for a given [excludeUni]
     * @param excludeUni the university to exclude
     * @return the list of tickets
     */
    fun getFilteredStudentTickets(excludeUni: String): List<Ticket> {
        return processedTickets.filter { it is StudentTicket && !it.isUni(excludeUni) }
    }

    /**
     * Get the number of VIP Tickets that have been processed
     * @return the number of tickets
     */
    fun getCountOfVipTickets(): Int {
        return processedTickets.filterIsInstance<VipTicket>().size
    }

    /**
     * Get the total price of all the RegularTicket that have been processed
     * @return the total price
     */
    fun getTotalPriceForRegularTickets(): Int {
        return processedTickets.filterIsInstance<RegularTicket>().sumOf { it.price }
    }

    /**
     * Get the highest price of each ticket type that have been processed
     * @return the list of prices
     */
    fun getHighestPriceForEachVipTickets(): List<Int> {
        return listOf(
            processedTickets.filterIsInstance<VipTicket>().maxOf { it.price },
            processedTickets.filterIsInstance<RegularTicket>().maxOf { it.price },
            processedTickets.filterIsInstance<StudentTicket>().maxOf { it.price },
        )
    }
}

// Example : TA's will check as follows :

fun main() {
    val obj = TicketSystem()
    obj.generateVipTicket("John", 10) // log printed for VIP here
    println(obj.getCountOfVipTickets()) // 1
}
