package ru.mcx73.gis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import ru.mcx73.gis.service.UserService;

/*
Настройки безопасности
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    /*
    accessDeniedHandler - нужен нам для самостоятельной обработки 403 ошибки. когда юзер попробует зайти туда
    куда ему нельзя
     */
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    настраивается доступ к различным ресурсам сайта. В качестве параметров метода antMatchers() передаем пути,
    для которых хотим установить ограничение. Затем указываем, пользователям с какой ролью будет доступна эта страница/страницы
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    //Доступ только для не зарегистрированных пользователей
                    .antMatchers("/registration").not().fullyAuthenticated()
                    //Доступ только для пользователей с ролью Администратор
                    .antMatchers("/admin/**").hasAuthority("ADMIN")
                    .antMatchers("/mfc").hasAnyAuthority("MODERATOR", "ADMIN")
                    .antMatchers("/docs").hasAnyAuthority("USER", "ADMIN")
                    //Доступ разрешен всем пользователей
                    .antMatchers("/", "/resources/**").permitAll()
                    //Все остальные страницы требуют аутентификации
                    .anyRequest().authenticated()
                .and()
                    //Настройка для входа в систему
                    .formLogin()
                    .loginPage("/login")
                    //Перенарпавление на главную страницу после успешного входа
                    .defaultSuccessUrl("/", false)
                    .permitAll()
                .and()
                .rememberMe()
                .and()
                    .logout()
                    .permitAll()
                    .logoutSuccessUrl("/")
        .and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);


    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }
}
