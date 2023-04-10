
package acme.features.authenticated.tutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tutorial.Tutorial;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedTutorialListService extends AbstractService<Authenticated, Tutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTutorialRepository repository;

	// AbstractService<Authenticated, Provider> ---------------------------


	@Override
	public void check() {
		super.getResponse().setChecked(true);
	}

	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Authenticated.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Collection<Tutorial> tutorials;

		tutorials = this.repository.findAllTutorials();

		super.getBuffer().setData(tutorials);
	}

	@Override
	public void unbind(final Tutorial object) {
		assert object != null;

		Tuple tuple;

		tuple = super.unbind(object, "code", "abstrac", "goals", "estimatedHours");
		tuple.put("assistant", object.getAssistant());
		tuple.put("course", object.getCourse());

		super.getResponse().setData(tuple);
	}
}
