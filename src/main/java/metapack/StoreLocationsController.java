package metapack;

import metapack.exception.CityNotFoundException;
import metapack.model.StoreLocation;
import metapack.repository.StoreLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/locations")
public class StoreLocationsController {

    @Autowired
    private StoreLocationRepository storeLocationRepository;

    @RequestMapping(method = RequestMethod.GET, params = "city")
    public Iterable searchByCity(String city) {
        List<StoreLocation> locations = storeLocationRepository.findByCity(city);
        if (locations == null) {
            throw new CityNotFoundException();
        }
        return locations;
    }
}