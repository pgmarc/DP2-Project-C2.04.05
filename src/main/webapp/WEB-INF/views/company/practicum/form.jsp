<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-select code="company.practicum.form.label.course" path="course" choices="${courses}"/>
	<acme:input-textbox code="company.practicum.form.label.code" path="code"/>
	<acme:input-textbox code="company.practicum.form.label.title" path="title"/>
	<acme:input-textbox code="company.practicum.form.label.practicumAbstract" path="practicumAbstract"/>
	<acme:input-textbox code="company.practicum.form.label.goals" path="goals"/>
	<acme:input-moment code="company.practicum.form.label.startingDate" path="startingDate" readonly="true"/>
	<acme:input-moment code="company.practicum.form.label.endingDate" path="endingDate" readonly="true"/>
	<acme:input-double code="company.practicum.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>
	<acme:input-double code="company.practicum.form.label.practicaPeriodLength" path="practicaPeriodLength" readonly="true"/>
	<acme:input-checkbox code="company.practicum.form.label.draftMode" path="draftMode" readonly="true"/>
	
	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="company.practicum.form.button.sessions" action="/company/practicum-session/list?practicumId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="company.practicum.form.button.sessions" action="/company/practicum-session/list?practicumId=${id}"/>
			<acme:submit code="company.practicum.form.button.update" action="/company/practicum/update?id=${id}"/>
			<acme:submit code="company.practicum.form.button.delete" action="/company/practicum/delete?id=${id}"/>
			<acme:submit code="company.practicum.form.button.publish" action="/company/practicum/publish?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.practicum.form.button.create" action="/company/practicum/create"/>			
		</jstl:when>	
	</jstl:choose>
</acme:form>
