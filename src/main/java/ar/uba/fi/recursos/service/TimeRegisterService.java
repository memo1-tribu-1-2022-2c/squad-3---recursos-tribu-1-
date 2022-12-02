package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.model.TimeRegisterTypeOfActivity;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import ar.uba.fi.recursos.repository.ConceptRepository;

@Service
public class TimeRegisterService {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;

    // @Autowired
    // private ConceptRepository conceptRepository;

    public TimeRegister createTimeRegister(TimeRegister timeRegister) {
        if (timeRegister.getHours() <= 0 && timeRegister.getHours() > 24) {
            throw new InvalidHourDetailHoursException(
                    "Las horas del parte no pueden ser negativas o mayores a 24: " + timeRegister.getHours());
        }

        return timeRegisterRepository.save(timeRegister);
    }

    public TimeRegister save(TimeRegister timeRegister) {
        return timeRegisterRepository.save(timeRegister);
    }

    public boolean verifyActivity(Long activityId, TimeRegisterTypeOfActivity typeOfActivity) {
        // if(typeOfActivity == TimeRegisterTypeOfActivity.TASK){
        // String url = "https://squad2-2022-2c.herokuapp.com/api/v1/tasks/" +
        // activityId;
        // RestTemplate restTemplate = new RestTemplate();
        // TaskData task_raw = restTemplate.getForObject(url, TaskData.class);
        // if (task_raw == null) {
        // return false;
        // }
        // return true;

        // } else if(typeOfActivity == TimeRegisterTypeOfActivity.CONCEPT){
        // if(conceptRepository.findById(activityId).isEmpty()) {
        // return false;
        // }
        // return true;
        // }
        // return false;
        return true;
    }
}