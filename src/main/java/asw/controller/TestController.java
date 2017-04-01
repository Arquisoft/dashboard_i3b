package asw.controller;

import asw.model.Proposal;
import asw.repository.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guille on 3/30/17.
 */

@Controller
public class TestController {
    private final DBService service;
    private Pattern newProposalPattern, upvotedProposalPattern, downvotedProposalPattern;

    @Autowired
    TestController(DBService service) {
        this.service = service;

        createMockDatabaseContent();

        newProposalPattern = Pattern.compile("New proposal \\[\"(.+)\"\\]");
        upvotedProposalPattern = Pattern.compile("Upvoted proposal \\[(.+)\\]");
        downvotedProposalPattern = Pattern.compile("Downvoted proposal \\[(.+)\\]");

    }

    private void createMockDatabaseContent() {
        service.insertProposal(new Proposal("Test"));
        service.insertProposal(new Proposal("Test2"));
        service.insertProposal(new Proposal("Test create"));
    }

    @KafkaListener(topics = "councilStaff")
    public void testAjaxRefresh(@Payload String data) {
        Matcher newProposalMatcher = newProposalPattern.matcher(data);
        Matcher upvotedProposalMatcher = upvotedProposalPattern.matcher(data);
        Matcher downvotedProposalMatcher = downvotedProposalPattern.matcher(data);
        //Matcher newProposalMatcher = newProposalPattern.matcher(data);
        if (newProposalMatcher.matches()) { // New Proposal
            String title = newProposalMatcher.group(1);
            Proposal proposal = new Proposal(title);
            service.insertProposal(proposal);

        }
        else if (upvotedProposalMatcher.matches()) { // Upvote proposal
            String title = upvotedProposalMatcher.group(1);
            service.upvoteProposal(title);
        }
        else if (downvotedProposalMatcher.matches()) { // Downvote proposal
            String title = downvotedProposalMatcher.group(1);
            service.downvoteProposal(title);
        }
        else {
            System.out.println("String [" + data + "] not recognized");
        }

    }


    @RequestMapping(path = "/test")
    public String testMayMays(Model model) {
        model.addAttribute("proposals", service.getAllProposal());
        return "test";
    }
}
