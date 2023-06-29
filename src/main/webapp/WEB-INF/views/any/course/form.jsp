
<%@page language="java"%>
<%@page language="java" import="acme.framework.helpers.PrincipalHelper"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.course.list.label.code" path="code"/>
	<acme:input-textbox code="any.course.form.label.title" path="title"/>
	<acme:input-textarea code="any.course.form.label.courseAbstract" path="courseAbstract"/>
	<acme:input-textbox code="any.course.form.label.nature" path="nature"/>
	<acme:input-money code="any.course.form.label.retailPrice" path="retailPrice"/>
	<acme:input-url code="any.course.form.label.moreInfo" path="moreInfo"/>
	<acme:input-textbox code="lecturer.course.form.label.lecturer" path="lecturer"/>
</acme:form>

<jstl:choose>
	<jstl:when test="hasRole('Auditor')">
		<acme:button code="any.audit.form.button.audit.create" action="/auditor/audit/create?courseId=${id}"/>
	</jstl:when>
</jstl:choose>

<acme:check-access test="hasRole('Authenticated')">
	<acme:button code="authenticated.practicum.form.button.list" action="/authenticated/practicum/list?courseId=${id}"/>
</acme:check-access>

<acme:button code="any.audit.form.button.audit.list" action="/any/audit/list?courseId=${id}"/> 		