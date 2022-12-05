package ar.uba.fi.recursos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ar.uba.fi.recursos.model.Concept;

@RepositoryRestResource
public interface ConceptRepository extends CrudRepository<Concept, Long> {

    @Override
    List<Concept> findAll();

    boolean existsByName(String name);

    List<Concept> findByName(String name);
}
