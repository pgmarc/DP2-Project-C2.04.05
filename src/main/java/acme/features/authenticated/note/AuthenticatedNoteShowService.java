
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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

		boolean status;
		final Date currentDate;

		final int id = super.getRequest().getData("id", int.class);
		final Note note = this.repository.findNoteById(id);
		currentDate = MomentHelper.getCurrentMoment();

		status = !MomentHelper.isLongEnough(note.getInstantiationMoment(), currentDate, 1, ChronoUnit.MONTHS);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id;
		final Note note;

		id = super.getRequest().getData("id", int.class);
		note = this.repository.findNoteById(id);
		super.getBuffer().setData(note);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "fullName", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
