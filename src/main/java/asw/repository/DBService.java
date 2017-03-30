package asw.repository;

import asw.model.Comment;
import asw.model.Proposal;
import asw.model.User;
import asw.model.Vote;

import java.util.List;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    User getParticipant(String email, String password);
    void insertUser(User user);

    List<Proposal> getAllProposal();
    void insertProposal(Proposal proposal);
    //void upvoteProposal(String id, Vote vote);
    //void downvoteProposal(String id, Vote vote);
    void upvoteProposal(String id);
    void downvoteProposal(String id);

    Proposal getProposal(String id);
    // TODO void addCommentProposal(String id, String comment);
}
