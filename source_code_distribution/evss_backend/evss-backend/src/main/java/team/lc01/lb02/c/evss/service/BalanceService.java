package team.lc01.lb02.c.evss.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.lc01.lb02.c.evss.dao.BalanceDao;
import team.lc01.lb02.c.evss.dao.BaseDao;
import team.lc01.lb02.c.evss.dao.UserDao;
import team.lc01.lb02.c.evss.domain.Balance;
import team.lc01.lb02.c.evss.domain.User;

import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    private BalanceDao balanceDao;

    /**
     *
     * @param email
     * @return Balance
     */
    public Balance initBalance(String email){
        Balance balance = new Balance(email, 0);
        Balance savedBalance = balanceDao.save(balance);
        return savedBalance;
    }

    /**
     *
     * @param email
     * @return Balance
     */
    public Balance deleteByEmail(String email){
        Optional<Balance> balanceO = balanceDao.findBalanceByEmail(email);
        if (!balanceO.isPresent()) {
            return null;
        }
        // get current balance object
        Balance balance = balanceO.get();
        balanceDao.delete(balance.getId());
        return balance;
    }

    /**
     *
     * @param id
     * @return boolean
     */
    public boolean delete(long id) {
        balanceDao.delete(id);
        return true;
    }

    /**
     *
     * @param email
     * @return Balance
     */
    public Balance findBalanceObjectByEmail(String email) {
        Optional<Balance> balanceO = balanceDao.findBalanceByEmail(email);
        if (!balanceO.isPresent()) {
            return null;
        }
        // get current balance object
        Balance balance = balanceO.get();
        return balance;
    }

    /**
     *
     * @param email
     * @param value
     * @return Balance
     */
    public Balance topUp(String email, Double value){
        Optional<Balance> balanceO = balanceDao.findBalanceByEmail(email);
        if (!balanceO.isPresent()) {
            return null;
        }
        // get current balance object
        Balance balance = balanceO.get();
        // get current balance
        double money = balance.getBalance();
        // add money to topup
        balance.setBalance(money + value);
        // update to database
        Balance savedbalance = balanceDao.save(balance);
        return savedbalance;
    }

    /**
     *
     * @param email
     * @param value
     * @return Balance
     */
    public Balance payment(String email, double value){
        Optional<Balance> balanceO = balanceDao.findBalanceByEmail(email);
        if (!balanceO.isPresent()) {
            return null;
        }
        // get current balance object
        Balance balance = balanceO.get();
        // get current balance
        double money = balance.getBalance();
        // check the balance is enough to pay
        if (money < value){
            return new Balance("xxx", Integer.MIN_VALUE);
        }
        // enough, then pay the money
        balance.setBalance(money - value);
        // update to database
        Balance savedbalance = balanceDao.save(balance);
        return savedbalance;
    }

    /**
     *
     * @param balance
     * @return double
     */
    public Double getBalance(Balance balance){
        return balance.getBalance();
    }
}
