package ru.mcx73.gisAPK.controller;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mcx73.gisAPK.entity.docs.TableDocum;
import ru.mcx73.gisAPK.service.TableDocumService;

import java.util.List;
import java.util.NoSuchElementException;


// Названия методов могут быть любыми, главное не дублировать их имена и URL mapping
@RestController
@RequestMapping("/tabledocum") // базовый адрес
public class TableDocumController {

    private final TableDocumService tableDocumService; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)


    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public TableDocumController(TableDocumService tableDocumService, ConfigurableEnvironment environment) {
        this.tableDocumService = tableDocumService;
    }

    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<TableDocum>> findAll() {

        return  ResponseEntity.ok(tableDocumService.findAll());
    }

    // добавление
    @PostMapping("/add")
    public ResponseEntity<TableDocum> add(@RequestBody TableDocum tableDocum) {

        // проверка на обязательные параметры
        if (tableDocum.getId() != null && tableDocum.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("избыточный параметр: id должен быть равен нулю", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение comment
        //|| docum.getText().trim().length() == 0
        if (tableDocum.getDocum() == null ) {
            return new ResponseEntity("пропущен параметр: docum", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(tableDocumService.add(tableDocum)); // возвращаем созданный объект со сгенерированным id

    }

    // обновление
    @PutMapping("/update")
    public ResponseEntity<TableDocum> update(@RequestBody TableDocum tableDocum) {

        // проверка на обязательные параметры
        if (tableDocum.getId() == null || tableDocum.getId() == 0) {
            return new ResponseEntity("пропущен параметр: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (tableDocum.getDocum() == null) {
            return new ResponseEntity("пропущен параметр: docum", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        tableDocumService.update(tableDocum);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }

    // удаление по id
    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            tableDocumService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


    // получение объекта по id
    @GetMapping("/id/{id}")
    public ResponseEntity<TableDocum> findById(@PathVariable Long id) {

        TableDocum tableDocum = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try{
            tableDocum = tableDocumService.findById(id);
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(tableDocum);
    }

}
