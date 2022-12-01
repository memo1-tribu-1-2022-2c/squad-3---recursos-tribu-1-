package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;
import ar.uba.fi.recursos.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConceptService {

    @Autowired
    private ConceptRepository conceptRepository;

    public Concept createConcept(Concept concept) {
        concept.setStatus(ConceptStatus.AVAILABLE);
        return conceptRepository.save(concept);
    }

    public Concept save(Concept concept) {
        return conceptRepository.save(concept);
    }

    public void delete(Concept concept) {
        conceptRepository.delete(concept);
    }
}
