package tacos.repository.mapper;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import tacos.domain.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Long> {

}
