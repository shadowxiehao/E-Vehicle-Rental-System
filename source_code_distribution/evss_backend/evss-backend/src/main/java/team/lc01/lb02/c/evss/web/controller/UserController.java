package team.lc01.lb02.c.evss.web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import team.lc01.lb02.c.evss.domain.User;
import team.lc01.lb02.c.evss.service.UserService;
import team.lc01.lb02.c.evss.util.CODE;
import team.lc01.lb02.c.evss.util.URL;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping(value = URL.USER)
@ResponseBody
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = URL.USER_ADD, method = RequestMethod.POST)
    public Map addUserController(@Valid @RequestBody User user,
                                 BindingResult bindingResult
//                                 HttpSession session,
//                                 @RequestParam("file") MultipartFile file
    ) {
        Map<String, Object> result = new HashMap<>();
//        if (!Util.isAdmin(session)) {
//            throw new WebSecurityException();
//        }
        if (user.getEmail() == null || user.getPassword() == null || user.getRole() == null) { //check valid
            result.put("code", CODE.WRONG_INPUT);
            result.put("data", bindingResult.getAllErrors());
            result.put("msg", "WRONG_INPUT");
        } else if (Objects.nonNull(user.getEmail()) && userService.isEmailAlreadyInUse(user.getEmail())) { //check email
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "email already in use");
            result.put("data", null);
        } else { //save user
            result.put("code", CODE.SUCCESS);
            User savedUser = userService.save(user);
            result.put("data", savedUser);
            result.put("msg", true);
        }
//        setProfilePicture(user, file);
        return result;
    }

    @RequestMapping(value = URL.USER_UPDATE, method = RequestMethod.POST)
    public Map updateUserController(@RequestBody User user
    ) {
        Map<String, Object> result = new HashMap<>();
        if (user != null && user.getEmail() != null) {
            User savedUser = userService.updateUser(user);
            if (savedUser == null) {
                result.put("code", CODE.NOT_FOUND);
                result.put("msg", "user not found");
                result.put("data", null);
            } else {
                result.put("code", CODE.SUCCESS);
                result.put("msg", "SUCCESS");
                result.put("data", savedUser);
            }
        } else {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);
        }
        return result;
    }

    @RequestMapping(value = URL.USER_DELETE, method = RequestMethod.POST)
    public Map deleteUserController(String email
    ) {
        Map<String, Object> result = new HashMap<>();

        if (email == null && email.equals("")) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);
        } else {
            User user = userService.deleteByEmail(email);
            if (user == null) {
                result.put("code", CODE.NOT_FOUND);
                result.put("msg", "NOT_FOUND");
                result.put("data", user);
            } else {
                result.put("code", CODE.SUCCESS);
                result.put("msg", "SUCCESS");
                result.put("data", user);
            }
        }
        return result;
    }

    @RequestMapping(value = URL.USER_GET, method = RequestMethod.GET)
    public Map getUserController(String email) {
        Map<String, Object> result = new HashMap<>();

        if (email != null && !email.equals("")) {
            User user = userService.findUserByEmail(email);
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", user);
        } else {
            result.put("code", CODE.FAILURE);
            result.put("msg", "FAILURE");
            result.put("data", null);
        }
        return result;
    }

    @RequestMapping(value = URL.USER_LOGIN_ByUser, method = RequestMethod.POST)
    public Map loginController(User user
    ) {
        Map<String, Object> result = new HashMap<>();
        //check user
        if (user == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);
        } else if (userService.checkLogin(user)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", userService.findUserByEmail(user.getEmail()));
        } else {
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "password not correct or other situations");
            result.put("data", null);
        }

        return result;
    }

    @RequestMapping(value = URL.USER_LOGIN, method = RequestMethod.POST)
    public Map<String, Object> loginController(String email,
                                               String password
    ) {
        Map<String, Object> result = new HashMap<>();
        User user = null;
        if (email != null && !email.equals("") && password != null) {
            user = new User();
            user.setEmail(email);
            user.setPassword(password);
        }
        //check user
        if (user == null) {
            result.put("code", CODE.WRONG_INPUT);
            result.put("msg", "WRONG_INPUT");
            result.put("data", null);
        } else if (userService.checkLogin(user)) {
            result.put("code", CODE.SUCCESS);
            result.put("msg", "SUCCESS");
            result.put("data", userService.findUserByEmail(user.getEmail()));
        } else {
            result.put("code", CODE.REFUSE_COMMAND);
            result.put("msg", "password not correct or other situations");
            result.put("data", null);
        }

        return result;
    }
//    private void setProfilePicture(User user, MultipartFile file) {
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                user.setProfilePicture(bytes);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
}

