package ar.uba.fi.recursos.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tbl_hd.id")
    private HourDetail hd;

    private Long taskId;
    
    private Double hours;
    
    // private Collection<Date> dates;

}

/*
{
    "taskId":"1",
    "hours":"2"
}
 */