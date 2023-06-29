
package acme.features.any.peep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.message.Peep;
import acme.framework.components.accounts.Any;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AnyPeepShowService extends AbstractService<Any, Peep> {

	@Autowired
	protected AnyPeepRepository repository;


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

		int id;
		Peep peep;

		id = super.getRequest().getData("id", int.class);
		peep = this.repository.findPeepById(id);
		super.getBuffer().setData(peep);
	}

	@Override
	public void unbind(final Peep peep) {

		Tuple tuple;
		tuple = super.unbind(peep, "instantiationMoment", "title", "nick", "message", "email", "moreInfo");

		super.getResponse().setData(tuple);
	}
}
