package ru.mcx73.gisAPK.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mcx73.gisAPK.entity.docs.Docum;

import java.util.Date;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface DocumRepository extends JpaRepository<Docum, Long> {

    // учитываем, что параметр может быть null или пустым
    @Query("SELECT p FROM Docum p where " +
            "(:comment is null or :comment='' or lower(p.comment) like lower(concat('%', :comment,'%'))) and" +
            "(:completed is null or p.completed=:completed) and " +
            "(:userId is null or p.user.id=:userId) and " +
            "(:dateDoc is null or p.dateDoc=:dateDoc) and " +
            "(:statusDocId is null or p.statusDoc.id=:statusDocId) and " +
            "(:categoryId is null or p.category.id=:categoryId)"
    )
    //искать по всем переданным параметрам (пустые параметры учитываться не будут)
    //Page оборачиваем для постраничного учета
    // Pageable нужен знать с какой страницы брать и сколько элементов брать
    Page<Docum> findByParams(
            @Param("comment") String comment,
            @Param("completed") Integer completed,
            @Param("userId") Long userId,
            @Param("dateDoc") Date dateDoc,
            @Param("statusDocId") Long statusDocId,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );

}
