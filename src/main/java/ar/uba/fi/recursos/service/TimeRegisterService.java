package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.exceptions.InvalidHourDetailHoursException;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeRegisterService {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;

    public TimeRegister createTimeRegister(TimeRegister timeRegister) {
        if(timeRegister.getHours()<=0 && timeRegister.getHours()>24){
            throw new InvalidHourDetailHoursException("Las horas del parte no pueden ser negativas o mayores a 24: " + timeRegister.getHours());
        }

        return timeRegisterRepository.save(timeRegister);
    }

    public TimeRegister save(TimeRegister timeRegister) {
        return timeRegisterRepository.save(timeRegister);
    }
}