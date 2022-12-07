package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.dtos.TaskData;
import ar.uba.fi.recursos.exceptions.ExistingTimeRegisterException;
import ar.uba.fi.recursos.exceptions.InvalidDateException;
import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityNotFoundException;

@Service
public class TimeRegisterService {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;
    @Autowired
    private HourDetailService hourDetailService;
    @Autowired
    private ConceptService conceptService;
    private final String TASKS_URL = "https://squad2-2022-2c.herokuapp.com/api/v1/tasks";

    public TimeRegister createTimeRegisterFrom(TimeRegister timeRegister) {
        HourDetail foundHourDetail = hourDetailService.findById(timeRegister.getHourDetailId());
        checkTimeRegisterActivityIsValid(timeRegister);
        checkTimeRegisterDoesNotAlreadyExist(timeRegister);
        checkTimeRegisterHoursAreValid(timeRegister);
        checkTimeRegisterDateIsInsidePeriodOf(timeRegister, foundHourDetail);
        foundHourDetail.addTimeRegister(timeRegister);
        hourDetailService.save(foundHourDetail);
        return timeRegister;
    }

    public TimeRegister modifyTimeRegister(Long timeRegisterId, TimeRegister newTimeRegister) {
        HourDetail foundHourDetail = hourDetailService.findById(findById(timeRegisterId).getHourDetailId());
        checkTimeRegisterActivityIsValid(newTimeRegister);
        checkTimeRegisterDoesNotAlreadyExist(newTimeRegister);
        checkTimeRegisterHoursAreValid(newTimeRegister);
        checkTimeRegisterDateIsInsidePeriodOf(newTimeRegister, foundHourDetail);
        newTimeRegister.setId(timeRegisterId);
        return timeRegisterRepository.save(newTimeRegister);
    }

    private void checkTimeRegisterActivityIsValid(TimeRegister timeRegister) {
        switch (timeRegister.getTypeOfActivity()) {
            case TASK -> {
                String url = String.format("%s/%d", TASKS_URL, timeRegister.getActivityId());
                if (new RestTemplate().getForObject(url, TaskData.class) == null)
                    throw new EntityNotFoundException(
                            String.format("La actividad %s - %d, no existe", timeRegister.getTypeOfActivity().name(),
                                    timeRegister.getActivityId()));
            }

            case CONCEPT -> conceptService.findById(timeRegister.getActivityId());
        }
    }

    private void checkTimeRegisterDoesNotAlreadyExist(TimeRegister timeRegister) {
        if (timeRegisterRepository.existsTimeRegisterByDateAndActivityIdAndTypeOfActivity(timeRegister.getDate(),
                timeRegister.getActivityId(), timeRegister.getTypeOfActivity())) {
            throw new ExistingTimeRegisterException("Ya existe un registro para esa misma fecha y actividad");
        }
    }

    private void checkTimeRegisterHoursAreValid(TimeRegister timeRegister) {
        if (timeRegister.getHours() <= 0 || timeRegister.getHours() > 24)
            throw new InvalidHourDetailHoursException(
                    "Las horas del registro deben ser positivas y estar dentro de las 24 horas");

        List<TimeRegister> fromSameDate = timeRegisterRepository.findAllByDateAndHourDetailId(timeRegister.getDate(),
                timeRegister.getHourDetailId());
        Double totalHours = fromSameDate.stream().filter(tr -> !tr.getId().equals(timeRegister.getId()))
                .mapToDouble(TimeRegister::getHours).sum();
        if ((totalHours + timeRegister.getHours()) > 24)
            throw new InvalidHourDetailHoursException(
                    String.format("La cantidad de horas registradas para el día %s no pueden superar las 24 horas",
                            timeRegister.getDate().toString()));
    }

    private void checkTimeRegisterDateIsInsidePeriodOf(TimeRegister timeRegister, HourDetail hourDetail) {
        LocalDate timeRegisterDate = timeRegister.getDate();
        LocalDate hourDetailStartDate = hourDetail.getStartTime();
        LocalDate hourDetailEndDate = hourDetail.getEndTime();

        if (timeRegisterDate.isAfter(hourDetailEndDate) || timeRegisterDate.isBefore(hourDetailStartDate)) {
            throw new InvalidDateException(
                    String.format("La fecha indicada para el registro no está dentro del período del %s al %s",
                            hourDetailStartDate.toString(), hourDetailEndDate.toString()));
        }
    }

    public List<TimeRegister> findAll() {
        return timeRegisterRepository.findAll();
    }

    public TimeRegister findById(Long timeRegisterId) {
        return timeRegisterRepository.findById(timeRegisterId).orElseThrow(() -> {
            throw new EntityNotFoundException("No existe ningún registro con id: " + timeRegisterId);
        });
    }

    public List<TimeRegister> findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(LocalDate minDate,
            LocalDate maxDate, Long workerId) {
        return timeRegisterRepository.findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(minDate,
                maxDate, workerId);
    }

    public void deleteById(Long timeRegisterId) {
        TimeRegister foundTimeRegister = findById(timeRegisterId);
        HourDetail foundHourDetail = hourDetailService.findById(foundTimeRegister.getHourDetailId());
        foundHourDetail.removeTimeRegister(foundTimeRegister);
        hourDetailService.save(foundHourDetail);
        timeRegisterRepository.deleteById(timeRegisterId);
    }
}