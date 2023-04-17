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
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<acme:form>
	<acme:input-moment code="authenticated.note.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="authenticated.note.form.label.title" path="title"/>
	<acme:input-textbox code="authenticated.note.form.label.fullName" path="fullName" readonly="true"/>
	<acme:input-textbox code="authenticated.note.form.label.message" path="message"/>
	<acme:input-email code="authenticated.note.form.label.email" path="email"/>
	<acme:input-url code="authenticated.note.form.label.moreInfo" path="moreInfo"/>
	<jstl:if test="${_command == 'create'}">
		<tags:modal modalBody="authenticated.note.form.button.create.modal.body" 
		modalTitle="authenticated.note.form.button.create.modal.title" 
		buttonName="authenticated.note.form.button.create" 
		action="/authenticated/note/create"  
		modalButtonCancelName="authenticated.note.form.button.close" 
		modalButtonSubmitName="authenticated.note.form.button.create"/>
	</jstl:if>
</acme:form>
