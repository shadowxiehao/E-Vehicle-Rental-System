package team.lc01.lb02.c.evss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.lc01.lb02.c.evss.dao.UserDao;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.util.Encryption;
import team.lc01.lb02.c.evss.util.UpdateUtil;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private BalanceService balanceService;

    public User save(User user) {
        user.setPassword(Encryption.encrypt(user.getPassword()));
        if (user.getProfilePicture() == null) {
            user.setProfilePicture("".getBytes());
        }
        if (user.getFirstName() == null) {
            user.setFirstName("");
        }
        if (user.getLastName() == null) {
            user.setLastName("");
        }
        User savedUser = userDao.save(user);
        if (savedUser != null) {
            // 创建用户的同时初始化balance
            balanceService.initBalance(savedUser.getEmail());
        }
        return savedUser;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findUserByEmail(String string) {
        return userDao.findUserByEmail(string).get();
    }

    public List<User> findAllExceptSelf(long id) {
        List<User> users = userDao.findAll();
        users.removeIf(i -> i.getId() == id);
        return users;
    }

    //update user (null value allowed except email)
    public User updateUser(User user) {
        String userEmail = user.getEmail();
        //根据userId找到原user实体
        Optional<User> oldUserO = userDao.findUserByEmail(userEmail);
        if (!oldUserO.isPresent()) {
            return null;
        }
        User oldUser = oldUserO.get();

        //process password if exist
        if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(Encryption.encrypt(user.getPassword()));
        }

        //将前端传来的不为空参数(也即是要修改值)copy覆盖原始对象属性值
        //修改的字段覆盖原对象
        UpdateUtil.copyNotNullProperties(user, oldUser);
        //更新到数据库
        User savedUser = userDao.save(oldUser);

        return savedUser;
    }

    public User deleteByEmail(String email) {
        //根据userId找到原user实体
        Optional<User> oldUserO = userDao.findUserByEmail(email);
        if (!oldUserO.isPresent()) {
            return null;
        }
        User oldUser = oldUserO.get();
        userDao.delete(oldUser.getId());
        // 删除用户的同时删除balance
        balanceService.deleteByEmail(email);
        return oldUser;
    }

    public void delete(long id) {
        userDao.delete(id);
    }


    public Boolean checkLogin(User user) {
        Optional<User> oFound = userDao.findUserByEmail(user.getEmail());
        if (!oFound.isPresent()) {
            return false;
        }
        User uFound = oFound.get();
        if (!Encryption.encrypt(user.getPassword()).equals(uFound.getPassword())) {
            return false;
        }
        return true;
    }

    public Optional<User> find(long id) {
        return userDao.find(id);
    }

    public boolean isEmailAlreadyInUse(String value) {
        return userDao.findUserByEmail(value).isPresent();
    }

}
