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

@ManagedBean
public class MessageProducer {
  
	private static final Logger logger = Logger.getLogger(MessageProducer.class);

	@Autowired
	private KafkaTemplate<String, String> template;

	@Scheduled(cron = "*/5 * * * * *")
	public void sendProposalMessagesCouncilstaff() {
		//testCreateProposal("councilStaff", "New proposal");

		testUpvoteProposal("councilStaff", "Test");

		testDownvoteProposal("councilStaff", "Test2");

	}

    Map<Integer, String> topics = new HashMap<>();

    @Scheduled(cron = "*/3 * * * * *")
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

    private void testCreateProposal(String topic, String proposal) {
		ListenableFuture<SendResult<String, String>> future;
		String message = "New proposal [\"" + proposal + "\"]";

		future = template.send(topic, message);

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

	private void testUpvoteProposal(String topic, String proposal) {
		ListenableFuture<SendResult<String, String>> future;

		String message = "Upvoted proposal [" + proposal + "]";

		future = template.send(topic, message);

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

	private void testDownvoteProposal(String topic, String proposal) {
		ListenableFuture<SendResult<String, String>> future;

		String message = "Downvoted proposal [" + proposal + "]";

		future = template.send(topic, message);

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
