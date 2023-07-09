<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<acme:form>
	<acme:input-moment code="administrator.bulletin.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="administrator.bulletin.form.label.title" path="title"/>
	<acme:input-textbox code="administrator.bulletin.form.label.message" path="message"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.critical" path="critical"/>	
	<acme:input-url code="administrator.bulletin.form.label.moreInfo" path="moreInfo"/>
	<acme:input-checkbox code="administrator.bulletin.form.label.confirmation" path="confirmation"/>
	<acme:submit code="administrator.bulletin.form.button.create" action="/administrator/bulletin/create"/>
</acme:form>
