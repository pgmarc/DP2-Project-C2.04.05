
package acme.features.any.peep;

import java.util.List;

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
		final List<Peep> object = this.repository.listPeeps();
		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Peep object) {
		Tuple tuple;
		tuple = super.unbind(object, "instantiationMoment", "title", "nick", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
