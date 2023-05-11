<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.activity.list.label.title" path="title"/>
	<acme:list-column code="authenticated.activity.list.label.abstract" path="abstract$"/>

</acme:list>

<acme:button code="authenticated.activity.form.button.create" action="/student/activity/create?enrolmentId=${param.id}"/>
