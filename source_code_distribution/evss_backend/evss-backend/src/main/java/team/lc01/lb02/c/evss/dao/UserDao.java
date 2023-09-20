package team.lc01.lb02.c.evss.dao;

import org.springframework.stereotype.Repository;
import team.lc01.lb02.c.evss.domain.User;

import java.util.List;
import java.util.Optional;


@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    public Optional<User> findUserByEmail(String email) {
        List<User> users = em.createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        List<User> users = em.createNamedQuery("User.findByEmailAndPassword", User.class)
                .setParameter("email", email)
                .setParameter("password", password)
                .getResultList();
        return users.size() == 1 ? Optional.of(users.get(0)) : Optional.empty();
    }
}