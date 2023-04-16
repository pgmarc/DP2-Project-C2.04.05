
package acme.features.assistant.tutorialsession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tutorial.Tutorial;
import acme.entities.tutorial.TutorialSession;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AssistantTutorialSessionRepository extends AbstractRepository {

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.tutorial.id = :tutorialId")
	Collection<TutorialSession> findTutorialSessionsByTutorialId(int tutorialId);

	@Query("SELECT t FROM Tutorial t WHERE t.id = :tutorialId")
	Tutorial findOneTutorialById(int tutorialId);

	@Query("SELECT ts FROM TutorialSession ts WHERE ts.id = :id")
	TutorialSession findOneTutorialSessionsById(int id);

	@Query("SELECT t FROM Tutorial t WHERE t.id = (SELECT ts.tutorial.id FROM TutorialSession ts WHERE ts.id = :tutorialSessionId)")
	Tutorial findOneTutorialByTutorialSessionId(int tutorialSessionId);

}
