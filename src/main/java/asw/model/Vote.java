package asw.model;

/**
 * Created by juanf on 20/03/2017.
 */

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "VotingSystem")
public class Vote {

    @Id
    String id;

    User user;
    int value; //Value upvote or downvote


    @Override
    public String toString() {
        return "Vote{" +
                "value=" + value +
                '}';
    }
}
