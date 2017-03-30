package asw.kafka;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Created by herminio on 28/12/16.
 */
@ManagedBean
public class DashboardListener {
    private static final Logger logger = Logger.getLogger(DashboardListener.class);

    /*
        Topics: councilstaff, councilmen, otherauthorities, updates
     */

    /*@KafkaListener(topics = "councilStaff")
    public void listenCouncilStaff(String data) {
        logger.info("New message received for council staff: \"" + data + "\"");
    }*/




}
