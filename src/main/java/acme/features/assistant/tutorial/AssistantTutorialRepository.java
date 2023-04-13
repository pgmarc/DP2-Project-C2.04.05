
package acme.features.assistant.tutorial;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Assistant;

@Repository
public interface AssistantTutorialRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("SELECT t FROM Tutorial t WHERE t.id = :id")
	Tutorial findOneTutorialById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.assistant.id = :id")
	Collection<Tutorial> findTutorialsByAssistantId(int id);

	@Query("SELECT s FROM Tutorial t JOIN TutorialSession s WHERE t.id = :id")
	Collection<TutorialSession> findSessionsByTutorialId(int id);

	@Query("SELECT a FROM Tutorial t JOIN Assistant a WHERE a.id = :activeRoleId")
	Assistant findOneAssistantById(int activeRoleId);

}
