
package acme.features.lecturer;

import java.util.List;
import java.util.stream.Collectors;

import acme.entities.course.Course;
import acme.entities.course.Lecture;
import acme.enumerates.Nature;
import acme.features.lecturer.course.LecturerCourseRepository;
import acme.features.lecturer.lecture.LecturerLectureRepository;

public class UpdateCourseNature {

	public static void updateCourseNature(final Course object, final LecturerLectureRepository lectureRepository, final LecturerCourseRepository courseRepository) {
		List<Nature> lectures;
		lectures = lectureRepository.getLecturesFromCourse(object.getId()).stream().map(Lecture::getNature).collect(Collectors.toList());
		final int theoryLectures = lectures.stream().filter(n -> n == Nature.THEORETICAL).collect(Collectors.toList()).size();
		final int handsOnLectures = lectures.stream().filter(n -> n == Nature.HANDS_ON).collect(Collectors.toList()).size();
		final int balancedLectures = lectures.stream().filter(n -> n == Nature.BALANCED).collect(Collectors.toList()).size();
		if (theoryLectures > handsOnLectures && theoryLectures > balancedLectures)
			object.setNature(Nature.THEORETICAL);
		else if (handsOnLectures > theoryLectures && handsOnLectures > balancedLectures)
			object.setNature(Nature.HANDS_ON);
		else
			object.setNature(Nature.BALANCED);
		courseRepository.save(object);
	}

}
