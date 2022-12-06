package ar.uba.fi.recursos.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.model.TimeRegisterTypeOfActivity;

@RepositoryRestResource
public interface TimeRegisterRepository extends CrudRepository<TimeRegister, Long> {

        @Override
        List<TimeRegister> findAll();

        @Query(value = "select tr from TimeRegister tr join HourDetail hr on tr.hourDetailId = hr.id where hr.workerId = :workerId and tr.date between :minDate and :maxDate order by tr.date asc")
        List<TimeRegister> findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(LocalDate minDate,
                        LocalDate maxDate, Long workerId);

        boolean existsTimeRegisterByDateAndActivityIdAndTypeOfActivity(LocalDate date, Long ActivityId,
                        TimeRegisterTypeOfActivity TypeOfActivity);

        List<TimeRegister> findByHourDetailId(Long workerId);

}