package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;
import ar.uba.fi.recursos.repository.ConceptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public List<Concept> findAll() {
        return conceptRepository.findAll();
    }

    public List<Concept> findByName(String name) {
        return conceptRepository.findByName(name);
    }

    public boolean existsByName(String name) {
        return conceptRepository.existsByName(name);
    }

    public Optional<Concept> findById(Long id) {
        return conceptRepository.findById(id);
    }
}
