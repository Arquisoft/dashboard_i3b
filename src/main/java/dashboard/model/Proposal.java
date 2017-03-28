package dashboard.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by juanf on 20/03/2017.
 */
@Document(collection = "VotingSystem")
public class Proposal {

    @Id
    String id;

    private List<Vote> votes; //This is the list of votes that will be linked in DB
    private List<User> supporters; //This is the list of supporters that should be User instead of String
    private List<Comment> comments;

    @Override
    public String toString() {
        return "Proposal{" +
                "votes=" + votes.size() +
                ", supporters=" + supporters.size() +
                ", comments=" + comments +
                '}';
    }
}
