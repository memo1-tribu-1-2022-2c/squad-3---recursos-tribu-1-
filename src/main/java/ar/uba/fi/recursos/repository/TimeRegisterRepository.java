package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.TimeRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TimeRegisterRepository extends CrudRepository<TimeRegister, Long> {

    @Override
    List<TimeRegister> findAll();
}