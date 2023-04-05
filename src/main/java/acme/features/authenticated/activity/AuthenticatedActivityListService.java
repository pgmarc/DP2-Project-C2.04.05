
package acme.features.authenticated.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.enrolment.Activity;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class AuthenticatedActivityListService extends AbstractService<Authenticated, Activity> {

	@Autowired
	protected AuthenticatedActivityRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Student.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void check() {
		boolean status;

		status = super.getRequest().hasData("id", int.class);

		super.getResponse().setChecked(status);
	}

	@Override
	public void load() {
		List<Activity> activities;
		final int enrolmentId = super.getRequest().getData("id", int.class);

		activities = this.repository.findAllActivitiesByEnrolmentId(enrolmentId);

		super.getBuffer().setData(activities);
	}

	@Override
	public void unbind(final Activity object) {
		Tuple tuple;

		tuple = super.unbind(object, "title", "abstract$", "moreInfo", "type", "startDate", "endDate");

		super.getResponse().setData(tuple);
	}
}
