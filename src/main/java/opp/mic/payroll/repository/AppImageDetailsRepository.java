package opp.mic.payroll.repository;

import opp.mic.payroll.model.AppImageDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppImageDetailsRepository extends CrudRepository<AppImageDetails,Long> {
    Optional<AppImageDetails> findByPath(String path);
    List<AppImageDetails> findAll();

    @Query(value = "SELECT * FROM photos u where u.fk_id=?",nativeQuery = true)
    Optional<AppImageDetails> findByUserId(Long id);
}
