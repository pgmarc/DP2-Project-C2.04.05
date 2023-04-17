
package acme.features.student.lecture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.course.Lecture;
import acme.framework.components.models.Tuple;
import acme.framework.services.AbstractService;
import acme.roles.Student;

@Service
public class StudentLectureShowService extends AbstractService<Student, Lecture> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected StudentLectureRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


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
		final int id = super.getRequest().getData("id", int.class);
		final Lecture object = this.repository.findOneLectureById(id);

		super.getBuffer().setData(object);
	}

	@Override
	public void unbind(final Lecture object) {
		Tuple tuple;

		tuple = super.unbind(object, "title", "lectureAbstract", "estimatedLearningTime", "nature", "body", "moreInfo");
		tuple.put("lecturer", object.getLecturer().getIdentity().getFullName());

		super.getResponse().setData(tuple);
	}
}
