
package acme.features.administrator.bulletin;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Bulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBulletinRepository extends AbstractRepository {

	@Query("SELECT b FROM Bulletin b WHERE b.id = :id")
	Bulletin findOneBulletinById(int id);

}
