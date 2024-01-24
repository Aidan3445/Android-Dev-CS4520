package com.example.a1ticketingsystemmanagement

class TicketSystem {
    val processedTickets: MutableList<Ticket> = mutableListOf()

    /**
     * Process a [ticket]
     * @param ticket the ticket to process
     */
    private fun processTickets(ticket: Ticket) {
        ticket.printTicket()
        this.processedTickets.add(ticket)
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
        val ticket = VipTicket(vipName, price)
        processTickets(ticket)
        return ticket
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
        val ticket = RegularTicket(name, price, isDiscounted)
        processTickets(ticket)
        return ticket
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
        val ticket = StudentTicket(uniName, studentFirstName, studentLastName, price)
        processTickets(ticket)
        return ticket
    }

    /**
     * Get all tickets that are over a given [amount]
     * @param amount the amount to compare to
     * @return the list of tickets
     */
    inline fun <reified T : Ticket> getTicketsOverAmount(amount: Int): List<T> {
        return processedTickets.filterIsInstance<T>().filter { it.price > amount }
    }

    /**
     * Filter tickets by a given [criteria]
     * @param criteria the criteria to filter by is a lambda [Ticket] -> [Boolean]
     * @return the list of tickets
     */
    inline fun <reified T : Ticket> getFilteredTickets(criteria: (Ticket) -> Boolean): List<Ticket> {
        return processedTickets.filterIsInstance<T>().filter { criteria(it) }
    }
}

/**
 * Get the number of VIP Tickets that have been processed
 * @return the number of tickets
 */
fun TicketSystem.getCountOfVipTickets(): Int {
    return processedTickets.filterIsInstance<VipTicket>().size
}

/**
 * Get the total price of all the RegularTicket that have been processed
 * @return the total price
 */
fun TicketSystem.getTotalPriceForRegularTickets(): Int {
    return processedTickets.filterIsInstance<RegularTicket>().sumOf { it.price }
}

/**
 * Get the highest price of each ticket type that have been processed
 * @return the list of prices
 */
fun TicketSystem.getHighestPriceTicketForEachType(): List<Ticket> {
    var vipMax: Ticket? = null
    var regularMax: Ticket? = null
    var studentMax: Ticket? = null

    for (ticket in processedTickets) {
        when (ticket) {
            is VipTicket -> {
                if (vipMax == null || ticket.price > vipMax.price) {
                    vipMax = ticket
                }
            }
            is RegularTicket -> {
                if (regularMax == null || ticket.price > regularMax.price) {
                    regularMax = ticket
                }
            }
            is StudentTicket -> {
                if (studentMax == null || ticket.price > studentMax.price) {
                    studentMax = ticket
                }
            }
        }
    }

    return listOfNotNull(vipMax, regularMax, studentMax)
}

// Example : TA's will check as follows :

fun main() {
    val obj = TicketSystem()
    obj.generateVipTicket("John", 100) // log printed for VIP here
    obj.generateRegularTicket("Sarah", 10, true) // log printed for Regular here
    obj.generateRegularTicket("Andrew", 20, false) // log printed for Regular here
    obj.generateStudentTicket("NEU", "Aidan", "Weinberg", 6) // log printed for Student here

    println(obj.getCountOfVipTickets()) // 1
    println(obj.getTicketsOverAmount<VipTicket>(5))
    println(obj.getTicketsOverAmount<StudentTicket>(5))
    println(obj.getTotalPriceForRegularTickets())
}
