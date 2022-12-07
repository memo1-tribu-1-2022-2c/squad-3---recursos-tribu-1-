package ar.uba.fi.recursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.model.TimeRegister;
import ar.uba.fi.recursos.service.HourDetailService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Validated
@RestController
@RequestMapping(path = "/hourDetails")
@EnableSwagger2
public class HourDetailController {

    @Autowired
    private HourDetailService hourDetailService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HourDetail>> getAllHourDetails(@RequestParam(required = false) Long workerId) {
        if (workerId == null)
            return ResponseEntity.ok(hourDetailService.findAll());
        return ResponseEntity.ok(hourDetailService.findByWorkerId(workerId));
    }

    @PostMapping(path = "")
    public ResponseEntity<HourDetail> createHourDetail(@RequestBody HourDetail hourDetail) {
        return ResponseEntity.ok(hourDetailService.createHourDetailFrom(hourDetail));
    }

    @GetMapping(path = "/{hourDetailId}")
    public ResponseEntity<HourDetail> getHourDetail(@PathVariable Long hourDetailId) {
        return ResponseEntity.ok(hourDetailService.findById(hourDetailId));
    }

    @GetMapping(path = "/{hourDetailId}/timeRegisters")
    public ResponseEntity<List<TimeRegister>> getTimeRegistersFromHourDetail(@PathVariable Long hourDetailId) {
        return ResponseEntity.ok(hourDetailService.findTimeRegistersFrom(hourDetailId));
    }

    @PutMapping(path = "/{hourDetailId}")
    public ResponseEntity<HourDetail> modifyHourDetail(@RequestBody HourDetail newHourDetail,
            @PathVariable Long hourDetailId) {
        return ResponseEntity.ok(hourDetailService.modifyHourDetail(hourDetailId, newHourDetail));
    }

    @DeleteMapping(path = "/{hourDetailId}")
    public void deleteHourDetail(@PathVariable Long hourDetailId) {
        hourDetailService.deleteById(hourDetailId);
    }

    @GetMapping(path = "/totalProjectHours/{projectId}")
    public ResponseEntity<Double> getTotalProjectHours(@PathVariable Long projectId) {
        return ResponseEntity.ok(hourDetailService.getTotalProjectHours(projectId));
    }

}

// hourDetail
// {
// "workerId": 1,
// "startTime":"2017-01-19",
// "endTime":"2018-01-19",
// "status":"BORRADOR",
// }
