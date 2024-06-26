package opp.mic.cms.repository;

import opp.mic.cms.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByUsername(String username);
    List<AppUser> findAll();
}
