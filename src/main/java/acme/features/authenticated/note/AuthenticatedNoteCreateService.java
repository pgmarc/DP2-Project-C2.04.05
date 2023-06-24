
package acme.features.authenticated.note;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedNoteCreateService extends AbstractService<Authenticated, Note> {

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
		Date currentDate;
		Principal principal;
		final String author;
		final String username;
		final String fullname;
		currentDate = MomentHelper.getCurrentMoment();

		principal = super.getRequest().getPrincipal();
		username = principal.getUsername();
		fullname = this.repository.findUserAccountById(principal.getAccountId()).getIdentity().getFullName();
		author = username + " - " + fullname;

		final Note object = new Note();
		object.setInstantiationMoment(currentDate);
		object.setAuthor(author);

		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note note) {

		super.bind(note, "title", "message", "email", "moreInfo", "confirmation");
	}

	@Override
	public void validate(final Note object) {

		boolean isConfirmed;

		isConfirmed = super.getRequest().getData("confirmation", boolean.class);
		super.state(isConfirmed, "confirmation", "authenticated.note.form.error.must-confirm-note");
	}

	@Override
	public void perform(final Note note) {

		this.repository.save(note);
	}

	@Override
	public void unbind(final Note note) {
		Tuple tuple;
		tuple = super.unbind(note, "instantiationMoment", "title", "author", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
