package team.lc01.lb02.c.evss.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.lc01.lb02.c.evss.dao.OrderDao;
import team.lc01.lb02.c.evss.domain.order.OrderRecord;
import team.lc01.lb02.c.evss.domain.order.Status;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
//auto generate constructor for orderDao and others with autowired
@RequiredArgsConstructor(onConstructor__ = {@Autowired})
public class OrderService {

    private final OrderDao orderDao;
    private final VehicleService vehicleService;

    public Boolean checkExistingOpenOrder(String email) {
        try {
            Optional<OrderRecord> order = orderDao.findOpenOrdersByEmail(email);
            return order.isPresent();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * init order
     *
     * @param email     user's email
     * @param startTime
     * @param sn
     * @return Order
     */
    public OrderRecord initOrder(String email, Date startTime, String sn) {
        if (checkExistingOpenOrder(email)) {
            return null;
        }
        OrderRecord orderRecord = new OrderRecord(email, startTime.getTime(), Arrays.stream(vehicleService.getLocationBySN(sn)).toList(), sn);

        orderRecord = orderDao.save(orderRecord);

        return orderRecord;
    }

    public OrderRecord initOrder(String email, long startTime, String sn) {
        if (checkExistingOpenOrder(email)) {
            return null;
        }

        OrderRecord orderRecord = new OrderRecord(email, startTime, Arrays.stream(vehicleService.getLocationBySN(sn)).toList(), sn);

        orderRecord = orderDao.save(orderRecord);

        return orderRecord;
    }

    /**
     * add one location
     *
     * @param email user's email
     * @return
     */
    public OrderRecord addLocation(String email) {
        if (!checkExistingOpenOrder(email)) {
            return null;
        }
        OrderRecord orderRecord = orderDao.findOpenOrdersByEmail(email).get();
        //add one location
        List<List<Double>> locations = orderRecord.getLocations();
        locations.add(Arrays.stream(vehicleService.getLocationBySN(orderRecord.getSn())).toList());
        orderRecord.setLocations(locations);
        orderRecord = orderDao.save(orderRecord);

        return orderRecord;
    }

    /**
     * end one order
     *
     * @param email   user's email
     * @param endTime
     * @return
     */
    public OrderRecord endOrder(String email, Date endTime) {
        if (!checkExistingOpenOrder(email)) {
            return null;
        }
        OrderRecord orderRecord = orderDao.findOpenOrdersByEmail(email).get();
        //add end_time
        orderRecord.setEndTime(endTime.getTime());

        //add end location
        List<List<Double>> locations = orderRecord.getLocations();
        locations.add(Arrays.stream(vehicleService.getLocationBySN(orderRecord.getSn())).toList());
        orderRecord.setLocations(locations);

        //add money info(half hour 1£)
        double money = (endTime.getTime() - orderRecord.getStartTime()) / 1000.0 / 60.0 / 30.0;
        orderRecord.setMoney(money);

        orderRecord.setStatus(Status.Closed);

        orderDao.save(orderRecord);

        return orderRecord;
    }

    public OrderRecord endOrder(String email, long endTime) {
        if (!checkExistingOpenOrder(email)) {
            return null;
        }
        OrderRecord orderRecord = orderDao.findOpenOrdersByEmail(email).get();
        //add end_time
        orderRecord.setEndTime(endTime);

        //add end location
        List<List<Double>> locations = orderRecord.getLocations();
        locations.add(Arrays.stream(vehicleService.getLocationBySN(orderRecord.getSn())).toList());
        orderRecord.setLocations(locations);

        //add money info(half hour 1£)
        double money = (endTime - orderRecord.getStartTime()) / 1000.0 / 60.0 / 30.0;
        orderRecord.setMoney(money);

        orderRecord.setStatus(Status.Closed);

        orderDao.save(orderRecord);

        return orderRecord;
    }

    public List<OrderRecord> getClosedOrdersByEmail(String email) {
        return orderDao.findClosedOrdersByEmail(email);
    }

    public OrderRecord getOpenOrderByEmail(String email) {
        return orderDao.findOpenOrdersByEmail(email).get();
    }

    public List<OrderRecord> getAllOrders() {
        return orderDao.findAll();
    }

    public List<OrderRecord> getOrdersByEmail(String email) {
        return orderDao.findOrdersByEmail(email);
    }

    public List<OrderRecord> getOrdersBySn(String sn) {
        return orderDao.findOrdersBySn(sn);
    }

    public List<OrderRecord> getOrdersByType(int type) {
        return orderDao.findOrdersByType(type);
    }

    public List<OrderRecord> getOrdersByTimeRange(Date moreThanTime, Date lessThanTime) {
        return orderDao.findOrderByTimeRange(moreThanTime, lessThanTime);
    }

    public List<OrderRecord> getOrdersByTimeRange(long moreThanTime, long lessThanTime) {
        return orderDao.findOrderByTimeRange(moreThanTime, lessThanTime);
    }
}
