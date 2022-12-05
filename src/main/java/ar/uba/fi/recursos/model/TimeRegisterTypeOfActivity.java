package ar.uba.fi.recursos.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum TimeRegisterTypeOfActivity {

    @JsonProperty("tarea")
    @JsonAlias("task")
    TASK,

    @JsonProperty("concepto")
    @JsonAlias("concept")
    CONCEPT
}
