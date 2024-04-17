package opp.mic.payroll.repository;

import opp.mic.payroll.model.UserAuthority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessRightsRepository extends CrudRepository<UserAuthority,Long> {

}
