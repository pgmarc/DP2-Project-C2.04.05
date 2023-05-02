
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
<jstl:choose>
	<jstl:when test="${_command =='show'}">
		<acme:input-textbox code="lecturer.courseLecture.form.course" path="course" readonly="true"/>
		<acme:input-textbox code="lecturer.courseLecture.form.lecture" path="lecture" readonly="true"/>
	</jstl:when>
	<jstl:otherwise>
		<acme:input-select code="lecturer.courseLecture.form.course" path="course" choices="${courses}"/>
		<acme:input-select code="lecturer.courseLecture.form.lecture" path="lecture" choices="${lectures}"/>
	</jstl:otherwise>
</jstl:choose>
<acme:submit test="${_command=='show'}" code="lecturer.courseLecture.form.button.delete" action="/lecturer/course-lecture/delete?id=${id}"/>
<acme:submit test="${_command=='create'}" code="lecturer.courseLecture.form.button.create" action="/lecturer/course-lecture/create"/>
</acme:form>
