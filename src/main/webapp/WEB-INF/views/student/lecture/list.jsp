<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="authenticated.lecture.list.label.title" path="title"/>
	<acme:list-column code="authenticated.lecture.list.label.abstract" path="lectureAbstract"/>

</acme:list>