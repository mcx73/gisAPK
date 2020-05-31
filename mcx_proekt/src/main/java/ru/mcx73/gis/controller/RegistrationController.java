package ru.mcx73.gis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.service.UserService;
import javax.validation.Valid;

/*
контроллер для страницы регистрации
 */

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        /*
        В GET запросе на страницу добавляется новый пустой объект класса User. Это сделано для того, чтобы при POST запросе
        не доставать данные из формы регистрации по одному (username, password, passwordComfirm),
        а сразу получить заполненный объект userForm.
         */
        model.addAttribute("userForm", new User());

        return "registration";
    }

    /*
    Аннотация Valid проверяет выполняются ли ограничения, установленные на поля, в данном случае длина не меньше 2 символов
     */
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";

        }

        /*
        При удачном сохранении пользователя – переходим на главную страницу.
         */

        return "redirect:/";
    }
}
