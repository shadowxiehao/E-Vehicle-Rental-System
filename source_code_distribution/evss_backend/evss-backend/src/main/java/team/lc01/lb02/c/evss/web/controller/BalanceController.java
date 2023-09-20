package team.lc01.lb02.c.evss.web.controller;

import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.lc01.lb02.c.evss.dao.BalanceDao;
import team.lc01.lb02.c.evss.dao.UserDao;
import team.lc01.lb02.c.evss.domain.Balance;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.service.BalanceService;
import team.lc01.lb02.c.evss.util.CODE;
import team.lc01.lb02.c.evss.util.URL;

import javax.print.attribute.standard.PrinterURI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(value = URL.BALANCE)
@ResponseBody
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    @RequestMapping(value = URL.BALANCE_TOPUP, method = RequestMethod.POST)
    public Map topUpController(String email, Double value){
        Map<String, Object> result = new HashMap<>();
        Balance savedbalance = balanceService.topUp(email,value);
        if (savedbalance == null){
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "User does not exist.");
            result.put("data", null);
        }else {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "Recharge success! Balance + " + value);
            result.put("data", savedbalance);
        }
        return result;
    }

    @RequestMapping(value = URL.BALANCE_PAYMENT, method = RequestMethod.POST)
    public Map paymentController(String email, Double value) {
        Map<String, Object> result = new HashMap<>();
        Balance savedbalance = balanceService.payment(email, value);
        if (savedbalance == null){
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "User does not exist.");
            result.put("data", null);
        }else if (balanceService.getBalance(savedbalance) == Integer.MIN_VALUE){
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "Not enough balance to pay.");
            result.put("data", null);
        }else {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "Payment success! Balance - " + value);
            result.put("data", savedbalance);
        }
        return result;
    }

    @RequestMapping(value = URL.BALANCE_SHOWBALANCE, method = RequestMethod.POST)
    public Map showbalanceController(String email) {
        Map<String, Object> result = new HashMap<>();
        Balance balance = balanceService.findBalanceObjectByEmail(email);
        if (balance == null){
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "User does not exist.");
            result.put("data", null);
        }else {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "Balance : " + balance.getBalance());
            result.put("data", balance);
        }
        return result;
    }
}
