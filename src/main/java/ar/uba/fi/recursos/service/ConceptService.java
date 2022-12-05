package ar.uba.fi.recursos.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.recursos.exceptions.ExistingConceptNameException;
import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;
import ar.uba.fi.recursos.repository.ConceptRepository;

@Service
public class ConceptService {

    @Autowired
    private ConceptRepository conceptRepository;

    public Concept createConcept(Concept conceptToCreate) {
        if (existsByName(conceptToCreate.getName()))
            throw new ExistingConceptNameException("Ya existe un concepto con el nombre: " + conceptToCreate.getName());

        conceptToCreate.setStatus(ConceptStatus.AVAILABLE);
        return conceptRepository.save(conceptToCreate);
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

    public Concept findById(Long id) {
        return conceptRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("No existe un concepto con id: " + id);
        });
    }

    public Concept modifyConcept(Long conceptId, Concept modifiedConcept) {
        Concept foundConcept = findById(conceptId);

        if (!modifiedConcept.getName().equals(foundConcept.getName()) && existsByName(modifiedConcept.getName()))
            throw new ExistingConceptNameException("Ya existe un concepto con el nombre: " + modifiedConcept.getName());

        modifiedConcept.setId(conceptId);
        return save(modifiedConcept);
    }

    public Concept deleteConcept(Long conceptId) {
        Concept foundConcept = findById(conceptId);
        foundConcept.setStatus(ConceptStatus.UNAVAILABLE);
        return save(foundConcept);
    }
}
