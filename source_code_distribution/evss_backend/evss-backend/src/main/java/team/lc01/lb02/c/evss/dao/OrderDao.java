package team.lc01.lb02.c.evss.dao;

import org.springframework.stereotype.Repository;
import team.lc01.lb02.c.evss.domain.order.OrderRecord;
import team.lc01.lb02.c.evss.domain.order.Status;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDao extends BaseDao<OrderRecord> {

    public OrderDao() {
        super(OrderRecord.class);
    }

    public List<OrderRecord> findOrdersByEmail(String email) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByEmail", OrderRecord.class)
                .setParameter("email", email)
                .getResultList();
        return orders;
    }

    public Optional<OrderRecord> findOpenOrdersByEmail(String email) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByEmailAndStatus", OrderRecord.class)
                .setParameter("email", email)
                .setParameter("status", Status.Open)
                .getResultList();
        return orders.size() == 1 ? Optional.of(orders.get(0)) : Optional.empty();
    }

    public List<OrderRecord> findClosedOrdersByEmail(String email) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByEmailAndStatus", OrderRecord.class)
                .setParameter("email", email)
                .setParameter("status", Status.Open)
                .getResultList();
        return orders;
    }

    public List<OrderRecord> findOrdersBySn(String sn) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findBySn", OrderRecord.class)
                .setParameter("sn", sn)
                .getResultList();
        return orders;
    }

    public List<OrderRecord> findOrdersByType(int type) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByType", OrderRecord.class)
                .setParameter("type", type)
                .getResultList();
        return orders;
    }

    public List<OrderRecord> findOrderByTimeRange(long moreThanTime, long lessThanTime) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByTimeRange", OrderRecord.class)
                .setParameter("moreThanTime", moreThanTime)
                .setParameter("lessThanTime", lessThanTime)
                .getResultList();
        return orders;
    }

    public List<OrderRecord> findOrderByTimeRange(Date moreThanTime, Date lessThanTime) {
        List<OrderRecord> orders = em.createNamedQuery("Order.findByTimeRange", OrderRecord.class)
                .setParameter("moreThanTime", moreThanTime.getTime())
                .setParameter("lessThanTime", lessThanTime.getTime())
                .getResultList();
        return orders;
    }
}