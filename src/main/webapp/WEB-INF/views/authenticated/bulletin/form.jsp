<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<acme:form>
	<jstl:if test="${_command == 'show' }">
		<acme:input-moment code="authenticated.bulletin.list.label.instantiationMoment" path="instantiationMoment"/>
	</jstl:if>
	<acme:input-textbox code="authenticated.bulletin.list.label.title" path="title"/>
	<acme:input-textarea code="authenticated.bulletin.list.label.message" path="message"/>
	<acme:input-checkbox code="authenticated.bulletin.list.label.critical" path="critical"/>	
	<acme:input-url code="authenticated.bulletin.list.label.moreInfo" path="moreInfo"/>
</acme:form>