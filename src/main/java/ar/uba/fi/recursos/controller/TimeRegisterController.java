package ar.uba.fi.recursos.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.service.TimeRegisterService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Validated
@RestController
@RequestMapping(path = "/timeRegisters")
@EnableSwagger2
public class TimeRegisterController {

    @Autowired
    private TimeRegisterService timeRegisterService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TimeRegister>> getAllTimeRegisters() {
        return ResponseEntity.ok(timeRegisterService.findAll());
    }

    @PostMapping(path = "")
    public ResponseEntity<TimeRegister> createTimeRegister(@RequestBody TimeRegister timeRegister) {
        return ResponseEntity.ok(timeRegisterService.createTimeRegisterFrom(timeRegister));
    }

    @PutMapping(path = "/{timeRegisterId}")
    public ResponseEntity<TimeRegister> modifyTimeRegister(@RequestBody TimeRegister newTimeRegister,
            @PathVariable Long timeRegisterId) {
        return ResponseEntity.ok(timeRegisterService.modifyTimeRegister(timeRegisterId, newTimeRegister));
    }

    @DeleteMapping(path = "/{timeRegisterId}")
    public void deleteTimeRegister(@PathVariable Long timeRegisterId) {
        timeRegisterService.deleteById(timeRegisterId);
    }

    @GetMapping(path = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeRegister> getTimeRegisterReport(@RequestParam Long workerId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate minDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate maxDate) {
        return timeRegisterService.findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(minDate,
                maxDate, workerId);
    }

}

// hourDetail
// {
// "workerId": 1,
// "startTime":"2017-01-19",
// "endTime":"2018-01-19",
// "status":"BORRADOR",
// "hours": "24"
// }

// timeRegister
// {
// "typeOfActivity":"TASK",
// "activityId":"121",
// "hours":"8",
// "dates": ["2020-01-01", "2020-01-02"]
// }
