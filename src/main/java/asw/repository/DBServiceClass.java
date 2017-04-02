package asw.repository;

import asw.model.Comment;
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

    // Proposal code

    @Autowired
    private
    ProposalRepository proposalRepository;

    @Override
    public List<Proposal> getAllProposal() {
        return proposalRepository.findAll();
    }

    @Override
    public Proposal getProposal(String id) {
        Proposal proposal = proposalRepository.findOne(id);
        if (proposal != null)
            return proposal;
        else {
            System.out.println("NULL PROPOSAL");
            return null;
        }
    }

    @Override
    public void upvoteProposal(String title) {
        Proposal proposal = proposalRepository.findByTitle(title);
        proposal.addUpvote();

        proposalRepository.save(proposal);
    }

    @Override
    public void downvoteProposal(String title) {
        Proposal proposal = proposalRepository.findByTitle(title);
        proposal.addDownvote();

        proposalRepository.save(proposal);
    }

    @Override
    public void insertProposal(Proposal proposal) {
        proposalRepository.insert(proposal);
    }

    @Override
    public void addCommentToProposal(String title, Comment comment) {
        Proposal proposal = proposalRepository.findByTitle(title);
        proposal.addComment(comment);

        proposalRepository.save(proposal);

    }
}
