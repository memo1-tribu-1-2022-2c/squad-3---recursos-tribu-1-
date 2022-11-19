package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.HourDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface HourDetailRepository extends CrudRepository<HourDetail, Long> {

    @Override
    List<HourDetail> findAll();
}
