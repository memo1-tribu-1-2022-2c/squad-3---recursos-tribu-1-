package ar.uba.fi.recursos.repository;

import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.model.TimeRegisterTypeOfActivity;

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

    List<TimeRegister> findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(LocalDate minDate, LocalDate maxDate, Long workerId);

    boolean existsTimeRegisterByDateAndActivityIdAndTypeOfActivity(LocalDate date, Long ActivityId, TimeRegisterTypeOfActivity TypeOfActivity);
}