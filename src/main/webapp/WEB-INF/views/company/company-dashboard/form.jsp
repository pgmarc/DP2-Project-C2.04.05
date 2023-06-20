<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>

<h2>
	<acme:message code="company.dashboard.form.title"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.average-practicum-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionsPeriodLengthAveragePerPractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviation-practicum-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionsPeriodLengthDeviationPerPractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.min-practicum-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionsPeriodLengthMinimumPerPractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.max-practicum-sessions"/>
		</th>
		<td>
			<acme:print value="${sessionsPeriodLengthMaximunPerPractica}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.average-practicum-length"/>
		</th>
		<td>
			<acme:print value="${practicaPeriodLengthAverage}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.deviation-practicum-length"/>
		</th>
		<td>
			<acme:print value="${practicaPeriodLengthDeviation}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.min-practicum-length"/>
		</th>
		<td>
			<acme:print value="${practicaPeriodLengthMinimun}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:message code="company.dashboard.form.label.max-practicum-length"/>
		</th>
		<td>
			<acme:print value="${practicaPeriodLengthMaximun}"/>
		</td>
	</tr>	
</table>

<h2>
	<acme:message code="company.dashboard.form.label.active-practicum-month"/>
</h2>


<div>
	<canvas id="canvas"></canvas>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		var data = {
			labels : [
				"<acme:message code="company.dashboard.form.label.january"/>",
				"<acme:message code="company.dashboard.form.label.february"/>",
				"<acme:message code="company.dashboard.form.label.march"/>",
				"<acme:message code="company.dashboard.form.label.april"/>",
				"<acme:message code="company.dashboard.form.label.may"/>",
				"<acme:message code="company.dashboard.form.label.june"/>",
				"<acme:message code="company.dashboard.form.label.july"/>",
				"<acme:message code="company.dashboard.form.label.august"/>",
				"<acme:message code="company.dashboard.form.label.september"/>",
				"<acme:message code="company.dashboard.form.label.october"/>",
				"<acme:message code="company.dashboard.form.label.november"/>",
				"<acme:message code="company.dashboard.form.label.december"/>"
			],
			datasets : [
				{
					data : [
						<jstl:out value="${activePracticumByMonth[0]}"/>, 
						<jstl:out value="${activePracticumByMonth[1]}"/>, 
						<jstl:out value="${activePracticumByMonth[2]}"/>,
						<jstl:out value="${activePracticumByMonth[3]}"/>, 
						<jstl:out value="${activePracticumByMonth[4]}"/>, 
						<jstl:out value="${activePracticumByMonth[5]}"/>,
						<jstl:out value="${activePracticumByMonth[6]}"/>, 
						<jstl:out value="${activePracticumByMonth[7]}"/>, 
						<jstl:out value="${activePracticumByMonth[8]}"/>,
						<jstl:out value="${activePracticumByMonth[9]}"/>, 
						<jstl:out value="${activePracticumByMonth[10]}"/>, 
						<jstl:out value="${activePracticumByMonth[11]}"/>,
					]
				}
			]
		};
				
		var options = {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0,
							stepSize: 1,
						}
					}
				]
			},
			legend : {
				display : false
			}
		};
	
		var canvas, context;
	
		canvas = document.getElementById("canvas");
		context = canvas.getContext("2d");
		new Chart(context, {
			type : "horizontalBar",
			data : data,
			options : options
		});
	});
</script>

<acme:return/>

