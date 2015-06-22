package metapack.repository;

import metapack.model.StoreLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
public class MapBackedStoreLocationRepository implements StoreLocationRepository {

    private static Logger log = LoggerFactory.getLogger(MapBackedStoreLocationRepository.class);

    private static final Map<String, List<StoreLocation>> cache = new HashMap<String, List<StoreLocation>>();

    @Override
    public List<StoreLocation> findByCity(String city) {
        synchronized (cache) {
            return cache.get(city);
        }
    }

    @Override
    public void refreshStoreLocations(List<StoreLocation> storeLocations) {

        Map<String, List<StoreLocation>> mappedLocations = storeLocations.stream().collect(
                Collectors.groupingBy(storeLocation -> storeLocation.getAddress().getCity(),
                        Collectors.mapping(storeLocation -> storeLocation, toList())));

        synchronized (cache) {
            cache.clear();
            cache.putAll(mappedLocations);
            log.info("Store Locations cache refreshed");
        }
    }

}
