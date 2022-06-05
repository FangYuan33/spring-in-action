package tacos.repository;

import org.springframework.data.repository.CrudRepository;
import tacos.domain.User;

public interface UserJPARepository extends CrudRepository<User, Long> {

    User findByUserName(String userName);
}
