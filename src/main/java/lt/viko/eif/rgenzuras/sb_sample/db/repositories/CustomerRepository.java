package lt.viko.eif.rgenzuras.sb_sample.db.repositories;

import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository linking the database entries with the application
 * @author ramunas.genzuras@stud.viko.lt
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
