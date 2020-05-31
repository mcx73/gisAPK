package ru.mcx73.gisAPK.controller;


import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mcx73.gisAPK.entity.additionals.StatusDoc;
import ru.mcx73.gisAPK.search.StatusDocSearchValues;
import ru.mcx73.gisAPK.service.StatusDocService;

import java.util.List;
import java.util.NoSuchElementException;

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// иначе пришлось бы выполнять лишнюю работу, использовать @ResponseBody для ответа, указывать тип отправки JSON
@RestController
@RequestMapping ("/status") // базовый адрес
public class StatusDocController {
    //константа для статуса нового документа
    private final Long defaultId = 1l; // l - чтобы тип числа был Long, иначе будет ошибка компиляции

    // доступ к данным из БД
    private StatusDocService statusDocService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public StatusDocController(StatusDocService statusDocService) {
        this.statusDocService = statusDocService;
    }

    //получим отсортированный список из БД
    @GetMapping("/all")
    public List<StatusDoc> findAll() {

        return statusDocService.findAll();
    }

    //добавляем новый статус документа
    @PostMapping("/add")
    public ResponseEntity<StatusDoc> add(@RequestBody StatusDoc statusDoc){

        // проверка на обязательные параметры
        if (statusDoc.getId() != null && statusDoc.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("избыточный параметр: id должен быть равен нулю", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (statusDoc.getName() == null || statusDoc.getName().trim().length() == 0) {
            return new ResponseEntity("пропущенный параметр: наименование", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(statusDocService.add(statusDoc));

    }

    //обновляем (изменяем) параметр статуса
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody StatusDoc statusDoc){

        // проверка на обязательные параметры
        if (statusDoc.getId() == null || statusDoc.getId() == 0) {
            return new ResponseEntity("пропущенный параметр: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение имя
        if (statusDoc.getName() == null || statusDoc.getName().trim().length() == 0) {
            return new ResponseEntity("пропущенный параметр: наименование", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        statusDocService.update(statusDoc);


        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }

    //ищем по id конкретный статус
    @GetMapping("/id/{id}")
    public ResponseEntity<StatusDoc> findById(@PathVariable Long id) {

        StatusDoc statusDoc = null;

        try{
            statusDoc = statusDocService.findById(id);
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(statusDoc);

        //для нового дока нужно получить сразу по 1 id, как ниже
       // return  ResponseEntity.ok(statRepository.findById(defaultId).get());
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            statusDocService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<StatusDoc>> search(@RequestBody StatusDocSearchValues statusDocSearchValues){

        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(statusDocService.findByName(statusDocSearchValues.getName()));
    }
}
