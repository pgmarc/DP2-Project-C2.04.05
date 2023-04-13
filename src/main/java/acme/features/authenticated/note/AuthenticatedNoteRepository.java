
package acme.features.authenticated.note;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.message.Note;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedNoteRepository extends AbstractRepository {

	@Query("select n from Note n")
	public List<Note> listNotes();

	@Query("select n from Note n where n.id = :id")
	public Note showNote(int id);
}
