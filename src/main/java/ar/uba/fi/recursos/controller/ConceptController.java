package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.repository.ConceptRepository;
import ar.uba.fi.recursos.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "/concept")
@EnableSwagger2
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @Autowired
    private ConceptRepository conceptRepository;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Concept> getAllConcepts() {
        return this.conceptRepository.findAll();
    }

    @PostMapping(path = "")
    public Concept createConcept(@RequestBody Concept concept) {
        return conceptService.createConcept(concept);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        return ResponseEntity.of(conceptRepository.findById(id));
    }
}

// {
//     "name":"no maternidad", 
//     "description":"descripcionnn",
//     "status":"AVAILABLE",
//     "remunerable": "false"
// }
