package com.simpleBankingApp.repository;

import com.simpleBankingApp.model.database.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByEmailIdIgnoreCase(String emailId);

    boolean existsUserByMobileNumber(Long mobileNumber);

    boolean existsUserByUserName(String userName);

    User findOneByUserName(String userName);

}
