package asw.repository;

import asw.model.Proposal;
import asw.model.User;

import java.util.List;

public interface DBService {

    boolean updateInfo(String id, String oldPass, String newPass);
    User getParticipant(String email, String password);
    void insertUser(User user);

    List<Proposal> getAllProposal();
}
