
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t WHERE t.draftMode = false")
	Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findOneTutorialById(int id);

}
