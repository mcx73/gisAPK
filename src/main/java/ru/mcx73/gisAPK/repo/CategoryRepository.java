package ru.mcx73.gisAPK.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mcx73.gisAPK.entity.additionals.Category;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // если name == null или =='', то получим все значения
    @Query("SELECT c FROM Category c where " +
            "(:name is null or :name='' or lower(c.name) like lower(concat('%', :name,'%')))  " +
            "order by c.name asc")
    List<Category> findByName(@Param("name") String name);

    // получить все значения, сортировка по названию
    List<Category> findAllByOrderByNameAsc();
}
