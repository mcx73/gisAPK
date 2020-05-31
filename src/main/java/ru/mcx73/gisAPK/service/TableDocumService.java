package ru.mcx73.gisAPK.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.docs.Docum;
import ru.mcx73.gisAPK.entity.docs.TableDocum;
import ru.mcx73.gisAPK.repo.TableDocumRepository;

import javax.transaction.Transactional;
import java.util.List;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class TableDocumService {
    private final TableDocumRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public TableDocumService(TableDocumRepository repository) {
        this.repository = repository;
    }

    public List<TableDocum> findAll() {
        return repository.findAll();
    }

    public TableDocum add(TableDocum tableDocum) {
        return repository.save(tableDocum); // метод save обновляет или создает новый объект, если его не было
    }

    public TableDocum update(TableDocum tableDocum){
        return repository.save(tableDocum); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public List findByParams(Docum documId){
        return repository.findByParams(documId);
    }

    public TableDocum findById(Long id){
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }


}
