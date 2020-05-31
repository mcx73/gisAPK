package ru.mcx73.gis.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.validation.constraints.Size;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/*
Entity говорит о том, что поля класса имеют отображение в БД, Table(name = «u_user») указывает с какой именно таблицей.
 */
@Entity
@Table(name = "u_users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /*
    параметр IDENTITY значит, что генерацией id будет заниматься БД. Существует другие стратегии.
    SEQUENCE – использует встроенный в базы данных, такие как PostgreSQL или Oracle, механизм генерации последовательных значений
     (sequence). TABLE – используется отдельная таблица с проинициализированным значениями ключей. Еще один вариант – AUTO,
     hibernate сам выберет из одну вышеописанных стратегий, но рекомендуется указывать стратегию явно.
     */

    @Size(min=2, message = "Не меньше 5 знаков")
    private String username;
    @Size(min=2, message = "Не меньше 5 знаков")
    private String password;
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    private String passwordConfirm;
    /*
    Поле, находящееся под аннотацией Transient, не имеет отображения в БД.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    /*
    FetchType.EAGER – «жадная» загрузка, т.е. список ролей загружается вместе с пользователем сразу
    (не ждет пока к нему обратятся).
     */


    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
