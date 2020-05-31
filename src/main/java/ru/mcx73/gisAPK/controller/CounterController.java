package ru.mcx73.gisAPK.controller;

// Если возникнет exception - клиенту вернется код  500 Internal Server Error, поэтому не нужно все действия оборачивать в try-catch

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// иначе пришлось бы выполнять лишнюю работу, использовать @ResponseBody для ответа, указывать тип отправки JSON

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mcx73.gisAPK.entity.additionals.СounterCategory;
import ru.mcx73.gisAPK.service.CounterService;

// Названия методов могут быть любыми, главное не дублировать их имена и URL mapping
@RestController
public class CounterController {

    private final CounterService counterService; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    private final Long defaultId = 1l; // l - чтобы тип числа был Long, иначе будет ошибка компиляции


    // для статистика всгда получаем только одну строку с id=1 (согласно таблице БД)
    @GetMapping("/stat")
    public ResponseEntity<СounterCategory> findById() {

        // можно не использовать ResponseEntity, а просто вернуть коллекцию, код все равно будет 200 ОК
        return  ResponseEntity.ok(counterService.findById(defaultId));
    }
}
