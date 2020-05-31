package ru.mcx73.gisAPK.entity.additionals;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class СounterCategory {
    private Long id;
    private Long completedTotal;//проверенные
    private Long uncompletedTotal;//непроверенные
    private Long returnedTotal;//возвращенный на доработку

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Basic
    @Column(name = "completed_total")
    public Long getCompletedTotal() {
        return completedTotal;
    }

    @Basic
    @Column(name = "uncompleted_total")
    public Long getUncompletedTotal() {
        return uncompletedTotal;
    }

    @Basic
    @Column(name = "returned_total")
    public Long getReturnedTotal() {
        return returnedTotal;
    }


}
