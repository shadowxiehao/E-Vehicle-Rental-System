package team.lc01.lb02.c.evss.dao;

import java.util.List;
import team.lc01.lb02.c.evss.domain.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketDao extends BaseDao<Ticket> {

    public TicketDao() {
        super(Ticket.class);
    }

    /**
     * Find all tickets
     *
     * @return List - All tickets or 'null' if no ticket
     */
    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = em.createNamedQuery("Ticket.viewAll", Ticket.class).getResultList();

        return tickets.size() > 0 ? tickets : null;
    }

    /**
     * Find all tickets belong to a user
     *
     * @return List - All tickets belong to a user or 'null' if no ticket
     */
    public List<Ticket> findTicketsByUserEmail(String email) {
        List<Ticket> tickets = em.createNamedQuery("Ticket.findByUserEmail", Ticket.class)
                .setParameter("email", email).getResultList();

        return tickets.size() > 0 ? tickets : null;
    }
}
