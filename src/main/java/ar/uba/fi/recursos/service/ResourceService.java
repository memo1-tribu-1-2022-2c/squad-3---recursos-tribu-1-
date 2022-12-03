package ar.uba.fi.recursos.service;

import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ar.uba.fi.recursos.model.Resource;

@Service
public class ResourceService {

    static final String url = "https://anypoint.mulesoft.com/mocking/api/v1/sources/exchange/assets/754f50e8-20d8-4223-bbdc-56d50131d0ae/recursos-psa/1.0.0/m/api/recursos";

    public List<Resource> getAllResources() {
        RestTemplate restTemplate = new RestTemplate();
        Resource[] resourcesRaw = restTemplate.getForObject(url, Resource[].class);
        
        if (resourcesRaw == null) {
            return Collections.emptyList();
        }

        return Arrays.asList(resourcesRaw);
    }

    public Optional<Resource> findById(Long resourceId) {
        List<Resource> resources = this.getAllResources();
        return resources.stream().filter(r -> r.getId().equals(resourceId)).findAny();
    }
}
