<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="authenticated.enrolment.list.label.code" path="code" readonly="true"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.motivation" path="motivation" readonly="true"/>
			<acme:input-textarea code="authenticated.enrolment.list.label.goals" path="goals" readonly="true"/>
			<acme:input-double code="authenticated.enrolment.list.label.workTime" path="workTime" readonly="true"/>
			<acme:input-double code="authenticated.enrolment.list.label.courseCode" path="courseCode" readonly="true"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.courseTitle" path="courseTitle" readonly="true"/>
		</jstl:when>
		<jstl:when test="${_command == 'finalized'}">
			<acme:input-textbox code="authenticated.enrolment.list.label.holder" path="holder"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.creditCardNumber" path="creditCardNumber"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.expiryDate" path="expiryDate"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.securityCode" path="securityCode"/>
			<acme:submit code="authenticated.enrolment.form.button.finalized" action="/student/enrolment/finalized?id=${id}"/>
			
		</jstl:when>
		<jstl:otherwise>
			<acme:input-textbox code="authenticated.enrolment.list.label.code" path="code"/>
			<acme:input-textbox code="authenticated.enrolment.list.label.motivation" path="motivation"/>
			<acme:input-textarea code="authenticated.enrolment.list.label.goals" path="goals"/>
		</jstl:otherwise>
	
	</jstl:choose>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.enrolment.form.button.create" action="/student/enrolment/create?courseId=${param.courseId}"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.enrolment.form.button.update" action="/student/enrolment/update"/>
	<acme:submit test="${_command == 'show' && draftMode == true}" code="authenticated.enrolment.form.button.delete" action="/student/enrolment/delete?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == true}" code="authenticated.enrolment.form.button.update" action="/student/enrolment/update?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == true}" code="authenticated.enrolment.form.button.finalized" action="/student/enrolment/finalized?id=${id}"/>
	<acme:button test="${_command == 'show' && draftMode == false}" code="authenticated.enrolment.form.button.workspace" action="/student/activity/list?id=${id}"/>

</acme:form>