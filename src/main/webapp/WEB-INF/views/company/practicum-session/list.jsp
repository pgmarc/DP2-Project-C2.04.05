<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<div class= "row">
	<div class="col-md">
		<p>
			<acme:message code="company.addendum.list.disclaimer"/>
		</p>
	</div>
</div>

<acme:list>
	<acme:list-column code="company.session.list.label.startingDate" path="startingDate"/>
	<acme:list-column code="company.session.list.label.endingDate" path="endingDate"/>
	<acme:list-column code="company.session.list.label.title" path="title"/>
	<acme:list-column code="company.session.list.label.addendum" path="addendum"/>
</acme:list>

<acme:button test="${showCreateAddendum}" code="company.session.form.button.create-addendum" action="/company/practicum-session/create-addendum?practicumId=${practicumId}"/>
<acme:button test="${showCreate}" code="company.session.form.button.create" action="/company/practicum-session/create?practicumId=${practicumId}"/>
