package ru.mcx73.gisAPK.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mcx73.gisAPK.entity.additionals.Category;
import ru.mcx73.gisAPK.entity.additionals.StatusDoc;
import ru.mcx73.gisAPK.entity.additionals.TemplatesTableDocum;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface TemplatesRepository extends JpaRepository<TemplatesTableDocum, Long> {
    // если name == null или =='', то получим все значения
    @Query("SELECT c FROM TemplatesTableDocum c where " +
            "(:descriptId is null or c.descript.id=:descriptId) and " +
            "(:categoryId is null or c.category.id=:categoryId)")

    List<TemplatesTableDocum> findByParams(
            @Param("descriptId") Long descriptId,
            @Param("categoryId") Long categoryId

    );

    // получить все значения, сортировка по названию
    List<TemplatesTableDocum> findAllByOrderByIdAsc();

}
