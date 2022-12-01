package ar.uba.fi.recursos.service;

import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeRegisterService {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;

    public TimeRegister createTimeRegister(TimeRegister timeRegister) {
        // hourDetail.setId(-1L);
        return timeRegisterRepository.save(timeRegister);
    }

    public TimeRegister save(TimeRegister timeRegister) {
        return timeRegisterRepository.save(timeRegister);
    }
}