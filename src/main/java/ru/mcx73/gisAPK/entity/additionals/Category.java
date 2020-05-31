package ru.mcx73.gisAPK.entity.additionals;


import lombok.Setter;

import javax.persistence.*;

@Setter
@Table(name = "a_category")
@Entity
public class Category {
    private Long id;
    private String name;
    private Long completedCount;
    private Long uncompletedCount;
    private Long returnedTotal;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Basic
    @Column(name = "completed_count")
    public Long getCompletedCount() {
        return completedCount;
    }

    @Basic
    @Column(name = "uncompleted_count")
    public Long getUncompletedCount() {
        return uncompletedCount;
    }

    @Basic
    @Column(name = "returned_count")
    public Long getReturnedTotal() {
        return returnedTotal;
    }


}
