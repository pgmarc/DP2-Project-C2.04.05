<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.offer.form.label.heading" path="heading" readonly="true"/>
	<acme:input-textbox code="authenticated.offer.form.label.summary" path="summary" readonly="true"/>
	<acme:input-moment code="authenticated.offer.form.label.startingDate" path="startingDate" readonly="true"/>
	<acme:input-moment code="authenticated.offer.form.label.endingDate" path="endingDate" readonly="true"/>
	<acme:input-money code="authenticated.offer.form.label.price" path="price" readonly="true"/>
	<acme:input-url code="authenticated.offer.form.label.moreInfo" path="moreInfo" readonly="true"/>
</acme:form>
