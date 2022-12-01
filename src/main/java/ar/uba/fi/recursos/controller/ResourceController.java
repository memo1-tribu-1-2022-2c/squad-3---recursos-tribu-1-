package ar.uba.fi.recursos.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ar.uba.fi.recursos.model.Resource;
import ar.uba.fi.recursos.service.ResourceService;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Validated
@RestController
@EnableSwagger2
@RequestMapping(path = "/resources")
public class ResourceController {
    
    @Autowired
    private ResourceService resourceService;

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable Long id) {
        return ResponseEntity.of(resourceService.findById(id));
    }
}
