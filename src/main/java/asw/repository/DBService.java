package asw.repository;

import asw.model.Comment;
import asw.model.Proposal;
import asw.model.User;

import java.util.List;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    User getParticipant(String email, String password);
    void insertUser(User user);

    List<Proposal> getAllProposal();
    void insertProposal(Proposal proposal);
    void upvoteProposal(String title);
    void downvoteProposal(String title);
    Proposal getProposal(String title);
    void addCommentToProposal(String title, Comment comment);
}
