package ar.uba.fi.recursos.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_tr")
public class TimeRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private TimeRegisterTypeOfActivity typeOfActivity;
    private Long activityId; // Id of the task or concept
    private LocalDate date;
    private Double hours;
    private Long hourDetailId;
}

// {
// "typeOfActivity":"TASK",
// "activityId":"121",
// "date": "2020-01-01"
// "hours":"8",
// "hourDetailId": 1
// }
