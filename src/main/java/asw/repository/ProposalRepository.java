package asw.repository;

import asw.model.Proposal;
import asw.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends MongoRepository<Proposal, String> {

    List<Proposal> findAll();
    Proposal findByTitle(String title);


}
