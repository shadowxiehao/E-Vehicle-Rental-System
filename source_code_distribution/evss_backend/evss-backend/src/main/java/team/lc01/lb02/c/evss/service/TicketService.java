package team.lc01.lb02.c.evss.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import team.lc01.lb02.c.evss.dao.TicketDao;
import team.lc01.lb02.c.evss.domain.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TicketService {

    @Autowired
    private TicketDao ticketDao;

    /**
     * Add a ticket
     *
     * @param ticket Ticket - A ticket
     * @return Ticket
     */
    public Ticket add(Ticket ticket) {
        return ticketDao.save(ticket);
    }

    /**
     * Find all tickets
     *
     * @return Map - tickets or 'null' if no ticket
     */
    public Map<Integer, Ticket> findAllTickets() {
        if(ticketDao.findAllTickets() == null) {
            return null;
        } else {
            List<Ticket> ticketList = ticketDao.findAllTickets();
            Map<Integer, Ticket> tickets = new HashMap<>();
            for(int i = 0; i < ticketList.size(); i++) {
                tickets.put(i, ticketList.get(i));
            }
            return tickets;
        }
    }

    /**
     * Find a ticket by a user email
     *
     * @param email String - An user email
     * @return Map - tickets or 'null' if no ticket
     */
    public Map<Integer, Ticket> findTicketByUserEmail(String email) {
        if(ticketDao.findTicketsByUserEmail(email) == null) {
            return null;
        } else {
            List<Ticket> ticketList = ticketDao.findTicketsByUserEmail(email);
            Map<Integer, Ticket> tickets = new HashMap<>();
            for(int i = 0; i < ticketList.size(); i++) {
                tickets.put(i, ticketList.get(i));
            }
            return tickets;
        }

    }
}
