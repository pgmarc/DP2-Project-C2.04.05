<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="authenticated.course.list.label.code" path="code"/>
	<acme:input-textbox code="authenticated.course.list.label.title" path="title"/>
	<acme:input-textbox code="authenticated.course.list.label.abstract" path="courseAbstract"/>
	<acme:input-textbox code="authenticated.course.list.label.nature" path="nature"/>
	<acme:input-double code="authenticated.course.list.label.price" path="retailPrice"/>
	<acme:input-url code="authenticated.course.list.label.moreInfo" path="moreInfo"/>
</acme:form>

<acme:button code="authenticated.course.list.label.lectures" 
action="/student/lecture/list?courseId=${id}"/>

<acme:button test="${enrolment == null}" code="authenticated.course.list.label.enrolment" 
action="/student/enrolment/create?courseId=${id}"/>
