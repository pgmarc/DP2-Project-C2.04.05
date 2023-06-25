
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Peep;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyPeepRepository extends AbstractRepository {

	@Query("SELECT p FROM Peep p")
	public Collection<Peep> findAllPeeps();

	@Query("SELECT p FROM Peep p WHERE p.id = :id")
	public Peep findPeepById(int id);

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	public UserAccount findUserAccountById(int id);
}
