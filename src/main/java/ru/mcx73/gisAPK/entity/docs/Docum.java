package ru.mcx73.gisAPK.entity.docs;

import lombok.Setter;
import ru.mcx73.gisAPK.entity.additionals.Category;
import ru.mcx73.gisAPK.entity.additionals.StatusDoc;
import ru.mcx73.gisAPK.entity.users.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Table(name = "d_docum")
@Entity
public class Docum {
    private Long id;
    private Category category;
    private User user;
    private StatusDoc statusDoc;
    private Date dateDoc;
    private Date dateCreatDoc;
    private String comment;
    private Integer completed; // 1 = принят, 0 = не принят

    // указываем, что поле заполняется в БД
    // нужно, когда добавляем новый объект и он возвращается уже с новым id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    @Basic
    @Column(name = "date_add")
    public Date getDateDoc() {
        return dateDoc;
    }

    @Basic
    @Column(name = "date_creat")
    public Date getDateCreatDoc() {
        return dateCreatDoc;
    }

    @Basic
    @Column(name = "completed")
    public Integer getCompleted() {
        return completed;
    } // 1 = true, 0 = false

    // ссылка на объект User
    // одна задача имеет ссылку на один объект
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public User getUser() {
        return user;
    }

    // ссылка на объект StatusDoc
    // одна задача имеет ссылку на один объект
    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public StatusDoc getStatusDoc() {
        return statusDoc;
    }

    // ссылка на объект Category
    // одна задача имеет ссылку на один объект
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // по каким полям связывать (foreign key)
    public Category getCategory() {
        return category;
    }

    @OneToMany(mappedBy = "docum", cascade = CascadeType.ALL, orphanRemoval = true)
    List<TableDocum> documList = new ArrayList<>();

}
