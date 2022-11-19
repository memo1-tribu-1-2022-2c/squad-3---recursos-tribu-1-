package ar.uba.fi.recursos.controller;

import ar.uba.fi.recursos.model.HourDetail;
import ar.uba.fi.recursos.repository.HourDetailRepository;
import ar.uba.fi.recursos.service.HourDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.URI;
import java.util.List;

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
    public List<HourDetail> getAllHourDetails() {
        return this.hourDetailRepository.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<HourDetail> createHourDetail(@RequestBody HourDetail hourDetail) {
        HourDetail created = this.hourDetailService.createHourDetail(hourDetail);
        return ResponseEntity.created(URI.create("/api/v1/hourDetail/" + created.getId())).build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<HourDetail> getHourDetail(@PathVariable Long id) {
        return ResponseEntity.of(hourDetailRepository.findById(id));
    }
}
