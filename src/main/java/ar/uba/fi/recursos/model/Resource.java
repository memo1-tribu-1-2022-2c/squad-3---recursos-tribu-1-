package ar.uba.fi.recursos.model;

import lombok.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource {

    @Id
    @JsonProperty("legajo")
    private Long id;

    @JsonProperty("Nombre")
    private String name;

    @JsonProperty("Apellido")
    private String surname;
}