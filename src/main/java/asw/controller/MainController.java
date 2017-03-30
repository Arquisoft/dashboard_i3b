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
    private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());
    private List<SseEmitter> logsCouncilStaff = Collections.synchronizedList(new ArrayList<>());
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

    @RequestMapping("/updates")
    SseEmitter subscribeUpdates() {
        SseEmitter sseEmitter = new SseEmitter();
        synchronized (this.sseEmitters) {
            this.sseEmitters.add(sseEmitter);
            sseEmitter.onCompletion(() -> {
                synchronized (this.sseEmitters) {
                    this.sseEmitters.remove(sseEmitter);
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
        //proposals = service.getAllProposal();
        switch (topic) {
            case "updates":
                synchronized (this.sseEmitters) {
                    for (SseEmitter sseEmitter : this.sseEmitters) {
                        /*proposals.parallelStream().forEach(c -> {
                                try {
                                    sseEmitter.send(c.toString());
                                } catch (IOException e) {
                                    logger.error("Browser has closed");
                                }
                            });*/
                        try {
                            sseEmitter.send("update " + data);
                        } catch (IOException e) {
                            logger.error("Browser has closed");
                        }

                    }
                }
                break;
            case "councilStaff":
                synchronized (this.logsCouncilStaff) {
                    for (SseEmitter sseEmitter : this.logsCouncilStaff) {
                        try {
                            sseEmitter.send("CouncilStaff log: " + data);
                        } catch (IOException e) {
                            logger.error("Browser has closed");
                        }
                    }
                }
                break;

        }
        //creo que aqui se puede mandar un modelo y data como un atributo del modelo igual que la anterior vez
        return data;
    }

    @KafkaListener(topics = "update")
    public void sendMessage(String data) {
        showMessage(data, "updates");
    }

//    @KafkaListener(topics = "councilStaff")
  //  public void sendMessage2(String data) {
    //    showMessage(data, "councilStaff");
    //}

    //Aqui estaría bien que hubiera otros kafka listener con los metodos
    // que hay en dashboardListener y que enviara los logs.


}