package metapack.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import metapack.model.StoreLocation;
import metapack.repository.StoreLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class StoreLocationsRefreshService implements StoreLocationsService {

    Logger log = LoggerFactory.getLogger(StoreLocationsRefreshService.class);

    @Value("${HTTPS_LOCATION_SERVICE_HEROKUAPP_COM_LOCATIONS}")
    private final String externalServiceUrl = null;

    @Autowired
    private StoreLocationRepository storeLocationRepository;

    @Override
    @HystrixCommand(fallbackMethod = "retryStoreLocation", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public void refreshStoreLocationsCache() {
        RestTemplate restClient = new RestTemplate();
        restClient.setErrorHandler(new DefaultResponseErrorHandler());
        ResponseEntity<StoreLocation[]> responseEntity = restClient.getForEntity(externalServiceUrl, StoreLocation[].class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            storeLocationRepository.refreshStoreLocations(Arrays.asList(responseEntity.getBody()));
        }
    }

    private void retryStoreLocation() {
            log.error("FALLBACK METHOD REST CALL TIMED OUT");
    }


}