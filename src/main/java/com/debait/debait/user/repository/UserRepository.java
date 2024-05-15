package com.debait.debait.user.repository;

import com.debait.debait.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @EntityGraph (attributePaths = "authorities")
    @Query("SELECT u FROM User u JOIN FETCH u.authorities WHERE u.login_id = :loginId")
    Optional<User> findOneWithAuthoritiesByLogin_id (String loginId);

}
