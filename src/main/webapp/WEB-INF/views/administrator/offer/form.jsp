<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="administrator.offer.form.label.instantiationMoment" path="instantiationMoment" readonly="true"/>
	<acme:input-textbox code="administrator.offer.form.label.heading" path="heading"/>
	<acme:input-textbox code="administrator.offer.form.label.summary" path="summary"/>
	<acme:input-moment code="administrator.offer.form.label.startingDate" path="startingDate"/>
	<acme:input-moment code="administrator.offer.form.label.endingDate" path="endingDate"/>
	<acme:input-money code="administrator.offer.form.label.price" path="price"/>
	<acme:input-url code="administrator.offer.form.label.moreInfo" path="moreInfo"/>
</acme:form>
