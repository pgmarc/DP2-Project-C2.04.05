
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="lecturer.course.list.label.code" path="code"/>
	<acme:list-column code="lecturer.course.list.label.title" path="title"/>
	<acme:list-column code="lecturer.course.list.label.courseAbstract" path="courseAbstract"/>
	<acme:list-column code="lecturer.course.list.label.nature" path="nature"/>
	<acme:list-column code="lecturer.course.list.label.retailPrice" path="retailPrice"/>
	<acme:list-column code="lecturer.course.list.label.moreInfo" path="moreInfo"/>
	<acme:list-column code="lecturer.course.list.label.lecturer" path="lecturer"/>
</acme:list>
<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="lecturer.course.list.button.create" action="/lecturer/course/create"/>
</jstl:if>
