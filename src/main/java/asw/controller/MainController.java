package asw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asw.repository.DBService;
import asw.kafka.LogProcessorService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class MainController {

    private static final Logger logger = Logger.getLogger(MainController.class);
    private List<SseEmitter> logsCouncilmen = Collections.synchronizedList(new ArrayList<>());
    private List<SseEmitter> logsCouncilStaff = Collections.synchronizedList(new ArrayList<>());
    private List<SseEmitter> logsOtherAuthorities = Collections.synchronizedList(new ArrayList<>());
    private SseEmitter emitter = new SseEmitter();
    private final DBService service;
    private final LogProcessorService logProcessor;


    @Autowired
    MainController(DBService service, LogProcessorService logProcessor) {
        this.logProcessor = logProcessor;
        this.service = service;
    }

    @RequestMapping("/")
    public String handleRequest(Model model) {
        return "index";
    }

    @RequestMapping("/councilmen_logs")
    SseEmitter subscribeCouncilmen() {
        SseEmitter sseEmitter = new SseEmitter();
        synchronized (this.logsCouncilmen) {
            this.logsCouncilmen.add(sseEmitter);
            sseEmitter.onCompletion(() -> {
                synchronized (this.logsCouncilmen) {
                    this.logsCouncilmen.remove(sseEmitter);
                }
            });
        }
        return sseEmitter;
    }

    @RequestMapping("/otherAuthorities_logs")
    SseEmitter subscribeOtherAuthorities() {
        SseEmitter sseEmitter = new SseEmitter();
        synchronized (this.logsOtherAuthorities) {
            this.logsOtherAuthorities.add(sseEmitter);
            sseEmitter.onCompletion(() -> {
                synchronized (this.logsOtherAuthorities) {
                    this.logsOtherAuthorities.remove(sseEmitter);
                }
            });
        }
        return sseEmitter;
    }

    @RequestMapping("/councilstaff_logs")
    SseEmitter subscribeLogs() {
        SseEmitter log = new SseEmitter();
        synchronized (this.logsCouncilStaff) {
            this.logsCouncilStaff.add(log);
            log.onCompletion(() -> {
                synchronized (this.logsCouncilStaff) {
                    this.logsCouncilStaff.remove(log);
                }
            });
        }
        return log;
    }

    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String showMessage(String data, String topic) {
        switch (topic) {
            case "councilStaff":
                synchronized (this.logsCouncilStaff) {
                    for (SseEmitter sseEmitter : this.logsCouncilStaff) {
                        try {
                            sseEmitter.send("CouncilStaff log: " + data);
                            logger.info("send");
                        } catch (IOException e) {
                            logger.error("Browser has closed");
                        }
                    }
                }
                break;
            case "councilmen":
                synchronized (this.logsCouncilmen) {
                    for (SseEmitter sseEmitter : this.logsCouncilmen) {
                        try {
                            sseEmitter.send("Councilmen log: " + data);
                            logger.info("send");
                        } catch (IOException e) {
                            logger.error("Browser has closed");
                        }
                    }
                }
                break;

            case "otherAuthorities":
                synchronized (this.logsOtherAuthorities) {
                    for (SseEmitter sseEmitter : this.logsOtherAuthorities) {
                        try {
                            sseEmitter.send("Other Authorities log: " + data);
                            logger.info("send");
                        } catch (IOException e) {
                            logger.error("Browser has closed");
                        }
                    }
                }
                break;

        }
        return data;
    }

    @KafkaListener(topics = "councilStaff")
    public void sendMessageCouncilStaff(@Payload String data) {
        logProcessor.processKafkaLog(data);
        showMessage(data, "councilStaff");
        logger.info("New message received for council staff: \"" + data + "\"");
    }

    @KafkaListener(topics = "councilmen")
    public void sendMessageCouncilMen(@Payload String data) {
        logProcessor.processKafkaLog(data);
        showMessage(data, "councilmen");
        logger.info("New message received for council men: \"" + data + "\"");
    }

    @KafkaListener(topics = "otherAuthorities")
    public void sendMessageOtherAuthorities(@Payload String data) {
        logProcessor.processKafkaLog(data);
        showMessage(data, "otherAuthorities");
        logger.info("New message received for other authorities: \"" + data + "\"");
    }

}