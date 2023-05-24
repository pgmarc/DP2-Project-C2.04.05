<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.practicum.form.label.code" path="code" readonly="true"/>
	<acme:input-textbox code="authenticated.practicum.form.label.title" path="title" readonly="true"/>
	<acme:input-textbox code="authenticated.practicum.form.label.practicumAbstract" path="practicumAbstract" readonly="true"/>
	<acme:input-textbox code="authenticated.practicum.form.label.goals" path="goals" readonly="true"/>
	<acme:input-moment code="authenticated.practicum.form.label.startingDate" path="startingDate" readonly="true"/>
	<acme:input-moment code="authenticated.practicum.form.label.endingDate" path="endingDate" readonly="true"/>
	<acme:input-double code="authenticated.practicum.form.label.estimatedTotalTime" path="estimatedTotalTime" readonly="true"/>
	<acme:input-double code="authenticated.practicum.form.label.practicaPeriodLength" path="practicaPeriodLength" readonly="true"/>
	<acme:input-textbox code="authenticated.company.form.label.name" path="companyName" readonly="true"/>
	<acme:input-textbox code="authenticated.company.form.label.vatNumber" path="companyVatNumber" readonly="true"/>
	<acme:input-textbox code="authenticated.company.form.label.summary" path="companySummary" readonly="true"/>
	<acme:input-url code="authenticated.company.form.label.moreInfo" path="companyMoreInfo" readonly="true"/>
</acme:form>
