package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.TimeRegisterRepository;
import ar.uba.fi.recursos.service.HourDetailService;
import ar.uba.fi.recursos.service.TimeRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private HourDetailService hourDetailService;

    @Autowired
    private TimeRegisterService timeRegisterService;

    @GetMapping(path = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeRegister> getTimeRegisterReport(@RequestParam Long workerId, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate minDate  , @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate maxDate) {
        return this.timeRegisterRepository.findTimeRegistersByDateBetweenAndHourDetail_WorkerIdOrderByDateAsc(minDate, maxDate, workerId);
    }


    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TimeRegister> getAllTimeRegisters() {
        return this.timeRegisterRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createTimeRegister(@RequestBody TimeRegister timeRegister) {
        
        Long hourDetailId = timeRegister.getHourDetailId();
        Optional<HourDetail> hourDetail = hourDetailService.findById(hourDetailId);
        if (!hourDetail.isPresent()) {
            return ResponseEntity.badRequest().body("HourDetail with id " + hourDetailId + " does not exist");
        }

        ResponseEntity<Object> isError = timeRegisterService.verifyTimeRegister(timeRegister);
        if (isError.getStatusCode() != HttpStatus.OK) {
            return isError;
        }
        
        HourDetail existingHourDetail = hourDetail.get();
        existingHourDetail.addTimeRegister(timeRegister);
        hourDetailService.save(existingHourDetail);

        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> modifyTimeRegisterData(@RequestBody TimeRegister timeRegister, @PathVariable Long id){
        Optional<TimeRegister> timeRegisterOptional = timeRegisterRepository.findById(id);

        if (!timeRegisterOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Time Register with id "+ id+ " does not exist");
        }

        ResponseEntity<Object> isError = timeRegisterService.verifyTimeRegister(timeRegister);
        if (isError.getStatusCode() != HttpStatus.OK) {
            return isError;
        }

        timeRegister.setId(id);
        return ResponseEntity.ok(timeRegisterRepository.save(timeRegister));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> deleteTimeRegister(@PathVariable Long id) {
        Optional<TimeRegister> timeRegisterOptional = timeRegisterRepository.findById(id);

        if (!timeRegisterOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Time Register with id "+ id+ " does not exist");
        }

        TimeRegister existingTimeRegister = timeRegisterOptional.get();

        // hour detail deletes the time register
        Long hourDetailId = existingTimeRegister.getHourDetailId();
        Optional<HourDetail> hourDetailOptional = hourDetailService.findById(hourDetailId);
        
        if (!hourDetailOptional.isPresent()) {
            return ResponseEntity.badRequest().body("Hour detail with id " + hourDetailId + "does not exist");
        }
        
        HourDetail hourDetail = hourDetailOptional.get();
        hourDetail.removeTimeRegister(existingTimeRegister);
        hourDetailService.save(hourDetail);
        timeRegisterRepository.deleteById(id);
        return ResponseEntity.ok().build();
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
