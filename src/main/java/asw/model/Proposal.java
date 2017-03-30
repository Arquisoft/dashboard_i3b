package asw.model;

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

    private String title;

    public String getTitle() { return title; }

    private List<Vote> upvotes;
    private List<Vote> downvotes;
    private List<Comment> comments;

    @Override
    public String toString() {
        StringBuilder s = null;

        s.append("[Proposal: ")
                .append(title)
                .append(" votes: ")
                .append(upvotes.size() + downvotes.size())
                .append(" supporters: ")
                .append(upvotes.size())
                .append(" comments: {");
        comments.forEach(comment -> s.append('\"' + comment.toString() + '\"'));
        s.append("}]");

        return s.toString();
    }
}
