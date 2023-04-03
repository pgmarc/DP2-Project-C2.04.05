<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.enrolment.list.label.code" path="code"/>
	<acme:input-textbox code="authenticated.enrolment.list.label.motivation" path="motivation"/>
	<acme:input-textarea code="authenticated.enrolment.list.label.goals" path="goals"/>
	<jstl:if test="${_command == 'show' }">
		<acme:input-double code="authenticated.enrolment.list.label.workTime" path="workTime" readonly="true"/>
	</jstl:if>
	<jstl:if test="${_command == 'create' }">
		<acme:input-select code="authenticated.enrolment.list.label.course" path="course"
		choices="courses"/>
	</jstl:if>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.enrolment.form.button.create" action="/authenticated/enrolment/create"/>

</acme:form>