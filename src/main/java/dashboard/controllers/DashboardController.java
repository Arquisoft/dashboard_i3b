package dashboard.controllers;

import dashboard.model.Proposal;
import dashboard.model.User;
import dashboard.model.Vote;
import dashboard.repository.DBService;
import hello.MainController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by juanf on 20/03/2017.
 */
@Controller
public class DashboardController {

    private final DBService service;
    private static final Logger logger = Logger.getLogger(MainController.class);

    @Autowired
    DashboardController(DBService service) {
        this.service = service;
    }


    List<Proposal> proposals;
    List<User> users;
    List<Vote> votes;

    private List<SseEmitter> sseEmitters = Collections.synchronizedList(new ArrayList<>());


    @RequestMapping("/")
    public String handleRequest() {
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


    @RequestMapping(path = "/", method = RequestMethod.POST)
    public String showMessage(String data, String topic) {
        //proposals = service.getAllProposal();
        synchronized (this.sseEmitters) {
            for (SseEmitter sseEmitter : this.sseEmitters) {
                try {
                    switch (topic) {
                        case "updates":
                            /*proposals.parallelStream().forEach(c -> {
                                try {
                                    sseEmitter.send(c.toString());
                                } catch (IOException e) {
                                    logger.error("Browser has closed");
                                }
                            });*/
                            sseEmitter.send("update " + data);
                            break;
                        case "councilStaff":
                            sseEmitter.send("CouncilStaff log: " + data);

                            break;
                    }
                } catch (IOException e) {
                    logger.error("Browser has closed");
                }


            }
        }
        //creo que aqui se puede mandar un modelo y data como un atributo del modelo igual que la anterior vez
        return data;
    }

    @KafkaListener(topics = "updates")
    public void sendMessage(String data) {
        showMessage(data, "updates");
    }

    @KafkaListener(topics = "councilStaff")
    public void sendMessage2(String data) {
        showMessage(data, "councilStaff");
    }

    //Aqui estaría bien que hubiera otros kafka listener con los metodos
    // que hay en dashboardListener y que enviara los logs.


}
