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

    private List<Vote> upvotes;
    private List<Vote> downvotes;
    private List<Comment> comments;

    @Override
    public String toString() {
        StringBuilder s = null;

        s.append("Proposal{" +
                "votes=" + upvotes.size() + downvotes.size() +
                ", supporters=" + upvotes.size() +
                ", comments={");
        comments.forEach(comment -> s.append("{" + comment.toString() + "}"));
        s.append('}');

        return s.toString();
    }
}
