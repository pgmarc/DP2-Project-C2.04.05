<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.activity.list.label.title" path="title"/>
	<acme:input-textarea code="authenticated.activity.list.label.abstract" path="abstract$"/>
	<acme:input-url code="authenticated.activity.list.label.moreInfo" path="moreInfo"/>
	<acme:input-textbox code="authenticated.activity.list.label.type" path="type"/>
	<acme:input-moment code="authenticated.activity.list.label.startDate" path="startDate"/>
	<acme:input-moment code="authenticated.activity.list.label.endDate" path="endDate"/>
	

</acme:form>