package metapack.schedule;

import metapack.service.StoreLocationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    StoreLocationsService storeLocationsService;

    @Scheduled(cron = "${locations.refresh.cron}")
    @Async
    public void refreshStoreLocationData() throws Exception {
        log.info("Scheduled update of cache data " + new Date());
        storeLocationsService.refreshStoreLocationsCache();
    }
}