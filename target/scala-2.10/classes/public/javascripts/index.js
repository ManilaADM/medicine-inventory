(function() {

  $(function() {
    return $.get("/employee", function(data) {
      return $.each(data, function(index, employee) {
        return $("#employees").append($("<li>").text(employee.employee_id));
      });
    });
  });

}).call(this);
