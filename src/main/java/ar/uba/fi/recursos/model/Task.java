package ar.uba.fi.recursos.model;

import lombok.*;

import java.time.LocalDate;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //

    private String name; //

    private String description; //

    private String status; //
    
    private LocalDate startTime; //

    private LocalDate endTime; //

    private Double estimatedHours;

    private Double realHours;

}


