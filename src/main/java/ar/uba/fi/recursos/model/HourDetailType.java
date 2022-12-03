package ar.uba.fi.recursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum HourDetailType {

    @JsonProperty("semanal")
    @JsonAlias("weekly")
    WEEKLY,

    @JsonProperty("quincenal")
    @JsonAlias("biweekly")
    BIWEEKLY,

    @JsonProperty("mensual")
    @JsonAlias("monthly")
    MONTHLY
}