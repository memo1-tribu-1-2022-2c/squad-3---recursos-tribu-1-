package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TaskRepository extends CrudRepository<Task, Long> {

    @Override
    List<Task> findAll();
}