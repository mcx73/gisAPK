package ru.mcx73.gisAPK.entity.docs;

import lombok.Setter;
import ru.mcx73.gisAPK.entity.additionals.Descript;

import javax.persistence.*;

@Setter
@Table(name = "d_tabledocum")
@Entity
public class TableDocum {
    private Long id;
    private Docum docum;
    private Descript descript;
    private String filename;
    private String icon;

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "filename")
    public String getFilename() {
        return filename;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    // ссылка на объект Docum
    // много задач имеет ссылку на один объект
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docum_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Docum getDocum() {
        return docum;
    }

    // ссылка на объект Descript
    // одна задача имеет ссылку на один объект
    @ManyToOne
    @JoinColumn(name = "descript_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Descript getDescript() {
        return descript;
    }


}

