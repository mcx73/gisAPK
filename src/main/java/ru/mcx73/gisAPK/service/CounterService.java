package ru.mcx73.gisAPK.service;

import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.additionals.СounterCategory;
import ru.mcx73.gisAPK.repo.CounterRepository;

import javax.transaction.Transactional;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class CounterService {

    private final CounterRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public CounterService(CounterRepository repository) {
        this.repository = repository;
    }

    public СounterCategory findById(Long id){
        return repository.findById(id).get();
    }
}
