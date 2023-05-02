
package acme.features.any.peep;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.message.Peep;
import acme.framework.repositories.AbstractRepository;

public interface AnyPeepRepository extends AbstractRepository {

	@Query("Select p from Peep p")
	public List<Peep> listPeeps();

	@Query("Select p from Peep p where p.id = :id")
	public Peep showPeep(int id);
}
