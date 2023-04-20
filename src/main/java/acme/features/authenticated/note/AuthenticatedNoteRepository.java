
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

	@Query("select n from Note n where (:date - n.instantiationMoment < 30) ")
	public List<Note> listNotes(Date date);

	@Query("select n from Note n where n.id = :id")
	public Note showNote(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	public UserAccount getUserAccountById(int id);
}
