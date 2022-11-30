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
    
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "hd_id")
    // private HourDetail hd;

    private Long taskId;
    
    private Double hours;

    @ElementCollection(targetClass=LocalDate.class)
    @CollectionTable(name="tbl_tr_dates", joinColumns=@JoinColumn(name="tr_id"))
    private List<LocalDate> dates;
}


// {
//     "taskId":"1",
//     "hours":"2",
//     "dates": ["2020-01-01", "2020-01-02"]
// }
