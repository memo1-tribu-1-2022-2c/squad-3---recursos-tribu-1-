package ar.uba.fi.recursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum ConceptStatus {

    @JsonProperty("disponible")
    @JsonAlias("available")
    AVAILABLE,

    @JsonProperty("no disponible")
    @JsonAlias("unavailable")
    UNAVAILABLE
}
