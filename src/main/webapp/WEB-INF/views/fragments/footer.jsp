<%--
- footer.jsp
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
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:footer-panel>
	<acme:footer-subpanel code="master.footer.title.about">
		<acme:footer-option icon="fa fa-building" code="master.footer.label.company" action="/master/company"/>
		<acme:footer-option icon="fa fa-file" code="master.footer.label.license" action="/master/license"/>		
	</acme:footer-subpanel>

	<acme:footer-subpanel code="master.footer.title.languages">
		<acme:footer-option icon="fa fa-language" code="master.footer.label.english" action="/?locale=en"/>
		<acme:footer-option icon="fa fa-language" code="master.footer.label.spanish" action="/?locale=es"/>
	</acme:footer-subpanel>

	<acme:footer-logo logo="images/logo.png">
		<acme:footer-copyright code="master.company.name"/>
	</acme:footer-logo>		

</acme:footer-panel>

