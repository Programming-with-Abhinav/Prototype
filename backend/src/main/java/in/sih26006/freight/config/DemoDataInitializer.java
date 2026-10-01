package in.sih26006.freight.config;

import in.sih26006.freight.entity.*;
import in.sih26006.freight.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner demoData(PortRepository ports, VesselRepository vessels) {
        return args -> {
            if (ports.count() == 0) {
                ports.save(new Port("Paradip", 14.5, 260, 42, "Low", 20.264, 86.699));
                ports.save(new Port("Visakhapatnam", 15.0, 280, 45, "Moderate", 17.686, 83.289));
                ports.save(new Port("Gangavaram", 16.0, 290, 45, "Low", 17.630, 83.235));
                ports.save(new Port("Gopalpur", 12.5, 220, 35, "Moderate", 19.274, 84.912));
                ports.save(new Port("Dhamra", 14.0, 250, 40, "Low", 20.782, 86.947));
                ports.save(new Port("Sagar-Sandheads", 11.5, 210, 33, "Moderate", 21.692, 88.039));
                ports.save(new Port("Haldia", 11.0, 200, 32, "Moderate", 22.025, 88.064));
            }
            if (vessels.count() == 0) {
                vessels.save(new Vessel("Handysize", 35000, 10.5, 180, 28));
                vessels.save(new Vessel("Supramax", 58000, 12.8, 200, 32));
                vessels.save(new Vessel("Panamax", 75000, 13.2, 225, 32));
                vessels.save(new Vessel("Capesize", 170000, 18.0, 289, 45));
            }
        };
    }
}
