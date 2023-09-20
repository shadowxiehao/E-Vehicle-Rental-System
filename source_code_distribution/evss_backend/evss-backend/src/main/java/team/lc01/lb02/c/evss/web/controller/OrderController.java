package team.lc01.lb02.c.evss.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.lc01.lb02.c.evss.domain.order.OrderRecord;
import team.lc01.lb02.c.evss.service.OrderService;
import team.lc01.lb02.c.evss.util.CODE;
import team.lc01.lb02.c.evss.util.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping(value = URL.ORDER)
@ResponseBody
@RequiredArgsConstructor(onConstructor__ = @Autowired)
public class OrderController {
    private final OrderService orderService;

    @RequestMapping(value = URL.ORDER_INIT, method = RequestMethod.POST)
    public Map initOrderController(String email,
                                   long startTime,
                                   String sn
    ) {
        Map<String, Object> result = new HashMap<>();
        if (!(StringUtils.hasText(email) && startTime != 0 && StringUtils.hasText(sn))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }
        if (orderService.checkExistingOpenOrder(email)) {
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "email already has an open order");
            result.put("data", null);

            return result;
        }
        OrderRecord orderRecord = orderService.initOrder(email, startTime, sn);
        if (Objects.nonNull(orderRecord)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", orderRecord);
        } else {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "NOT_FOUND");
            result.put("data", "");
        }

        return result;
    }

    @RequestMapping(value = URL.ORDER_End, method = RequestMethod.POST)
    public Map endOrderController(String email,
                                  long endTime
    ) {
        Map<String, Object> result = new HashMap<>();
        if (!(StringUtils.hasText(email) && endTime != 0)) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }
        if (!orderService.checkExistingOpenOrder(email)) {
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "email has no open order");
            result.put("data", null);

            return result;
        }
        OrderRecord orderRecord = orderService.endOrder(email, endTime);
        if (Objects.nonNull(orderRecord)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", orderRecord);
        } else {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "NOT_FOUND");
            result.put("data", "");
        }

        return result;
    }

    @RequestMapping(value = URL.ORDER_ADD_LOCATION, method = RequestMethod.POST)
    public Map addLocationController(String email
    ) {
        Map<String, Object> result = new HashMap<>();
        if (!(StringUtils.hasText(email))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }
        if (!orderService.checkExistingOpenOrder(email)) {
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "email has no open order");
            result.put("data", null);

            return result;
        }
        OrderRecord orderRecord = orderService.addLocation(email);
        if (Objects.nonNull(orderRecord)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", orderRecord);
        } else {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "NOT_FOUND");
            result.put("data", "");
        }

        return result;
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_ORDERS, method = RequestMethod.GET)
    public Map getAllOrdersByEmail() {
        Map<String, Object> result = new HashMap<>();

        List<OrderRecord> orders = orderService.getAllOrders();

        return getResultMapByCheckOrders(result, orders);
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_ORDERS_BY_EMAIL, method = RequestMethod.GET)
    public Map getAllOrdersByEmail(String email
    ) {
        Map<String, Object> result = new HashMap<>();

        if (!(StringUtils.hasText(email))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        List<OrderRecord> orders = orderService.getOrdersByEmail(email);

        return getResultMapByCheckOrders(result, orders);
    }

    @RequestMapping(value = URL.ORDER_GET_OPEN_ORDERS_BY_EMAIL, method = RequestMethod.GET)
    public Map getAllOpenOrdersByEmail(String email
    ) {
        Map<String, Object> result = new HashMap<>();

        if (!(StringUtils.hasText(email))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        OrderRecord orderRecord = orderService.getOpenOrderByEmail(email);

        return getResultMapByCheckOrders(result, orderRecord);
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_CLOSED_ORDERS_BY_EMAIL, method = RequestMethod.GET)
    public Map getAllClosedOrdersByEmail(String email
    ) {
        Map<String, Object> result = new HashMap<>();

        if (!(StringUtils.hasText(email))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        List<OrderRecord> orders = orderService.getClosedOrdersByEmail(email);

        return getResultMapByCheckOrders(result, orders);
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_ORDERS_BY_SN, method = RequestMethod.GET)
    public Map getOrdersBySn(String sn
    ) {
        Map<String, Object> result = new HashMap<>();

        if (!(StringUtils.hasText(sn))) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        List<OrderRecord> orders = orderService.getOrdersBySn(sn);

        return getResultMapByCheckOrders(result, orders);
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_ORDERS_BY_TYPE, method = RequestMethod.GET)
    public Map getOrdersByType(int type
    ) {
        Map<String, Object> result = new HashMap<>();

        if (type == 0) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        List<OrderRecord> orders = orderService.getOrdersByType(type);

        return getResultMapByCheckOrders(result, orders);
    }

    @RequestMapping(value = URL.ORDER_GET_ALL_ORDERS_BY_TimeRange, method = RequestMethod.GET)
    public Map getOrdersByTimeRange(long startTime,
                                    long endTime
    ) {
        Map<String, Object> result = new HashMap<>();

        if (startTime == 0 || endTime == 0) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);

            return result;
        }

        List<OrderRecord> orders = orderService.getOrdersByTimeRange(startTime, endTime);

        return getResultMapByCheckOrders(result, orders);
    }

    private Map getResultMapByCheckOrders(Map<String, Object> result, List<OrderRecord> orders) {
        if (Objects.nonNull(orders) && orders.size() > 0) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", orders);
        } else {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "NOT_FOUND");
            result.put("data", "");
        }
        return result;
    }

    private Map getResultMapByCheckOrders(Map<String, Object> result, OrderRecord orderRecord) {
        if (Objects.nonNull(orderRecord)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", orderRecord);
        } else {
            result.put("code", CODE.NOT_FOUND);
            result.put("msg", "NOT_FOUND");
            result.put("data", "");
        }
        return result;
    }
}
