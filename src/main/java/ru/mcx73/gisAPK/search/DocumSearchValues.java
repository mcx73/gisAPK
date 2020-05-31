package ru.mcx73.gisAPK.search;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// возможные значения, по которым можно искать задачи + значения сортировки
public class DocumSearchValues {
    // поля поиска (все типы - объектные, не примитивные. Чтобы можно было передать null)
    private String comment;
    private Long userId;
    private Date dateDoc;
    private Long statusDocId;
    private Long categoryId;
    private Integer completed;

    //постраничность номер страницы и размер списка на странице
    private Integer pageNumber;
    private Integer pageSize;

    //столбец сотрировки и направление сортировки
    private String sortColumn;
    private String sortDirection;

}
