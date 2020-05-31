package ru.mcx73.gisAPK.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.docs.Docum;
import ru.mcx73.gisAPK.repo.DocumRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class DocumService {

    private final DocumRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public DocumService(DocumRepository repository) {
        this.repository = repository;
    }

    public List<Docum> findAll() {
        return repository.findAll();
    }

    public Docum add(Docum docum) {
        return repository.save(docum); // метод save обновляет или создает новый объект, если его не было
    }

    public Docum update(Docum docum){
        return repository.save(docum); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Page findByParams(String comment,Integer completed, Long userId, Date dateDoc, Long statusDocId, Long categoryId, PageRequest paging){
        return repository.findByParams(comment, completed, userId, dateDoc, statusDocId, categoryId, paging);
    }

    public Docum findById(Long id){
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }


}
