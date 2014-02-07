$ ->
	$.get "/employee", (data) ->
		$.each data, (index, employee) ->
			$("#employees").append $("<li>").text employee.employee_id