package ar.uba.fi.recursos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;
import ar.uba.fi.recursos.service.ConceptService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Validated
@RestController
@RequestMapping(path = "/concepts")
@EnableSwagger2
public class ConceptController {

    @Autowired
    private ConceptService conceptService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Concept>> getAllConcepts(@RequestParam(required = false) String conceptName) {
        if (conceptName == null)
            return ResponseEntity.ok(conceptService.findAll());
        return ResponseEntity.ok(conceptService.findByName(conceptName));
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createConcept(@RequestBody Concept concept) {
        if (conceptService.existsByName(concept.getName()))
            return ResponseEntity.badRequest().body("A concept with the same name already exists");
        return ResponseEntity.ok(conceptService.createConcept(concept));
    }

    @GetMapping(path = "/{conceptId}")
    public ResponseEntity<Concept> getConcept(@PathVariable Long conceptId) {
        return ResponseEntity.of(conceptService.findById(conceptId));
    }

    @PutMapping(path = "/{conceptId}")
    public ResponseEntity<Object> modifyConcept(@RequestBody Concept concept, @PathVariable Long conceptId) {
        Optional<Concept> conceptOptional = conceptService.findById(conceptId);

        if (!conceptOptional.isPresent())
            return ResponseEntity.notFound().build();

        Concept existingConcept = conceptOptional.get();
        if (!concept.getName().equals(existingConcept.getName()) && conceptService.existsByName(concept.getName()))
            return ResponseEntity.badRequest().body("A different concept with the desired new name already exists");

        concept.setId(conceptId);
        return ResponseEntity.ok(conceptService.save(concept));
    }

    @DeleteMapping(path = "/{conceptId}")
    public ResponseEntity<Object> deleteConcept(@PathVariable Long conceptId) {
        Optional<Concept> conceptOptional = conceptService.findById(conceptId);

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
