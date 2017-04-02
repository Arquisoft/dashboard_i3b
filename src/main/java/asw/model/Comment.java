package asw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by juanf on 20/03/2017.
 */

@Document(collection = "VotingSystem")
public class Comment {

    @Id
    String id;
    User user;
    String commentString;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCommentString() {
        return commentString;
    }

    public void setCommentString(String commentString) {
        this.commentString = commentString;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user.getEmail() +
                ", commentString='" + commentString + '\'' +
                '}';
    }
}
