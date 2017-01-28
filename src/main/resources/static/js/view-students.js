
function deleteStudent(student_id) {
    $.ajax({
        url: "/v1/student-manager/delete/"+student_id,
        type: "DELETE",
        success: function ( data ) {
            //debugger;
            $("#student_"+student_id).remove();
         },
         error: function (xhr, ajaxOptions, thrownError) {
            alert('Error Status: '+ xhr.status + ' message: ' + xhr.responseText);
         },
         cache: false
    });
}

function populateStudents() {
    $.ajax({
        url: "/v1/student-manager",
        success: function ( data ) {
            $.each(data,function(){
                $("#student_div").append("<tr id='student_"+this.id+"' name='student_"+this.id+"'>" + 
                "<td><a class='student-container-black no-decor-link' href='/view_student_detail.html?student_id="+this.id+"'>"+this.firstName+" "+this.lastName+"</a></td>"+
                "<td><a class='student-container-black right-arrow-gray no-decor-link' href='/view_student_detail.html?student_id="+this.id+"'>></a></td>"+
                "<td><a class='no-decor-link' href='#' onclick='deleteStudent("+this.id+"); return false;'><img class='delete-icon' title='Delete this student.' src='/images/delete-icon.png'></a></td></tr>");
            });
        },
        error: function (xhr, ajaxOptions, thrownError) {
            alert('Error Status: '+ xhr.status + ' message: ' + xhr.responseText);
        },
        cache: false
    });
}

function populateStudentDetails() {
    $.ajax({
        url: "/v1/student-manager/find/"+getParameterByName('student_id'),
        success: function ( data ) {
            $("#student_div").append("<span class='student-container-black-left'>"+data.firstName+"</span>")
            $("#student_div").append("<br>")
            $("#student_div").append("<span class='student-container-black-left'>"+data.lastName+"</span>")
            if(data.photo != null) {
                $("#photoFileDiv").html('<img class="photo-container" src="data:image/png;base64,' + data.photo + '" />');
            }
          },
          cache: false
    });
}