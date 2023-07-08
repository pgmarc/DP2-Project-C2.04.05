<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="assistant.tutorialsession.form.label.title" path="title"/>
	<acme:input-textarea code="assistant.tutorialsession.form.label.abstrac" path="abstrac"/>
	<acme:input-textarea code="assistant.tutorialsession.form.label.goals" path="goals"/>
	<acme:input-select code="assistant.tutorialsession.form.label.natures" path="sessionNature" choices="${natures}"/>
	<acme:input-moment code="assistant.tutorialsession.form.label.start" path="startDate"/>
	<acme:input-moment code="assistant.tutorialsession.form.label.finish" path="finishDate"/>
		 
	<jstl:if test="${_command == 'show'}">
		<acme:button code="assistant.tutorialsession.form.button.tutorial" action="/assistant/tutorial/show?id=${tutorialId}"/>				
	</jstl:if>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
		<acme:submit code="assistant.tutorialsession.form.button.update" action="/assistant/tutorial-session/update"/>
		<acme:submit code="assistant.tutorialsession.form.button.delete" action="/assistant/tutorial-session/delete"/>
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="assistant.tutorialsession.form.button.create" action="/assistant/tutorial-session/create?tutorialId=${tutorialId}"/>
	</jstl:if>		
</acme:form>

