package ru.mcx73.gisAPK.repo;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.mcx73.gisAPK.entity.additionals.СounterCategory;

// принцип ООП: абстракция-реализация - здесь описываем все доступные способы доступа к данным
@Repository
public interface CounterRepository extends CrudRepository<СounterCategory, Long> {
}
