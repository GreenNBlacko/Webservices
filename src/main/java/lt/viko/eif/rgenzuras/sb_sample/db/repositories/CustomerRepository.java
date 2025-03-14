package lt.viko.eif.rgenzuras.sb_sample.db.repositories;

import lt.viko.eif.rgenzuras.sb_sample.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository linking the database entries with the application
 * @author ramunas.genzuras@stud.viko.lt
 */
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
