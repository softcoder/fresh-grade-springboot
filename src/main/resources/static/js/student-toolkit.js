

function getRandomUser() {
    $.ajax({
      //url: 'https://randomuser.me/api/?inc=name,picture',
      url: '/v1/student-manager/create_random',
      type: "POST",
      contentType: "application/json; charset=utf-8",
      dataType: 'json',
      success: function(data) {
        //debugger;        
        console.log(data);

        var encodedName = data.firstName + ' ' + data.lastName;
        $("#full_name").val(encodedName);
        $('#photoFilePreview').attr('src', 'data:image/jpg;base64,' + data.photo).fadeIn();
      }
    });
}

function dataURItoBlob(dataURI) {
    // convert base64/URLEncoded data component to raw binary data held in a string
    var byteString;
    if (dataURI.split(',')[0].indexOf('base64') >= 0) {
        byteString = atob(dataURI.split(',')[1]);
    }
    else {
        byteString = unescape(dataURI.split(',')[1]);
    }
    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];

    // write the bytes of the string to a typed array
    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ia], {type:mimeString});
}

function readURL(input,dest) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(dest).attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    }
}

function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
