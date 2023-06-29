
package acme.features.authenticated.note;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Note;
import acme.framework.components.accounts.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("SELECT n FROM Note n WHERE :startingDate < n.instantiationMoment AND n .instantiationMoment < :currentDate ")
	public List<Note> findNotesNotOlderThanOneMonth(Date startingDate, Date currentDate);

	@Query("SELECT n from Note n WHERE n.id = :id")
	public Note findNoteById(int id);

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id = :id")
	public UserAccount findUserAccountById(int id);
}
