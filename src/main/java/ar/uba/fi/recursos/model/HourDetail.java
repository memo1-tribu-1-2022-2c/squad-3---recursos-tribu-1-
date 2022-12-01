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
@Table(name = "tbl_hd")
public class HourDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    private Long workerId;

    private LocalDate startTime;
    
    private LocalDate endTime;
    
    private Double hours;
    
    private HourDetailStatus status;

    private HourDetailType type;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hourDetail")
    private List<TimeRegister> timeRegisters;

    public void addTimeRegister(TimeRegister timeRegister) {
        timeRegisters.add(timeRegister);
    }

    public void removeTimeRegister(Long timeRegisterId) {
        timeRegisters.removeIf(timeRegister -> timeRegister.getId().equals(timeRegisterId));
    }
}
