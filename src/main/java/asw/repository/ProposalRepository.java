package asw.repository;

import asw.model.Proposal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends MongoRepository<Proposal, String> {

    List<Proposal> findAll();


}
