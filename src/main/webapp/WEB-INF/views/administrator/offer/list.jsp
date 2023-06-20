<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.offer.list.label.startingDate" path="startingDate"/>
	<acme:list-column code="administrator.offer.list.label.endingDate" path="endingDate"/>
	<acme:list-column code="administrator.offer.list.label.heading" path="heading"/>	
</acme:list>

