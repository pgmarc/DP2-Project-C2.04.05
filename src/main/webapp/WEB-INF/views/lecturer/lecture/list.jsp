
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.lecture.list.label.title" path="title"/>
	<acme:list-column code="lecturer.lecture.list.label.lectureAbstract" path="lectureAbstract"/>
	<acme:list-column code="lecturer.lecture.list.label.nature" path="nature"/>
</acme:list>
<jstl:if test="${_command == 'list-all'}">
	<acme:button code="lecturer.lecture.list.button.create" action="/lecturer/lecture/create"/>
</jstl:if>


