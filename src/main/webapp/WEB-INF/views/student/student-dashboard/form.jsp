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

<h2>
	<acme:message code="student.dashboard.form.title.general-indicators"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.number-theory"/>
		</th>
		<td>
			<acme:print value="${numberOfTheoryActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.number-handson"/>
		</th>
		<td>
			<acme:print value="${numberOfHandsOnActivities}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.number-balanced"/>
		</th>
		<td>
			<acme:print value="${numberOfBalancedActivities}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.avg-workbook"/>
		</th>
		<td>
			<acme:print value="${averageWorkbook}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.max-workbook"/>
		</th>
		<td>
			<acme:print value="${maximumWorkbook}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.min-workbook"/>
		</th>
		<td>
			<acme:print value="${minimumWorkbook}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.stddev-workbook"/>
		</th>
		<td>
			<acme:print value="${deviationWorkbook}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.avg-learning"/>
		</th>
		<td>
			<acme:print value="${averageCourse}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.max-learning"/>
		</th>
		<td>
			<acme:print value="${maximumCourse}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.min-learning"/>
		</th>
		<td>
			<acme:print value="${minimumCourse}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:message code="student.dashboard.form.label.stddev-learning"/>
		</th>
		<td>
			<acme:print value="${deviationCourse}"/>
		</td>
	</tr>	
</table>