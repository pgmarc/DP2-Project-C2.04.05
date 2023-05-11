<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<acme:form>
	<acme:input-checkbox code="authenticated.bulletin.list.label.critical" path="critical"/>	
	<acme:input-textbox code="authenticated.bulletin.list.label.title" path="title"/>
	<acme:input-textarea code="authenticated.bulletin.list.label.message" path="message"/>
	<acme:input-url code="authenticated.bulletin.list.label.moreInfo" path="moreInfo"/>
	<jstl:if test="${_command == 'show' }">
		<acme:input-moment code="authenticated.bulletin.list.label.instantiationMoment" path="instantiationMoment"/>
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
		<tags:modal modalBody="authenticated.bulletin.form.button.create.modal.body" 
		modalTitle="authenticated.bulletin.form.button.create.modal.title" 
		buttonName="authenticated.bulletin.form.button.create" 
		action="/administrator/bulletin/create"  
		modalButtonCancelName="authenticated.bulletin.form.button.close" 
		modalButtonSubmitName="authenticated.bulletin.form.button.create"/>
	</jstl:if>

</acme:form>