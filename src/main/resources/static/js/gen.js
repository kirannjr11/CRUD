<!--    fetching roles-->

    function fetchUsers() {
        $.ajax({
            url: "/admin/userData",
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
                        "<td><button class='btn btn-primary btn-sm editBtn' data-id='" + user.id + "' data-bs-toggle='modal' data-bs-target='#exampleModal'>Edit</button></td>" +
                        "<td><button class='btn btn-danger btn-sm deleteBtn' data-id='" + user.id + "'>Delete</button></td>" +
                        "</tr>";

                    tableBody.append(row);
                });
            },
            error: function(xhr, status, error) {
                console.log("error while fetching user data: " + error);
            }
        });
    }

<!--generating roles-->

function generateRoles() {
    var preDefinedRoles = ["ROLE_ADMIN", "ROLE_USER"];
    var selectRoles = $("#roles");
    selectRoles.empty();
    preDefinedRoles.forEach(function(role) {
        selectRoles.append('<option value="' + role + '">' + role + '</option>');
    });
}

function fetchAndGenerateRoles() {
    generateRoles();
}

$(document).ready(function() {
    fetchAndGenerateRoles();
});

<!--    add User-->
$(document).ready(function() {
    $("#addUser").click(function() {
        $("#exampleModal2").modal('show');
    });
})

$("#addBtn").click(function() {
    var firstName = $("#addfirstName").val();
    var lastName = $("#addlastName").val();
    var country = $("#addcountry").val();
    var password = $("#addpassword").val();
    var roles = $("#roles").val();

    if (firstName === '' || lastName === '' || country === '' || password === '' || roles === null || roles.length === 0) {
        alert("Please fill out all required fields.");
        return;
    }

    $.ajax({
        url: "/admin/addUser",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            firstName: firstName,
            lastName: lastName,
            country: country,
            password: password,
            roles: roles
        }),
        success: function(response) {
         $('#exampleModal2').modal('hide');
            $("#successMessage").text(response.firstName + " has been added successfully").fadeIn();

            setTimeout(function() {
                $("#successMessage").fadeOut();
            },3000);

            fetchUsers();
        },
        error: function(xhr, status, error) {
            console.log("Error while adding new user: " + error);
        }
    });
});

<!--edit button-->

$(document).on("click", ".editBtn", function() {
    var id = $(this).data("id");
    $.ajax({
        url: "/admin/edit?id=" + id,
        type: "GET",
        datatype: "json",
        success: function(data) {
            $("#editId").val(data.id);
            $("#editfirstName").val(data.firstName);
            $("#editlastName").val(data.lastName);
            $("#editcountry").val(data.country);
            $("#editPassword").val(data.password);

            $("#editRoles").empty();
            data.roles.forEach(function(role) {
                 $("#editRoles").append('<option value="' + role.id + '">' + role.role + '</option>');
            });
//            $("#editSection").show();

            $('#exampleModal').modal('show');

        },
        error: function(xhr, status, error) {
            console.log("error while editing: " + error);
        }
    });
});

<!--cancel button-->

$("#cancelBtn").click(function() {
     $("#editfirstName").val("");
    $("#editlastName").val("");
    $("#editcountry").val("");
    $("#editpassword").val("");
    $("#editroles").val("")

     $('#exampleModal').modal('hide');
})


<!--fetching roles to edit-->

// Function to fetch roles and populate the dropdown
function fetchRolesForEditForm() {
    $.ajax({
        url: "/admin/roles",
        type: "GET",
        success: function(response) {
            var rolesSelect = $("#editroles");
            rolesSelect.empty(); // Clear previous options
            $.each(response, function(index, role) {
                rolesSelect.append("<option value='" + role.role + "'>" + role.role + "</option>");
            });
        },
        error: function(xhr, status, error) {
            console.log("Error while fetching roles: " + error);
        }
    });
}

// Call the function to fetch roles when the page is loaded or when the edit form is opened
$(document).ready(function() {
    fetchRolesForEditForm();
});


<!--update-->

$("#updateBtn").click(function() {
    var id = $("#editId").val();
    var firstName = $("#editfirstName").val();
    var lastName = $("#editlastName").val();
    var country = $("#editcountry").val();
    var password = $("#editpassword").val();
    var roles = $("#editroles").val();



    if (firstName === '' || lastName === '' || country === '' || password === '' || roles === null || roles.length === 0) {
        alert("Please fill out all required fields.");
        return;
    }
    $('#exampleModal').modal('hide');


    $.ajax({
        url: "/admin/user/update?id=" + id,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify({
            id: id,
            firstName: firstName,
            lastName: lastName,
            country: country,
            password: password,
            roles: roles
        }),
        success: function(response) {
            alert("Do you want to update User?: " + response.firstName);
            $("#editSection").hide();
            fetchUsers();

            $("#editId").val("");
            $("#editfirstName").val("");
            $("#editlastName").val("");
            $("#editcountry").val("");
            $("#editpassword").val("");
            $("#editroles").val("");

        },
        error: function(xhr, status, error) {
            console.log("Can not update User: " + error);
        }
    });
});


<!--delete-->

$(document).on("click", ".deleteBtn", function() {
    var id = $(this).data("id");
    if (confirm("Do you really want to delete this user?")) {
        $.ajax({
            url: "/admin/user?id=" + id, // Corrected this line
            type: "DELETE",
            success: function(response) {
                fetchUsers();
            },
            error: function(xhr, status, error) {
                console.log("Can not delete user: " + error);
            }
        });
    }
});

$(document).ready(function() {
    fetchUsers();
});