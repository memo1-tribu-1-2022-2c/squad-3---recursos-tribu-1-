package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.TimeRegister;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource
public interface TimeRegisterRepository extends CrudRepository<TimeRegister, Long> {

    @Override
    List<TimeRegister> findAll();

    @Query(value = "select tr from TimeRegister tr where tr.hourDetail.workerId = :workerId and tr.date between :minDate and :maxDate order by tr.date asc")
    List<TimeRegister> findTimeRegistersByDateIsGreaterThanEqualAndDateIsLessThanEqualAAndHourDetail_WorkerIdOrderByDateDateAsc(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate, @Param("workerId") Long workerId);
}