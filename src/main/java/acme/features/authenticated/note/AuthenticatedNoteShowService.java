
package acme.features.authenticated.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteShowService extends AbstractService<Authenticated, Note> {

	@Autowired
	protected AuthenticatedNoteRepository repository;


	@Override
	public void check() {
		boolean status;
		status = super.getRequest().hasData("id", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getData("id", int.class);
		final Note object = this.repository.showNote(id);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "fullName", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
