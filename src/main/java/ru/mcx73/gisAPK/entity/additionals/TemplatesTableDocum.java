package ru.mcx73.gisAPK.entity.additionals;

import lombok.Setter;

import javax.persistence.*;

@Setter
@Table(name = "a_templates")
@Entity
public class TemplatesTableDocum {
    private Long id;
    private Category category;
    private Descript descript;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    // ссылка на объект Category
    // одна категория имеет ссылку на несколько объектов
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Category getCategory() {
        return category;
    }

    @ManyToOne
    @JoinColumn(name = "descript_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Descript getDescript() {
        return descript;
    }

}
