package asw.kafka.mockProducer;

import java.util.Date;

import javax.annotation.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import asw.kafka.DashboardListener;

@ManagedBean
public class MessageProducer {
	private static final Logger logger = Logger.getLogger(DashboardListener.class);

	@Autowired
	KafkaTemplate<String, String> template;

	@Scheduled(cron = "*/3 * * * * *")
	public void send2() {
		// Simple message to test
		String message = "MESSAGE TEST UPDATE " + new Date();

		ListenableFuture<SendResult<String, String>> future = template.send("update", message);

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


	@Scheduled(cron = "*/5 * * * * *")
	public void send() {
		// Simple message to test
		String message = "MESSAGE TEST LOG COUNCILSTAFF " + new Date();

		ListenableFuture<SendResult<String, String>> future = template.send("councilStaff", message);

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
