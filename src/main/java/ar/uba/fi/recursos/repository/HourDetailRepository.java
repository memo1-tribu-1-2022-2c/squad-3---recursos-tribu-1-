package ar.uba.fi.recursos.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ar.uba.fi.recursos.model.HourDetail;

@RepositoryRestResource
public interface HourDetailRepository extends CrudRepository<HourDetail, Long> {

    @Override
    List<HourDetail> findAll();

    List<HourDetail> findByWorkerId(Long workerId);
}
