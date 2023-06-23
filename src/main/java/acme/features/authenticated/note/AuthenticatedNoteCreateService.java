
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
		Date today;
		Principal principal;
		final String name;
		today = MomentHelper.getCurrentMoment();
		principal = super.getRequest().getPrincipal();
		name = this.repository.findUserAccountById(principal.getAccountId()).getIdentity().getFullName();
		final Note object = new Note();
		object.setInstantiationMoment(today);
		object.setFullName(name);
		super.getBuffer().setData(object);
	}

	@Override
	public void bind(final Note object) {
		Date moment;
		Principal principal;
		final String name;
		principal = super.getRequest().getPrincipal();
		name = this.repository.findUserAccountById(principal.getAccountId()).getIdentity().getFullName();
		moment = MomentHelper.getCurrentMoment();
		super.bind(object, "instantiationMoment", "title", "fullName", "message", "email", "moreInfo", "confirmation");
		object.setFullName(name);
		object.setInstantiationMoment(moment);
	}

	@Override
	public void validate(final Note object) {

		boolean isConfirmed;

		isConfirmed = super.getRequest().getData("confirmation", boolean.class);
		super.state(isConfirmed, "confirmation", "authenticated.note.form.error.must-confirm-note");
	}

	@Override
	public void perform(final Note object) {

		this.repository.save(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "fullName", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
