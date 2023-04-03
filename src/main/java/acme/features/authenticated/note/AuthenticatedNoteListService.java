
package acme.features.authenticated.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteListService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final List<Note> object = this.repository.listNotes();
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "fullName", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
