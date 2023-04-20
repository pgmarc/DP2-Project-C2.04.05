
package acme.features.authenticated.bulletin;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBulletinRepository extends AbstractRepository {

	@Query("SELECT b FROM Bulletin b")
	List<Bulletin> findAllBulletins();

	@Query("SELECT b FROM Bulletin b WHERE b.id = :id")
	Bulletin findOneBulletinById(int id);

}
