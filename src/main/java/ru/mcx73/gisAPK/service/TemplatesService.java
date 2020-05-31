package ru.mcx73.gisAPK.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.additionals.TemplatesTableDocum;
import ru.mcx73.gisAPK.entity.docs.Docum;
import ru.mcx73.gisAPK.repo.DocumRepository;
import ru.mcx73.gisAPK.repo.TemplatesRepository;

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
public class TemplatesService {
    private final TemplatesRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public TemplatesService(TemplatesRepository repository) {
        this.repository = repository;
    }

    public List<TemplatesTableDocum> findAll() {
        return repository.findAll();
    }

    public TemplatesTableDocum add(TemplatesTableDocum templatesTableDocum) {
        return repository.save(templatesTableDocum); // метод save обновляет или создает новый объект, если его не было
    }

    public TemplatesTableDocum update(TemplatesTableDocum templatesTableDocum){
        return repository.save(templatesTableDocum); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public List findByParams(Long descriptId, Long categoryId){
        return repository.findByParams(descriptId, categoryId);
    }

    public TemplatesTableDocum findById(Long id){
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }


}
