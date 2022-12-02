package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;
import ar.uba.fi.recursos.repository.ConceptRepository;
import ar.uba.fi.recursos.service.ConceptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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
    public List<Concept> getAllConcepts(@RequestParam(required = false) String name) {
        if (name != null) {
            return this.conceptRepository.findByName(name);
        }
        return this.conceptRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createConcept(@RequestBody Concept concept) {
        if (conceptRepository.existsByName(concept.getName())) {
            return ResponseEntity.badRequest().body("A concept with the same name already exists");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(conceptService.createConcept(concept));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        return ResponseEntity.of(conceptRepository.findById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> modifyConcept(@RequestBody Concept concept, @PathVariable Long id) {
        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Concept existingConcept = conceptOptional.get();
        if (!existingConcept.getName().equals(concept.getName()) && conceptRepository.existsByName(concept.getName())) {
            return ResponseEntity.badRequest().body("A concept with the same name already exists");
        }

        concept.setId(id);
        return ResponseEntity.ok(conceptService.save(concept));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteConcept(@PathVariable Long id) {
        Optional<Concept> conceptOptional = conceptRepository.findById(id);

        if (!conceptOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Concept existingConcept = conceptOptional.get();
        existingConcept.setStatus(ConceptStatus.UNAVAILABLE);
        return ResponseEntity.ok(conceptService.save(existingConcept));
    }
}

// {
// "name":"no maternidad",
// "description":"descripcionnn",
// "status":"AVAILABLE",
// "remunerable": "false"
// }
