package org.pollub.pai_lab10.repository;

import org.pollub.pai_lab10.model.UserDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}
