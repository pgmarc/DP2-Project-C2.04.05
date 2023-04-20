
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.courseLecture.list.label.course" path="course"/>
	<acme:list-column code="lecturer.course.list.label.lecture" path="lecture"/>
</acme:list>
<jstl:if test="${_command == 'list'}">
	<acme:button code="lecturer.courseLecture.list.button.create" action="/lecturer/course-lecture/create"/>
</jstl:if>
