package ar.uba.fi.recursos.model;

import lombok.*;

import java.time.ZonedDateTime;

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
    
    private ZonedDateTime startTime; //

    private ZonedDateTime endTime; //

    private Double estimatedHours;

    private Double realHours;

}


