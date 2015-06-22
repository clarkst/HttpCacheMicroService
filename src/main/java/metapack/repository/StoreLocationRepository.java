package metapack.repository;

import metapack.model.StoreLocation;

import java.util.List;

public interface StoreLocationRepository{

    List<StoreLocation> findByCity(String city);

    void refreshStoreLocations(List<StoreLocation> storeLocations);
}