package team.lc01.lb02.c.evss.util;


public class URL {

    public static final String ROOT = "/";
    // -----------User------------------------
    public static final String USER = "/user";

    public static final String USER_ADD = "/add";
    public static final String USER_UPDATE = "/update";
    public static final String USER_DELETE = "/delete";
    public static final String USER_GET = "/get";
    public static final String USER_LOGIN = "/login";
    public static final String USER_LOGIN_ByUser = "/login/user";

    // -----------Order------------------------
    public static final String ORDER = "/order";
    public static final String ORDER_INIT = "/init";
    public static final String ORDER_End = "/end";
    public static final String ORDER_ADD_LOCATION = "/add/location";
    public static final String ORDER_GET_ALL_ORDERS = "/get/all";
    public static final String ORDER_GET_ALL_ORDERS_BY_EMAIL = "/get/all/email";
    public static final String ORDER_GET_OPEN_ORDERS_BY_EMAIL = "/get/open/email";
    public static final String ORDER_GET_ALL_CLOSED_ORDERS_BY_EMAIL = "/get/all/closed/email";
    public static final String ORDER_GET_ALL_ORDERS_BY_SN = "/get/all/sn";
    public static final String ORDER_GET_ALL_ORDERS_BY_TYPE = "/get/all/type";
    public static final String ORDER_GET_ALL_ORDERS_BY_TimeRange = "/get/all/time";

    // -----------Vehicle------------------------

    public static final String VEHICLE = "/vehicle";

    public static final String VEHICLE_ADD = "/add";

    public static final String VEHICLE_FIND = "/find";

    public static final String VEHICLE_STATUS = "/status";

    public static final String VEHICLE_MAINTAIN = "/maintain";

    public static final String VEHICLE_LOC = "/location";

    public static final String VEHICLE_USER = "/user";

    public static final String VEHICLE_CHARGE = "/charge";

    // -----------Ticket------------------------

    public static final String TICKET = "/ticket";

    public static final String TICKET_ADD = "/add";

    public static final String TICKET_ALL = "/all";

    public static final String TICKET_BYUSER = "/user";

    public static final String TICKET_BYVEHICLE = "/vehicle";

    // -----------Balance------------------------
    public static final String BALANCE = "/balance";

    public static final String BALANCE_TOPUP = "/topup";

    public static final String BALANCE_PAYMENT = "/payment";

    public static final String BALANCE_SHOWBALANCE = "/showbalance";


}
