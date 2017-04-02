package asw.kafka;

import asw.model.Proposal;
import asw.repository.DBService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guille on 4/3/17.
 */
@Service
public class LogProcessorService {
    private static final Logger logger = Logger.getLogger(LogProcessorService.class);
    private final DBService service;
    private Pattern newProposalPattern, upvotedProposalPattern, downvotedProposalPattern;

    @Autowired
    LogProcessorService(DBService service) {
        this.service = service;

        newProposalPattern = Pattern.compile("New proposal \\[\"(.+)\"\\]");
        upvotedProposalPattern = Pattern.compile("Upvoted proposal \\[(.+)\\]");
        downvotedProposalPattern = Pattern.compile("Downvoted proposal \\[(.+)\\]");
    }

    public void processKafkaLog(@Payload String data) {
        Matcher newProposalMatcher = newProposalPattern.matcher(data);
        Matcher upvotedProposalMatcher = upvotedProposalPattern.matcher(data);
        Matcher downvotedProposalMatcher = downvotedProposalPattern.matcher(data);
        if (newProposalMatcher.matches()) { // New Proposal
            String title = newProposalMatcher.group(1);
            Proposal proposal = new Proposal(title);
            service.insertProposal(proposal);
            logger.info("Added proposal [" + title + "]");

        }
        else if (upvotedProposalMatcher.matches()) { // Upvote proposal
            String title = upvotedProposalMatcher.group(1);
            service.upvoteProposal(title);
            logger.info("Upvoted proposal [" + title + "]");
        }
        else if (downvotedProposalMatcher.matches()) { // Downvote proposal
            String title = downvotedProposalMatcher.group(1);
            service.downvoteProposal(title);
            logger.info("Downvoted proposal [" + title + "]");
        }
        else {
            logger.info("String [" + data + "] not recognized");
        }

    }
}
