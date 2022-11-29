package ar.uba.fi.recursos.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private ConceptStatus status;

    private Boolean remunerable;


    // public void setId(Long id){
    //     this.id = id;
    // }

    // public Long getId(){
    //     return this.id;
    // }

    // public void setName(String name){
    //     this.name = name;
    // }

    // public String getName(){
    //     return this.name;
    // }

    // public void setDescription(String description){
    //     this.description = description;
    // }

    // public String getDescription(){
    //     return this.description;
    // }

    // public void setStatus(ConceptStatus status){
    //     this.status = status;
    // }

    // public ConceptStatus getStatus(){
    //     return this.status;
    // }

    // public void setRemunerable(Boolean remunerable){
    //     this.remunerable = remunerable;
    // }

    // public Boolean getRemunerable(){
    //     return this.remunerable;
    // }

}

