<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="company.session.form.label.title" path="title"/>
	<acme:input-textbox code="company.session.form.label.sessionAbstract" path="sessionAbstract"/>
	<acme:input-moment code="company.session.form.label.startingDate" path="startingDate"/>
	<acme:input-moment code="company.session.form.label.endingDate" path="endingDate"/>
	<acme:input-url code="company.session.form.label.moreInfo" path="moreInfo"/>
	<acme:input-checkbox code="company.session.form.label.addendum" path="addendum" readonly="true"/>
	<jstl:if test="${_command == 'create-addendum'}">
		<acme:input-checkbox code="company.session.form.label.confirmation" path="confirmation"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="company.session.form.button.update" action="/company/practicum-session/update?id=${id}"/>
			<acme:submit code="company.session.form.button.delete" action="/company/practicum-session/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="company.session.form.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create-addendum'}">
			<acme:submit code="company.session.form.button.create-addendum" action="/company/practicum-session/create-addendum?practicumId=${practicumId}"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
