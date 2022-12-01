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

    private HourDetailStatus status;

    private HourDetailType type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hourDetail")
    private List<TimeRegister> timeRegisters;
    // Use of @OneToMany or @ManyToMany targeting an unmapped class: ar.uba.fi.recursos.model.HourDetail.timeRegistersIds[java.lang.Long]

    public void addTimeRegister(TimeRegister timeRegister) {
        timeRegisters.add(timeRegister);
    }

    public void removeTimeRegister(TimeRegister timeRegister) {
        timeRegisters.removeIf(id -> id.equals(timeRegister));
    }
}
