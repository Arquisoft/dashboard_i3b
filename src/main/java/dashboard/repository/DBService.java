package dashboard.repository;

import dashboard.model.Proposal;
import dashboard.model.User;
import hello.UserInfo;

import java.util.List;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    User getParticipant(String email, String password);
    void insertUser(User user);

    List<Proposal> getAllProposal();
}
