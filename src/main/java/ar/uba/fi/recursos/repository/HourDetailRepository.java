package ar.uba.fi.recursos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ar.uba.fi.recursos.model.HourDetail;

@RepositoryRestResource
public interface HourDetailRepository extends CrudRepository<HourDetail, Long> {

    @Override
    List<HourDetail> findAll();

    List<HourDetail> findByWorkerIdOrderByStartTime(Long workerId);

    @Query(value = "select hd from HourDetail hd where hd.workerId = :workerId and ((hd.startTime between :minDate and :maxDate) or (hd.endTime between :minDate and :maxDate))")
    List<HourDetail> findAllWithOverlappingDates(Long workerId, LocalDate minDate, LocalDate maxDate);
}
