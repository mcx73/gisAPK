package ru.mcx73.gisAPK.entity.additionals;


import lombok.Setter;

import javax.persistence.*;

@Setter
@Table(name = "a_descript")
@Entity
public class Descript {
    private Long id;
    private String name;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 1050)
    public String getName() {
        return name;
    }

}
