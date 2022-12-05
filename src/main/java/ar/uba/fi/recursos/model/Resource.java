package ar.uba.fi.recursos.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAlias;

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
public class Resource {

    @Id
    @JsonAlias("legajo")
    private Long id;

    @JsonAlias("nombre")
    private String name;

    @JsonAlias("apellido")
    private String surname;
}