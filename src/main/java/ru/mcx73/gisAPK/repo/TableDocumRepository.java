package ru.mcx73.gisAPK.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mcx73.gisAPK.entity.docs.Docum;
import ru.mcx73.gisAPK.entity.docs.TableDocum;

import java.util.List;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface TableDocumRepository extends JpaRepository<TableDocum, Long> {

    // учитываем, что параметр может быть null или пустым
    @Query("SELECT p FROM TableDocum p where " +
               "(:documId is null or p.docum.id=:documId)"
    )
    //искать по всем переданным параметрам (пустые параметры учитываться не будут)
    List<TableDocum> findByParams(
            @Param("documId") Docum documId


    );


}
