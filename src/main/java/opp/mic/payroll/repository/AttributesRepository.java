package opp.mic.payroll.repository;

import opp.mic.payroll.model.Attributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributesRepository extends JpaRepository<Attributes,Long> {
    Optional<Attributes> findByName(String name);

}
