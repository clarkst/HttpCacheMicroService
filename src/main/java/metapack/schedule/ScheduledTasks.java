package metapack.schedule;

import metapack.service.StoreLocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    StoreLocationsService storeLocationsService;

//    @Scheduled(cron = "0 0 * * * ?")${rates.refresh.cron}
    @Scheduled(cron = "${locations.refresh.cron}")
    @Async
    public void refreshStoreLocationData() throws Exception {
        System.out.println("UPDATE CACHE"+new Date());
        storeLocationsService.refreshStoreLocationsCache();
    }
}