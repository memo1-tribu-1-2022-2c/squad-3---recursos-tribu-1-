package ar.uba.fi.recursos.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hourDetailId")
    private List<TimeRegister> timeRegisters;

    public void addTimeRegister(TimeRegister timeRegister) {
        timeRegisters.add(timeRegister);
    }

    public void removeTimeRegister(TimeRegister timeRegister) {
        timeRegisters.removeIf(id -> id.equals(timeRegister));
    }
}
