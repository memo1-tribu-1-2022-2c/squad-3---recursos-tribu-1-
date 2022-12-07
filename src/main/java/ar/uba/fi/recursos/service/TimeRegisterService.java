package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.dtos.TaskData;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.model.TimeRegisterTypeOfActivity;
import ar.uba.fi.recursos.repository.ConceptRepository;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class TimeRegisterService {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;
    @Autowired
    private ConceptRepository conceptRepository;

    public boolean verifyActivity(Long activityId, TimeRegisterTypeOfActivity typeOfActivity) {
        if (typeOfActivity == TimeRegisterTypeOfActivity.TASK) {
            String url = "https://squad2-2022-2c.herokuapp.com/api/v1/tasks/" + activityId;
            RestTemplate restTemplate = new RestTemplate();
            TaskData task_raw = restTemplate.getForObject(url, TaskData.class);
            return task_raw != null;

        } else if (typeOfActivity == TimeRegisterTypeOfActivity.CONCEPT) {
            return conceptRepository.findById(activityId).isPresent();
        }
        return false;
    }

    public ResponseEntity<Object> verifyNewTimeRegister(TimeRegister timeRegister) {
        if (timeRegisterRepository.existsTimeRegisterByDateAndActivityIdAndTypeOfActivity(timeRegister.getDate(), timeRegister.getActivityId(), timeRegister.getTypeOfActivity())) {
            return ResponseEntity.badRequest().body("Time Register with given date and activity already exists");
        }
        return verifyTimeRegister(timeRegister);
    }

    public ResponseEntity<Object> verifyTimeRegister(TimeRegister timeRegister) {
        if (timeRegister.getHours() <= 0 || timeRegister.getHours() > 24) {
            return ResponseEntity.badRequest().body("Las horas del parte no pueden ser negativas o mayores a 24: " + timeRegister.getHours());
        }
        if (!verifyActivity(timeRegister.getActivityId(), timeRegister.getTypeOfActivity())) {
            return ResponseEntity.badRequest().body("La actividad " + timeRegister.getTypeOfActivity().name() + "-" + timeRegister.getActivityId() + ", no existe");
        }
        List<TimeRegister> fromSameDate = timeRegisterRepository.findAllByDateAndHourDetailId(timeRegister.getDate(), timeRegister.getHourDetailId());
        Double totalHours = fromSameDate.stream().filter(tr -> Objects.equals(tr.getId(), timeRegister.getId())).map(TimeRegister::getHours).reduce(Double::sum).orElse((double) 0);
        totalHours += timeRegister.getHours();
        if (totalHours > 24) {
            return ResponseEntity.badRequest().body("Los registros para el día " + timeRegister.getDate() + " no pueden superar el límite de 24 horas");
        }
        return ResponseEntity.ok().build();
    }

    public List<TimeRegister> findAll() {
        return timeRegisterRepository.findAll();
    }

    public Optional<TimeRegister> findById(Long timeRegisterId) {
        return timeRegisterRepository.findById(timeRegisterId);
    }

    public void deleteById(Long timeRegisterId) {
        timeRegisterRepository.deleteById(timeRegisterId);
    }

    public List<TimeRegister> findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(LocalDate minDate,
                                                                                                 LocalDate maxDate, Long workerId) {
        return timeRegisterRepository.findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(minDate,
                maxDate, workerId);
    }
}