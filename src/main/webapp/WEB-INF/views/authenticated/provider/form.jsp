<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.provider.form.label.company" path="company"/>
	<acme:input-textbox code="authenticated.provider.form.label.sector" path="sector"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.provider.form.button.create" action="/authenticated/provider/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.provider.form.button.update" action="/authenticated/provider/update"/>
</acme:form>
