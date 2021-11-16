// Preview added images before user submits report
function previewFiles() {
    var preview = document.querySelector('#preview');
    var files   = document.querySelector('input[type=file]').files;

    console.log(preview);

    function readAndPreview(file) {

        // Make sure `file.name` matches our extensions criteria
        if ( /\.(jpe?g|png|gif)$/i.test(file.name) ) {
            var reader = new FileReader();

            reader.addEventListener("load", function () {
                var image = new Image();
                image.height = 150;
                image.width = 150;
                image.title = file.name;
                image.src = this.result;
                preview.appendChild( image );
            }, false);

            reader.readAsDataURL(file);
        }

    }

    if (files) {
        [].forEach.call(files, readAndPreview);
    }

}

// Make My account dropdown menu in banner dynamic
var dropdown = document.querySelector('.dropdown');
dropdown.addEventListener('click', function(event) {
    event.stopPropagation();
    dropdown.classList.toggle('is-active');
});

