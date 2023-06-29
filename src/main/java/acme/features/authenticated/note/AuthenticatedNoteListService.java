
package acme.features.authenticated.note;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Note;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.helpers.MomentHelper;
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
		Date currentDate;
		Date oneMonthAgo;
		currentDate = MomentHelper.getCurrentMoment();
		oneMonthAgo = MomentHelper.deltaFromCurrentMoment(-1, ChronoUnit.MONTHS);
		final List<Note> object = this.repository.findNotesNotOlderThanOneMonth(oneMonthAgo, currentDate);
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Note object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title");

		super.getResponse().setData(tuple);
	}
}
