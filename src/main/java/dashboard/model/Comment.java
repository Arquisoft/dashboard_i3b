package dashboard.model;

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

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + user.getEmail() +
                ", commentString='" + commentString + '\'' +
                '}';
    }
}
