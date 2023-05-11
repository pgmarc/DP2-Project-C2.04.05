
package acme.features.lecturer.lecture;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.framework.components.accounts.Principal;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Lecturer;

@Service
public class LecturerLectureListAllService extends AbstractService<Lecturer, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LecturerLectureRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


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
		final Principal principal = super.getRequest().getPrincipal();
		final List<Lecture> object = this.repository.getAllLecturesFromLecturer(principal.getAccountId());

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;
		final Lecturer lecturer = object.getLecturer();
		tuple = super.unbind(object, "title", "lectureAbstract", "nature");
		tuple.put("lecturer", lecturer.getIdentity().getFullName());
		tuple.put("lecturerId", lecturer.getId());
		super.getResponse().setData(tuple);
	}
}
