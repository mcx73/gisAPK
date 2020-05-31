package ru.mcx73.gis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.mcx73.gis.entity.Role;
import ru.mcx73.gis.entity.User;
import ru.mcx73.gis.repository.RoleRepository;
import ru.mcx73.gis.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

 /*
  Содержит методы для бизнес-логики приложения
 */

@Service
public class UserService implements UserDetailsService {
    /*
     работает режим «один Entity Manager на одну транзакцию приложения».
     */
    @PersistenceContext
    private EntityManager em;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }

        return user;
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    /*
    saveUser(User user)
    Сначала происходит поиск в БД по имени пользователя, если пользователь с таким именем уже существует метод заканчивает работу.
    Если имя пользователя не занято, добавляется роль ROLE_USER. Чтобы не хранить пароль в «сыром» виде он предварительно
    хэшируется с помощью bCryptPasswordEncoder. Затем новый пользователь сохраняется в БД.
     */

    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        /*
        ниже смотрим при регистрации, если справочник роли пуст, то это 1 запуск. тогда создаем роли
        и первому юзеру назначаем права по умолчанию - админ, остальные юзеры будут по умолчанию
        иметь права - юзер
         */
        List<Role> list = roleService.AllRole();
        Role role;

        if(list == null || list.size() == 0){
            roleService.saveUserEndRoles();
            role = new Role(1L, "ROLE_USER");
        }else{
            role = new Role(3L, "ROLE_USER");
        }


        user.setRoles(Collections.singleton(role));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public List<User> usergtList(Long idMin) {
        return em.createQuery("SELECT u FROM User u WHERE u.id > :paramId", User.class)
                .setParameter("paramId", idMin).getResultList();
    }

    public void updateProfile(User user, String username, String email, String password, String roleslist) {
        String userEmail = user.getEmail();
        String userName = user.getUsername();
        String userPassword = user.getPassword();

        /*
        проверим перед записью изменились ли реквизиты по отношению к текущему пользователю
         */
        boolean isNameChanged = (!username.equals(userName));
        boolean isPasswordChanged = (!username.equals(userPassword));
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isPasswordChanged && !password.equals("")) {
            user.setPassword(bCryptPasswordEncoder.encode(password));
        }
        if (isNameChanged) {
            user.setUsername(username);
        }
        if (isEmailChanged) {
            user.setEmail(email);

        }
        Set<Role> roleSet = new HashSet<Role>();

        if (roleslist.equals("ADMIN")) {
            Role role = new Role(1L, "ROLE_USER");
            //user.setRoles(Collections.singleton(role));
            roleSet.add(role);
            user.setRoles(roleSet);
            userRepository.save(user);
        }
        if (roleslist.equals("MODERATOR")) {
            Role role = new Role(2L, "ROLE_USER");
            //user.setRoles(Collections.singleton(role));
            roleSet.add(role);
            user.setRoles(roleSet);
            userRepository.save(user);
        }
        if (roleslist.equals("USER")) {
            Role role = new Role(3L, "ROLE_USER");
            //user.setRoles(Collections.singleton(role));
            roleSet.add(role);
            user.setRoles(roleSet);
            userRepository.save(user);
        }
    }
}
