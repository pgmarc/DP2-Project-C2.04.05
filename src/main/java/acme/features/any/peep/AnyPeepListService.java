
package acme.features.any.peep;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepListService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


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

		Collection<Peep> peeps;
		peeps = this.repository.findAllPeeps();
		super.getBuffer().setData(peeps);

	}

	@Override
	public void unbind(final Peep peep) {

		Tuple tuple;
		tuple = super.unbind(peep, "instantiationMoment", "title", "nick", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
