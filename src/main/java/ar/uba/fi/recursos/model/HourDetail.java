package ar.uba.fi.recursos.model;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HourDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime startTime;

    private ZonedDateTime endTime;

    private Double hours;

    private HourDetailStatus status;


    // public void setId(Long id){
    //     this.id = id;
    // }

    // public Long getId(){
    //     return this.id;
    // }

    // public void setStartTime(ZonedDateTime startTime){
    //     this.startTime = startTime;
    // }

    // public ZonedDateTime getStartTime(){
    //     return this.startTime;
    // }

    // public void setEndTime(ZonedDateTime endTime){
    //     this.endTime = endTime;
    // }

    // public ZonedDateTime getEndTime(){
    //     return this.endTime;
    // }

    // public void setStatus(HourDetailStatus status){
    //     this.status = status;
    // }

    // public HourDetailStatus getStatus(){
    //     return this.status;
    // }

}


// {
//     "startTime":"2017-01-19T15:26+05:30", 
//     "endTime":"2018-01-19T15:26+05:30",
//     "status":"BORRADOR",
//     "hours": "24"
// }