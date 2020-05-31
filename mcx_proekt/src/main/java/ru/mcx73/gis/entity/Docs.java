package ru.mcx73.gis.entity;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.File;

@Entity
public class Docs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String filename;

    private String icon;

    public Docs() {
    }

    public Docs(User user) {
        this.user = user;
    }

    public String getAuthorName() {
        return user != null ? user.getUsername() : "<none>";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
        String ext1 = FilenameUtils.getExtension(filename);
        if(ext1.equals("docx") || ext1.equals("doc") || ext1.equals("rtf") || ext1.equals("odt")){
            this.icon = "/resources/img/doc.jpg";
        }else if(ext1.equals("xls") || ext1.equals("xlsx")){
            this.icon = "/resources/img/excel.png";
        }else if(ext1.equals("pdf")){
            this.icon = "/resources/img/pdf.jpg";
        }else if(ext1.equals("zip") || ext1.equals("rar") || ext1.equals("7z") || ext1.equals("tar")){
            this.icon = "/resources/img/zip.jpg";
        }else {
            this.icon = "/resources/img/zero.jpg";
        }

    }
}
