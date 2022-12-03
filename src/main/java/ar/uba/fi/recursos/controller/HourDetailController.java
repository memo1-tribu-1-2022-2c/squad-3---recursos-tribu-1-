package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.service.HourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping(path = "/hourDetails")
@EnableSwagger2
public class HourDetailController {

    @Autowired
    private HourDetailService hourDetailService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<HourDetail>> getAllHourDetails(@RequestParam(required = false) Long workerId) {
        if (workerId != null)
            return ResponseEntity.ok(hourDetailService.findByWorkerId(workerId));
        return ResponseEntity.ok(hourDetailService.findAll());
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createHourDetail(@RequestBody HourDetail hourDetail) {
        HourDetail createdHourDetail;
        try {
            createdHourDetail = hourDetailService.createHourDetail(hourDetail);
        } catch (Throwable e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
        return ResponseEntity.ok(createdHourDetail);
    }

    @GetMapping(path = "/{hourDetailId}")
    public ResponseEntity<HourDetail> getHourDetail(@PathVariable Long hourDetailId) {
        return ResponseEntity.of(hourDetailService.findById(hourDetailId));
    }

    @PutMapping(path = "/{hourDetailId}")
    public ResponseEntity<Object> modifyHourDetail(@RequestBody HourDetail hourDetail,
            @PathVariable Long hourDetailId) {
        Optional<HourDetail> hourDetailOptional = hourDetailService.findById(hourDetailId);

        if (!hourDetailOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (!hourDetailService.hasValidPeriod(hourDetail)) {
            return ResponseEntity.badRequest().body("Dates are not valid or overlaps with other hour details");
        }

        hourDetail.setId(hourDetailId);
        return ResponseEntity.ok(hourDetailService.save(hourDetail));
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
