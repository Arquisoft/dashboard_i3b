package dashboard.listeners;

import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

import javax.annotation.ManagedBean;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class DashboardListener {

    private static final Logger logger = Logger.getLogger(DashboardListener.class);

    @KafkaListener(topics = "councilStaff")
    public void listenCouncilStaff(String data) {
        logger.info("New message received for council staff: \"" + data + "\"");
    }

    @KafkaListener(topics = "councilmen")
    public void listenCouncilmen(String data) {
        logger.info("New message received for councilmen: \"" + data + "\"");
    }

    @KafkaListener(topics = "otherAuthorities")
    public void listenOtherAuthorities(String data) {
        logger.info("New message received for other authorities: \"" + data + "\"");
    }

}
