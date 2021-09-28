package com.rm.jdbc.starter.test;

import com.rm.jdbc.starter.dao.TicketDao;
import com.rm.jdbc.starter.dto.TicketDto;
import com.rm.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class TicketTest {

    public static void main(String[] args) {

        checkFindAllWithParamTicket();
    }

    private static void checkFindAllWithParamTicket() {
        TicketDto ticketDto = new TicketDto(3, 0, "Степан Дор", "A1");
        TicketDao ticketDao = TicketDao.getInstance();
        List<Ticket> tickets = ticketDao.findAll(ticketDto);
        System.out.println(tickets);
    }

    private static void checkFindAllTicket() {
        TicketDao ticketDao = TicketDao.getInstance();
        List<Ticket> tickets = ticketDao.findAll();
        System.out.println(tickets);
    }

    private static void checkFindByIdTicket() {
        TicketDao ticketDao = TicketDao.getInstance();
        Optional<Ticket> ticket = ticketDao.findById(2L);
        System.out.println(ticket);
    }

    private static void checkSaveTicket() {
        TicketDao ticketDao = TicketDao.getInstance();
        Ticket ticket = new Ticket();
        ticket.setPassengerNo("1234567");
        ticket.setPassengerName("Name");
//        ticket.setFlight(3L);
        ticket.setSeatNo("B3");
        ticket.setCost(BigDecimal.TEN);
        Ticket savedTicket = ticketDao.save(ticket);
        System.out.println(savedTicket);
    }

    private static void checkUpdateTicket() {
        TicketDao ticketDao = TicketDao.getInstance();
        Optional<Ticket> maybeTicket = ticketDao.findById(2L);
        System.out.println(maybeTicket);

        maybeTicket.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });
    }

    private static void checkDeleteTicket() {
        TicketDao ticketDao = TicketDao.getInstance();
        boolean deleteResult = ticketDao.delete(56L);
        System.out.println(deleteResult);
    }
}
