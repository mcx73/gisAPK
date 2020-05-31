package ru.mcx73.gisAPK.controller;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mcx73.gisAPK.entity.additionals.Category;
import ru.mcx73.gisAPK.search.CategorySearchValues;
import ru.mcx73.gisAPK.service.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;

// используем @RestController вместо обычного @Controller, чтобы все ответы сразу оборачивались в JSON
// иначе пришлось бы выполнять лишнюю работу, использовать @ResponseBody для ответа, указывать тип отправки JSON
@RestController
@RequestMapping ("/category") // базовый адрес
public class CategoryController {
    // доступ к данным из БД
    private CategoryService categoryService;

    // автоматическое внедрение экземпляра класса через конструктор
    // не используем @Autowired ля переменной класса, т.к. "Field injection is not recommended "
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //получим отсортированный список из БД
    @GetMapping("/all")
    public List<Category> findAll() {

        return categoryService.findAllByOrderByNameAsc();
    }

    //добавляем новую категорию
    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category){

        // проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            // id создается автоматически в БД (autoincrement), поэтому его передавать не нужно, иначе может быть конфликт уникальности значения
            return new ResponseEntity("избыточный параметр: id должен быть равен нулю", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение name
        if (category.getName() == null || category.getName().trim().length() == 0) {
            return new ResponseEntity("пропущенный параметр: наименование", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(categoryService.add(category));
    }

    //обновляем (изменяем) параметр категории
    @PutMapping("/update")
    public ResponseEntity update(@RequestBody Category category){

        // проверка на обязательные параметры
        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("пропущенный параметр: id", HttpStatus.NOT_ACCEPTABLE);
        }

        // если передали пустое значение title
        if (category.getName() == null || category.getName().trim().length() == 0) {
            return new ResponseEntity("пропущенный параметр: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // save работает как на добавление, так и на обновление
        categoryService.update(category);

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)

    }

    //ищем по id конкретную категорию
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        Category category = null;

        try{
            category = categoryService.findById(id);
        }catch (NoSuchElementException e){ // если объект не будет найден
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return  ResponseEntity.ok(category);
    }

    // параметр id передаются не в BODY запроса, а в самом URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {

        // можно обойтись и без try-catch, тогда будет возвращаться полная ошибка (stacktrace)
        // здесь показан пример, как можно обрабатывать исключение и отправлять свой текст/статус
        try {
            categoryService.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            return new ResponseEntity("id="+id+" не найден", HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK); // просто отправляем статус 200 (операция прошла успешно)
    }

    // поиск по любым параметрам CategorySearchValues
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues){

        // если вместо текста будет пусто или null - вернутся все категории
        return ResponseEntity.ok(categoryService.findByName(categorySearchValues.getName()));
    }



}
