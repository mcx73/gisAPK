package ru.mcx73.gis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mcx73.gis.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    /*
    выполнять стандартные запросы к БД
    метод поиска пользователя в БД по имени
    При необходимости можно использовать аннотацию Query над методом и писать запросы
    @Query(value = "SELECT nextval(pg_get_serial_sequence('t_user', 'id'))", nativeQuery = true)
    Long getNextId();

     */

}
