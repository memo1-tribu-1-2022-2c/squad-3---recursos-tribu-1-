package ar.uba.fi.recursos.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_hd")
public class HourDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private ZonedDateTime startTime;
    
    private ZonedDateTime endTime;
    
    private Double hours;
    
    private HourDetailStatus status;

    @OneToMany(mappedBy = "hd", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TimeRegister> timeRegisters;

    public void addTimeRegister(TimeRegister timeRegister) {
        timeRegister.setHd(this);
        timeRegisters.add(timeRegister);
    }
}
