<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.enrolment.list.label.code" path="code"/>
	<acme:input-textbox code="authenticated.enrolment.list.label.motivation" path="motivation"/>
	<acme:input-textarea code="authenticated.enrolment.list.label.goals" path="goals"/>
	<acme:input-double code="authenticated.enrolment.list.label.workTime" path="workTime" readonly="true"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.enrolment.form.button.create" action="/student/enrolment/create?courseId=${param.courseId}"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.enrolment.form.button.update" action="/student/enrolment/update"/>
	<acme:submit test="${_command == 'show'}" code="authenticated.enrolment.form.button.delete" action="/student/enrolment/delete?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == true}" code="authenticated.enrolment.form.button.update" action="/student/enrolment/update?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == true}" code="authenticated.enrolment.form.button.publish" action="/student/enrolment/update?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == false}" code="authenticated.enrolment.form.button.workspace" action="/student/activity/list?id=${id}"/>

</acme:form>