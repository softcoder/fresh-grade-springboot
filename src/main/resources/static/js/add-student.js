
function trigger_submit_form() {
    //debugger;
    var encodedName = encodeURIComponent($("#full_name").val());
    if(encodedName != null && encodedName.trim() != '') {
        var formData = null;
        if($('input[type=file]')[0].files.length > 0) {
            formData = new FormData();
            var photoData = $('input[type=file]')[0].files[0];
            formData.append('photoFile', photoData); 
        }
        // Can't copy the Image to upload to the rest API when pulled down from another domain.
        else if($('#photoFilePreview').attr('src') != '') {
            var photo = dataURItoBlob($('#photoFilePreview').attr('src'));
            formData = new FormData();
            formData.append('photoFile', photo);
        }
        $.ajax({
            url: "/v1/student-manager/create_fullname?full_name="+encodedName,
            type: "POST",
            encoding:"UTF-8",
            dataType: 'json',
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function ( data ) {
                window.location.href = "/view_student_detail.html?student_id="+data.id;
            },
            error: function (xhr, ajaxOptions, thrownError) {
            	if(xhr.responseText.indexOf('The field photoFile exceeds its maximum permitted size of') != -1) {
            		var item = xhr.responseText;
            		var matches = item.match('/.*The field photoFile exceeds its maximum permitted size of (.*) bytes.*/');
            		alert("Uploaded files should not be larger than " + matches[1] + ' bytes');
                }
            	else {
                	alert('Error Status: '+ xhr.status + ' message: ' + xhr.responseText);
            	}
            }
        });
    }
    else {
        $('#full_name_submit').trigger('click');
    }
}
