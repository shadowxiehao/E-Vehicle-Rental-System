package team.lc01.lb02.c.evss.dao;


import org.springframework.stereotype.Repository;
import team.lc01.lb02.c.evss.domain.Balance;
import team.lc01.lb02.c.evss.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public class BalanceDao extends BaseDao<Balance> {

    public BalanceDao() {
        super(Balance.class);
    }

    public Optional<Balance> findBalanceByEmail(String email) {
        List<Balance> balances = em.createNamedQuery("Balance.findBalanceByEmail", Balance.class)
                .setParameter("email", email)
                .getResultList();
        return balances.size() == 1 ? Optional.of(balances.get(0)) : Optional.empty();
    }
}
