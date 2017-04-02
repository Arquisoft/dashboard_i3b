package asw.kafka.mockProducer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import asw.kafka.DashboardListener;
import scala.collection.mutable.HashTable;

@ManagedBean
public class MessageProducer {
  
	private static final Logger logger = Logger.getLogger(DashboardListener.class);

	@Autowired
	private KafkaTemplate<String, String> template;

	//@Scheduled(cron = "*/5 * * * * *")
	public void sendProposalMessages() {
		ListenableFuture<SendResult<String, String>> future;
		// Simple message to test
		/*String message = "New proposal [\"Test create\"]";

		future = template.send("councilStaff", message);

		//Log if it is sent or not
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success sending " + message);
			}
			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error sending " + message);
			}
		});
*/
		String message2 = "Upvoted proposal [Test create]";

		future = template.send("councilStaff", message2);

		//Log if it is sent or not
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success sending " + message2);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error sending " + message2);
			}
		});

		String message3 = "Upvoted proposal [Test]";

		future = template.send("councilStaff", message3);

		//Log if it is sent or not
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success sending " + message3);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error sending " + message3);
			}
		});

		String message4 = "Downvoted proposal [Test2]";

		future = template.send("councilStaff", message4);

		//Log if it is sent or not
		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Success sending " + message4);
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Error sending " + message4);
			}
		});
	}

    Map<Integer, String> topics = new HashMap<Integer, String>();

    @Scheduled(cron = "*/10 * * * * *")
    public void send() {
        topics.put(0, "councilStaff");
        topics.put(1, "otherAuthorities");
        topics.put(2, "councilmen");

        Random a = new Random();
        a.setSeed(new Date().getTime());
        int key = a.nextInt(3);
        // Simple message to test
        String message = "MESSAGE TEST LOG " + topics.get(key)+ " " + new Date();

        ListenableFuture<SendResult<String, String>> future = template.send(topics.get(key), message);

        //Log if it is sent or not
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Success sending " + message);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error sending " + message);
            }
        });
    }

    public void send(String topic) {
        String message = "MESSAGE TEST LOG " + topic + " " + new Date();

        ListenableFuture<SendResult<String, String>> future = template.send(topic, message);

        //Log if it is sent or not
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Success sending " + message);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Error sending " + message);
            }
        });
    }
}
