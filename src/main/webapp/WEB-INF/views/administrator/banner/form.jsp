<%--
- form.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.banner.form.label.slogan" path="slogan"/>
	<jstl:if test="${!acme:anyOf(_command, 'create|update')}">
		<acme:input-moment code="administrator.banner.form.label.lastModified" path="lastModified"/>
	</jstl:if>
	<acme:input-moment code="administrator.banner.form.label.displayStart" path="displayStart"/>
	<acme:input-moment code="administrator.banner.form.label.displayFinish" path="displayFinish"/>
	<acme:input-url code="administrator.banner.form.label.target" path="target"/>
	
	<acme:submit test="${acme:anyOf(_command, 'show|update')}" code="administrator.banner.form.button.update" action="/administrator/banner/update"/>
	<acme:submit test="${acme:anyOf(_command, 'show|delete')}" code="administrator.banner.form.button.delete" action="/administrator/banner/delete"/>
	<acme:submit test="${_command == 'create'}" code="administrator.banner.form.button.create" action="/administrator/banner/create"/>
</acme:form>


