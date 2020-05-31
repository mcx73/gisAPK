package ru.mcx73.gisAPK.entity.additionals;


import lombok.Setter;

import javax.persistence.*;

@Setter
@Table(name = "a_status")
@Entity
public class StatusDoc {
    private Long id;
    private String name;
    private String color;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Basic
    @Column(name = "color")
    public String getColor() {
        return color;
    }

    // обратная ссылка на коллекцию Docums не нужна

}

