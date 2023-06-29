<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan"/>
	<acme:list-column code="administrator.banner.list.label.lastModified" path="lastModified"/>
	<acme:list-column code="administrator.banner.list.label.target" path="target"/>
</acme:list>

<acme:button code="administrator.banner.form.button.create" action="/administrator/banner/create"/>