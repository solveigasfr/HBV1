// Preview added images before user submits report
function previewFiles() {
    const preview = document.querySelector('#preview');
    const files = document.querySelector('input[type=file]').files;

    function readAndPreview(file) {
        // Make sure `file.name` matches our extensions criteria
        if (/\.(jpe?g|png|gif)$/i.test(file.name)) {
            const reader = new FileReader();

            reader.addEventListener("load",function ()  {
                const image = new Image();
                image.height = 150;
                image.width = 150;
                image.title = file.name;
                image.src = this.result;
                preview.appendChild(image);
            }, false);

            reader.readAsDataURL(file);
        }
    }

    if (files) {
        [].forEach.call(files, readAndPreview);
    }
}

// let serverContext = "[[@{/}]]";
//     function savePassword(){
//         // Check if the old password given is correct
//         var validateOld = loggedIn.getUserPassword() === $("#oldPassword ").val();
//         if(!validateOld) {
//             $("#error").show();
//             return;
//         }
//
//         // Check if new password and confirmed password match
//         var newPassword = $("#newPassword").val();
//         var validateNew = password == $("#confirmPassword").val();
//         if(!valid) {
//             $("#error").show();
//             return;
//         }
//
//         $.post(serverContext + "userController/updatePassword",
//             {password: password, oldpassword: $("#oldpass").val()} ,function(data){
//                 window.location.href = serverContext +"/home.html?message="+data.message;
//             })
//             .fail(function(data) {
//                 $("#errormsg").show().html(data.responseJSON.message);
//             });
//     }

function init(event) {

    // Make My account dropdown menu togglable
    const dropdown = document.querySelector('.dropdown');
    const dropdownButton = document.querySelector('.dropdown button');

    if(dropdownButton) {
        dropdownButton.addEventListener('click', () => {
            dropdown.classList.toggle('is-active');
        });
    }

}

window.addEventListener("DOMContentLoaded", init);
