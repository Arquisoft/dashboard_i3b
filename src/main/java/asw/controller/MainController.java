package asw.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import asw.model.Proposal;
import asw.model.User;
import asw.model.Vote;
import asw.repository.DBService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
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


    @Autowired
    MainController(DBService service) {
        this.service = service;
    }


    List<Proposal> proposals;
    List<User> users;
    List<Vote> votes;


    @RequestMapping("/")
    public String handleRequest(Model model) {
        return "index";
    }

    @RequestMapping("/councilmen")
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

    @RequestMapping("/otherAuthorities")
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

    @RequestMapping("/councilstaff")
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
    public void sendMessageCouncilStaff(String data) {
        showMessage(data, "councilStaff");
        logger.info("New message received for council staff: \"" + data + "\"");
    }

    @KafkaListener(topics = "councilmen")
    public void sendMessageCouncilMen(String data) {
        showMessage(data, "councilmen");
        logger.info("New message received for council men: \"" + data + "\"");
    }

    @KafkaListener(topics = "otherAuthorities")
    public void sendMessageOtherAuthorities(String data) {
        showMessage(data, "otherAuthorities");
    }

}