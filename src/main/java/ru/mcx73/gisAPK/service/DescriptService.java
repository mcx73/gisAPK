package ru.mcx73.gisAPK.service;

import org.springframework.stereotype.Service;
import ru.mcx73.gisAPK.entity.additionals.Descript;
import ru.mcx73.gisAPK.repo.DescriptRepository;

import javax.transaction.Transactional;
import java.util.List;

// всегда нужно создавать отдельный класс Service для доступа к данным, даже если кажется,
// что мало методов или это все можно реализовать сразу в контроллере
// Такой подход полезен для будущих доработок и правильной архитектуры (особенно, если работаете с транзакциями)
@Service

// все методы класса должны выполниться без ошибки, чтобы транзакция завершилась
// если в методе возникнет исключение - все выполненные операции откатятся (Rollback)
@Transactional
public class DescriptService {
    private final DescriptRepository repository; // сервис имеет право обращаьтся к репозиторию (БД)

    public DescriptService(DescriptRepository repository) {
        this.repository = repository;
    }

    public List<Descript> findAll() {
        return repository.findAll();
    }

    public Descript add(Descript descript) {
        return repository.save(descript); // метод save обновляет или создает новый объект, если его не было
    }

    public Descript update(Descript descript){
        return repository.save(descript); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public List<Descript> findByName(String text){
        return repository.findByName(text);
    }

    public Descript findById(Long id){
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }

    public List<Descript> findAllByOrderByNameAsc(){
        return repository.findAllByOrderByNameAsc();
    }


}
