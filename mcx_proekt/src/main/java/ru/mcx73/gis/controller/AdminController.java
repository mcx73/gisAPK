package ru.mcx73.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.DocsRepository;
import ru.mcx73.gis.service.RoleServiceImpl;
import ru.mcx73.gis.service.UserService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    DocsRepository docsRepository;

    @Value("${upload.path}")
    private String uploadPath;

    /*
    получает данные всех пользователей и добавляет их на страницу.
     */
    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";

    }

    /*
    PathVariable. С помощью этой аннотации мы получаем отдельные части URL, для метод getUser() URL будет выглядеть следующим
     образом: http://localhost:8080/admin/gt/24, после перехода выведется список всех пользователей с id>24.
     */

    @GetMapping("/admin/gt/{userId}")
    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "admin";
    }

    /*
    AuthenticationPrincipal - получает пользователя с контекста, чтобы не искать его в БД
     */
    @GetMapping("/profile/{userId}")
    public String  getProfile(@PathVariable("userId") Long id, Model model) {
        User user = new User();
        user = userService.findUserById(id);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", "");
        model.addAttribute("passwordConfirm", "");

        Iterator<Role> iterator = user.getRoles().iterator();

        String r = "";
        while (iterator.hasNext()){
            r = iterator.next().getName();
            break;
        }
        model.addAttribute("roleUser", r);

        List<Role> addressList = roleService.AllRole();
        List<String> roleList = new ArrayList<>();
        for (Role roles : addressList) {
            roleList.add(roles.getName());
        }
        model.addAttribute("roleList", roleList);

        return "profile";
    }

    @PostMapping("/profile/{userId}")
    public String  updateProfile(@PathVariable("userId") Long id,
                                 @RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password,
                                 @RequestParam String passwordConfirm,
                                 @RequestParam String roleslist) {
            //User user;// = new User();
        User user = userService.findUserById(id);
            userService.updateProfile(user,username,email,passwordConfirm,roleslist);

        return "redirect:/admin";
    }
}
