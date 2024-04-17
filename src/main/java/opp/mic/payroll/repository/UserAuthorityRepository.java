package opp.mic.payroll.repository;

import opp.mic.payroll.model.UserAuthority;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority,Long> {
        @Query(value = "SELECT * from user_authority u where u.role_id=?" ,nativeQuery = true)
        List<UserAuthority> findByRoleId(Long roleId);
}
