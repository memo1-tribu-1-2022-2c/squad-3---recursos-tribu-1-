package ar.uba.fi.recursos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ar.uba.fi.recursos.model.Concept;
import ar.uba.fi.recursos.model.ConceptStatus;

@RepositoryRestResource
public interface ConceptRepository extends CrudRepository<Concept, Long> {

    @Override
    List<Concept> findAll();

    boolean existsByName(String name);

    List<Concept> findByName(String name);

    Optional<Concept> findByIdAndStatus(Long id, ConceptStatus status);
}
