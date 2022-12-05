package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.repository.HourDetailRepository;
import ar.uba.fi.recursos.service.HourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/hourDetail")
@EnableSwagger2
public class HourDetailController {

    @Autowired
    private HourDetailService hourDetailService;

    @Autowired
    private HourDetailRepository hourDetailRepository;
    
    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)	
    public List<HourDetail> getAllHourDetails(@RequestParam(required = false) Long workerId) {
        if (workerId != null) {
            return this.hourDetailRepository.findByWorkerId(workerId);
        }
        return this.hourDetailRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createHourDetail(@RequestBody HourDetail hourDetail){
        return hourDetailService.createHourDetail(hourDetail);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HourDetail> getHourDetail(@PathVariable Long id) {
        return ResponseEntity.of(hourDetailRepository.findById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> modifyHourDetailData(@RequestBody HourDetail hourDetail, @PathVariable Long id){
        Optional<HourDetail> hourDetailOptional = hourDetailRepository.findById(id);

        if (!hourDetailOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<Object> response = hourDetailService.verifyDates(hourDetail);
        if (response.getStatusCode() != HttpStatus.OK) {
            return response;
        }

        hourDetail.setId(id);
        return ResponseEntity.ok(hourDetailService.save(hourDetail));
    }


    @DeleteMapping(path = "/{id}")
    public void deleteHourDetail(@PathVariable Long id) {
        hourDetailRepository.deleteById(id);
    }


    @GetMapping(path = "/totalProjectHours/{projectId}")
    public ResponseEntity<Double> getTotalProjectHours(@PathVariable Long projectId) {
        return ResponseEntity.ok(hourDetailService.getTotalProjectHours(projectId));
    }
    
}





// hourDetail
// {
//     "workerId": 1,
//     "startTime":"2017-01-19", 
//     "endTime":"2018-01-19",
//     "status":"BORRADOR",
// }
