package ru.mcx73.gisAPK.controller;

// Если возникнет exception - вернется код  500 Internal Server Error, поэтому не нужно все действия оборачивать в try-catch

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// иначе пришлось бы выполнять лишнюю работу, использовать @ResponseBody для ответа, указывать тип отправки JSON

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mcx73.gisAPK.entity.docs.Docum;
import ru.mcx73.gisAPK.repo.DocumRepository;
import ru.mcx73.gisAPK.search.DocumSearchValues;
import ru.mcx73.gisAPK.service.DocumService;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

// Названия методов могут быть любыми, главное не дублировать их имена и URL mapping
@RestController
@RequestMapping("/docum") // базовый адрес
public class DocumController {

    private final DocumService documService; // сервис для доступа к данным (напрямую к репозиториям не обращаемся)


    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public DocumController(DocumService documService, ConfigurableEnvironment environment) {
        this.documService = documService;
    }

    // получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Docum>> findAll() {

        return  ResponseEntity.ok(documService.findAll());
    }

    // добавление
    @PostMapping("/add")
    public ResponseEntity<Docum> add(@RequestBody Docum docum) {

        // проверка на обязательные параметры
        if (docum.getId() != null && docum.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("избыточный параметр: id должен быть равен нулю", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение comment
        //|| docum.getText().trim().length() == 0
        if (docum.getUser() == null ) {
            return new ResponseEntity("пропущен параметр: user", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(documService.add(docum)); // возвращаем созданный объект со сгенерированным id

    }

    // обновление
    @PutMapping("/update")
    public ResponseEntity<Docum> update(@RequestBody Docum docum) {

        // проверка на обязательные параметры
        if (docum.getId() == null || docum.getId() == 0) {
            return new ResponseEntity("пропущен параметр: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (docum.getUser() == null) {
            return new ResponseEntity("пропущен параметр: user", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        documService.update(docum);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }

    // удаление по id
    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            documService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }


    // получение объекта по id
    @GetMapping("/id/{id}")
    public ResponseEntity<Docum> findById(@PathVariable Long id) {

        Docum docum = null;

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try{
            docum = documService.findById(id);
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(docum);
    }

    // поиск по любым параметрам
    // TaskSearchValues содержит все возможные параметры поиска
    @PostMapping("/search")
    public ResponseEntity<Page<Docum>> search(@RequestBody DocumSearchValues documSearchValues) {

        // имитация загрузки (для тестирования индикаторов загрузки)
//        imitateLoading();

        // исключить NullPointerException
        String comment = documSearchValues.getComment() != null ? documSearchValues.getComment() : null;

        // конвертируем Boolean в Integer
        //Integer completed = documSearchValues.getCompleted() != null ?  documSearchValues.getCompleted() : null;
        Long userId = documSearchValues.getUserId() != null ? documSearchValues.getUserId() : null;
        Date dateDoc = documSearchValues.getDateDoc() != null ? documSearchValues.getDateDoc() : null;
        Long statusDocId = documSearchValues.getStatusDocId() != null ? documSearchValues.getStatusDocId() : null;
        Long categoryId = documSearchValues.getCategoryId() != null ? documSearchValues.getCategoryId() : null;

        String sortColumn = documSearchValues.getSortColumn() != null ? documSearchValues.getSortColumn() : null;
        String sortDirection = documSearchValues.getSortDirection() != null ? documSearchValues.getSortDirection() : null;

        Integer pageNumber = documSearchValues.getPageNumber() != null ? documSearchValues.getPageNumber() : null;
        Integer pageSize = documSearchValues.getPageSize() != null ? documSearchValues.getPageSize() : null;
        Integer completed = documSearchValues.getCompleted() != null ? documSearchValues.getCompleted() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().length() == 0 || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // объект сортировки
        Sort sort = Sort.by(direction, sortColumn);

        //содержит и сортировку и параметры постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = documService.findByParams(comment, completed, userId, dateDoc, statusDocId, categoryId, pageRequest);
       // List result = documService.findByParams(comment, completed, userId, dateDoc, statusDocId, categoryId);


        // результат запроса
        return ResponseEntity.ok(result);
        //return ResponseEntity.ok(documService.findByParams(comment, userId, dateDoc, statusDocId, categoryId));

    }

}
