package asw.repository;

import asw.model.Proposal;
import asw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBServiceClass implements DBService {

    @Autowired
    private
    UserRepository userRepository;

    @Autowired
    private
    ProposalRepository proposalRepository;

    @Override
    public boolean updateInfo(String id, String oldPass, String newPass) {
        User user = userRepository.findOne(id);
        if (user.getPassword().equals(oldPass)) {
            user.setPassword(newPass);
            userRepository.save(user);
            return true;
        } else
            return false;
    }

    @Override
    public User getParticipant(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password))
            return user;
        else
            return null;
    }

    @Override
    public void insertUser(User user) {
        userRepository.insert(user);
    }

    @Override
    public List<Proposal> getAllProposal() {
        return proposalRepository.findAll();
    }
}
