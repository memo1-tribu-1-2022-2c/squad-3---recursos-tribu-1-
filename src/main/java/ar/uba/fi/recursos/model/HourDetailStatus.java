package ar.uba.fi.recursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum HourDetailStatus {

    @JsonProperty("borrador")
    @JsonAlias("draft")
    DRAFT,

    @JsonProperty("emitido")
    @JsonAlias("issued")
    ISSUED,

    @JsonProperty("rechazado")
    @JsonAlias("refused")
    REFUSED,

    @JsonProperty("aprobado")
    @JsonAlias("approved")
    APPROVED
}
