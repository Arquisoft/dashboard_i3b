package asw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by juanf on 20/03/2017.
 */
@Document(collection = "VotingSystem")
public class Proposal {

    @Id
    String id;
    private String title;
    private int votes;
    private int upvotes;
    private int downvotes;
    private List<Comment> comments;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getVotes() { return upvotes - downvotes; }
    public int getUpvotes() { return upvotes; }
    public int getDownvotes() { return downvotes; }


    public Proposal() {
        this.comments = new ArrayList<>();
        this.votes = 0;
    }
    public Proposal(String title) {
        this.comments = new ArrayList<>();
        this.votes = 0;
        this.title= title;
    }

    public void addUpvote() {
        upvotes++;
    }
    public void addDownvote() {
        downvotes++;
    }
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        s.append("[Proposal: ")
                .append(title)
                .append(" votes: ")
                .append(votes)
                //.append(" supporters: ")
                //.append(upvotes.size())
                .append(" comments: {");
        //comments.forEach(comment -> s.append('\"' + comment.toString() + '\"'));
        //TODO Fix
        s.append("}]");

        return s.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Proposal other = (Proposal) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
