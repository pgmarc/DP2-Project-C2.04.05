<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialsession.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorialsession.form.label.abstrac" path="abstrac"/>
	<acme:input-textarea code="assistant.tutorialsession.form.label.goals" path="goals"/>
	<jstl:if test="${_command != 'create'}">
		<acme:input-double readonly="true" code="assistant.tutorialsession.form.label.estimatedHours" path="estimatedHours"/>
		<acme:input-textbox readonly="true" code="assistant.tutorialsession.form.label.assistant" path="assistantName"/>
	</jstl:if>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="assistant.tutorialsession.form.button.session" action="/assistant/tutorial/show?id=${tutorialId}"/>				
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="assistant.tutorialsession.form.button.update" action="/assistant/tutorial-session/update"/>
			<acme:submit code="assistant.tutorialsession.form.button.delete" action="/assistant/tutorial-session/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="assistant.tutorialsession.form.button.create" action="/assistant/tutorial-session/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

