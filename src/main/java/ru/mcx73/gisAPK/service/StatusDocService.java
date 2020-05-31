package ru.mcx73.gisAPK.service;

import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.additionals.StatusDoc;
import ru.mcx73.gisAPK.repo.StatusDocRepository;

import javax.transaction.Transactional;
import java.util.List;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class StatusDocService {
    private StatusDocRepository statusDocRepository; // сервис имеет право обращаьтся к репозиторию (БД)

    public StatusDocService(StatusDocRepository repository) {
        this.statusDocRepository = repository;
    }

    public List<StatusDoc> findAll() {
        return statusDocRepository.findAll();
    }

    public StatusDoc add(StatusDoc statusDoc) {
        return statusDocRepository.save(statusDoc); // метод save обновляет или создает новый объект, если его не было
    }

    public StatusDoc update(StatusDoc statusDoc){
        return statusDocRepository.save(statusDoc); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id){
        statusDocRepository.deleteById(id);
    }

    public StatusDoc findById(Long id){
        return statusDocRepository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }

    public List<StatusDoc> findByName(String text){
        return statusDocRepository.findByName(text);
    }


}
