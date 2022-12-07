package ar.uba.fi.recursos.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.uba.fi.recursos.model.Resource;

@Service
public class ResourceService {

    private final String RESOURCES_URL = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";

    public List<Resource> getAllResources() {
        Resource[] rawResources = new RestTemplate().getForObject(RESOURCES_URL, Resource[].class);

        if (rawResources == null)
            return Collections.emptyList();
        return Arrays.asList(rawResources);
    }

    public Resource findById(Long resourceId) {
        return getAllResources().stream().filter(r -> r.getId().equals(resourceId)).findFirst().orElseThrow(() -> {
            throw new EntityNotFoundException("No existe ningún recurso con id: " + resourceId);
        });
    }
}
