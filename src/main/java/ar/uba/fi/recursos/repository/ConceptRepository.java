package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.Concept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ConceptRepository extends CrudRepository<Concept, Long> {

    @Override
    List<Concept> findAll();
}

