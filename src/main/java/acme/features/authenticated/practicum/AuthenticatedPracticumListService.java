
package acme.features.authenticated.practicum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Course;
import acme.entities.practicum.Practicum;
import acme.framework.components.accounts.Authenticated;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;

@Service
public class AuthenticatedPracticumListService extends AbstractService<Authenticated, Practicum> {

	@Autowired
	private AuthenticatedPracticumRepository repository;


	@Override
	public void check() {

		boolean status;

		status = super.getRequest().hasData("courseId", int.class);
		super.getResponse().setChecked(status);
	}

	@Override
	public void authorise() {

		boolean status;
		Course course;
		int courseId;

		courseId = super.getRequest().getData("courseId", int.class);
		course = this.repository.findCourseById(courseId);

		status = course != null && super.getRequest().getPrincipal().hasRole(Authenticated.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		int courseId;
		Collection<Practicum> practica;

		courseId = super.getRequest().getData("courseId", int.class);
		practica = this.repository.findPracticaByCourseId(courseId);

		super.getBuffer().setData(practica);
	}

	@Override
	public void unbind(final Practicum object) {

		Tuple tuple;

		tuple = super.unbind(object, "code", "title");

		super.getResponse().setData(tuple);
	}

}
