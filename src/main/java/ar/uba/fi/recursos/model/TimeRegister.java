package ar.uba.fi.recursos.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


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

    @ManyToOne(fetch = FetchType.LAZY)
    private HourDetail hourDetail;
}


// {
//     "typeOfActivity":"TASK",
//     "activityId":"121",
//     "hours":"8",
//     "date": "2020-01-01"
// }


