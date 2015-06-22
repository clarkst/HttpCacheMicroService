package metapack;

import metapack.service.StoreLocationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableScheduling
public class Application {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Component
    static class Runner implements CommandLineRunner {

        @Autowired
        private StoreLocationsService storeLocationsService;

        @Override
        public void run(String... args) throws Exception {
            log.info("INIT CACHE");
            storeLocationsService.refreshStoreLocationsCache();
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
