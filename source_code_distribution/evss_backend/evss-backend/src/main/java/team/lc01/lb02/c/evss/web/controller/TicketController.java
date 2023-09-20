package team.lc01.lb02.c.evss.web.controller;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import team.lc01.lb02.c.evss.util.URL;
import team.lc01.lb02.c.evss.util.CODE;
import team.lc01.lb02.c.evss.util.Status;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.domain.Ticket;
import team.lc01.lb02.c.evss.domain.Vehicle;
import team.lc01.lb02.c.evss.service.UserService;
import org.springframework.stereotype.Controller;
import team.lc01.lb02.c.evss.service.TicketService;
import team.lc01.lb02.c.evss.service.VehicleService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping(value = URL.TICKET)
@ResponseBody
public class TicketController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    /**
     * Add a ticket with a user email, vehicle serial number and a content
     * will set the status to 3 - reported when added
     * @param email String - A user email
     * @param sn String - A vehicle serial number
     * @param content String - A ticket content
     * @return Map - results
     */
    @RequestMapping(value = URL.TICKET_ADD, method = RequestMethod.POST)
    public Map<String, Object> addTicketController(String email, String sn, String content) {
        Map<String, Object> result = new HashMap<>();
        Ticket ticket;

        try {
            ticket = new Ticket();
            User user = userService.findUserByEmail(email);
            Vehicle vehicle = vehicleService.findVehicleBySN(sn);
            Set<Ticket> v_old_tickets = vehicle.getTickets();
            Set<Ticket> v_new_tickets;
            Set<Ticket> u_old_tickets = user.getTickets();
            Set<Ticket> u_new_tickets;
            ticket.setUserEmail(email);
            ticket.setContent(content);
            ticket.setVehicleSN(sn);

            if(v_old_tickets == null) {
                v_new_tickets = new HashSet<>(){{add(ticket);}};
                u_new_tickets = new HashSet<>(){{add(ticket);}};
                user.setTickets(u_new_tickets);
                vehicle.setTickets(v_new_tickets);
            } else {
                v_new_tickets = new HashSet<>(v_old_tickets){{add(ticket);}};
                u_new_tickets = new HashSet<>(u_old_tickets){{add(ticket);}};
                user.setTickets(u_new_tickets);
                vehicle.setTickets(v_new_tickets);
            }
            vehicle = vehicleService.changeStatusBySN(sn, Status.REPORTED);
            ticketService.add(ticket);
            vehicleService.add(vehicle);
            userService.updateUser(user);
            result.put("code", CODE.SUCCESS);
            result.put("msg", "ticket added success");
            result.put("data", ticket);

        } catch (Exception e) {
            System.out.printf(e.getMessage());
            result.put("msg", "parameters invalid");
            result.put("code", CODE.WRONG_INPUT);
        }

        return result;

    }

    /**
     * Get all tickets
     *
     * @return Map - results
     */
    @RequestMapping(value = URL.TICKET_ALL, method = RequestMethod.GET)
    public Map<String, Object> allTicketsController() {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, Ticket> tickets = ticketService.findAllTickets();

        return getResultMap(result, tickets);
    }

    /**
     * Get a specified user tickets
     *
     * @param email String - A user email
     * @return Map - results
     */
    @RequestMapping(value = URL.TICKET_BYUSER, method = RequestMethod.GET)
    public Map<String, Object> userTicketsController(String email) {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, Ticket> tickets = ticketService.findTicketByUserEmail(email);

        return getResultMap(result, tickets);
    }

    /**
     * Get a specified vehicle tickets
     *
     * @param sn String - A vehicle serial number
     * @return Map - results
     */
    @RequestMapping(value = URL.TICKET_BYVEHICLE, method = RequestMethod.GET)
    public Map<String, Object> vehicleTicketsController(String sn) {
        Map<String, Object> result = new HashMap<>();
        Map<Integer, Ticket> tickets = vehicleService.findAllTickets(sn);

        return getResultMap(result, tickets);
    }

    /**
     * Common parts
     *
     */
    private Map<String, Object> getResultMap(Map<String, Object> result, Map<Integer, Ticket> tickets) {
        if(tickets == null) {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "no ticket");
        } else {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "get tickets success");
            result.put("data", tickets);
        }

        return result;
    }
}
