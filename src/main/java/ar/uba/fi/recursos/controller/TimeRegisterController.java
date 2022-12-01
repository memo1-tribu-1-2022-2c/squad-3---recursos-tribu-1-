package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.HourDetailRepository;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import ar.uba.fi.recursos.service.HourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/timeRegister")
@EnableSwagger2
public class TimeRegisterController {

    @Autowired
    private TimeRegisterRepository timeRegisterRepository;
    
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeRegister> getTimeRegisterReport(@RequestParam Long workerId, @RequestParam LocalDate minDate, @RequestParam LocalDate maxDate) {
        return this.timeRegisterRepository.findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(minDate, maxDate, workerId);
    }

}





// hourDetail
// {
//     "workerId": 1,
//     "startTime":"2017-01-19", 
//     "endTime":"2018-01-19",
//     "status":"BORRADOR",
//     "hours": "24"
// }

// timeRegister
// {
//     "typeOfActivity":"TASK",
//     "activityId":"121",
//     "hours":"8",
//     "dates": ["2020-01-01", "2020-01-02"]
// }
