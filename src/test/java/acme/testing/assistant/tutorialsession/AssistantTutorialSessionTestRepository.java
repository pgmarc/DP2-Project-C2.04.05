
package acme.testing.assistant.tutorialsession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

public interface AssistantTutorialSessionTestRepository extends AbstractRepository {

	@Query("SELECT t FROM Tutorial t")
	Collection<Tutorial> findAllTutorials();

	@Query("select t from Tutorial t where t.assistant.userAccount.username = :username")
	Collection<Tutorial> findManyTutorialsByAssitantUsername(String username);

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.tutorial.id = :tutorialId")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int tutorialId);

}
