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
import java.util.Optional;

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
    public List<Concept> getAllConcepts( @RequestParam(required = false) String name) {
        if (name != null) {
            return this.conceptRepository.findByName(name);
        }
        return this.conceptRepository.findAll();
    }

    @PostMapping(path = "")
    public Concept createConcept(@RequestBody Concept concept) {
        Optional<Concept> conceptOptional = conceptRepository.existsByName(concept.getName());
        if (conceptOptional.isPresent()) {
            throw new RuntimeException("Concept already exists");
        }
        return conceptService.createConcept(concept);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        return ResponseEntity.of(conceptRepository.findById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> modifyConceptData(@RequestBody Concept concept, @PathVariable Long id){
        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        concept.setId(id);
        conceptService.save(concept);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteConcept(@PathVariable Long id) {
        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        conceptService.delete(conceptOptional.get());
        return ResponseEntity.ok().build();
    }
}

// {
//     "name":"no maternidad", 
//     "description":"descripcionnn",
//     "status":"AVAILABLE",
//     "remunerable": "false"
// }
