function fetchUsers() {
         $.ajax({
             url: "/usersList",
             type: "GET",
             dataType: "json",
             success: function(data) {
                 var tableBody = $("#userTable");
                 tableBody.empty();
                 data.forEach(function(user) {
                     var row = "<tr>" +
                         "<td>" + user.id + "</td>" +
                         "<td>" + user.firstName + "</td>" +
                         "<td>" + user.lastName + "</td>" +
                         "<td>" + user.country + "</td>" +
                         "<td>" + user.roles.map(role => role.role).join(", ") + "</td>" +
                         "</tr>";

                     tableBody.append(row);
                 });
             },
             error: function(xhr, status, error) {
                 console.log("error while fetching user data: " + error);
             }
         });
     }
    fetchUsers();