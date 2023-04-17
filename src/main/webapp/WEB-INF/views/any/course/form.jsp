
<%@page language="java"%>

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
