// Preview added images before user submits report
function previewFiles() {
    const preview = document.querySelector('#preview');
    const files = document.querySelector('input[type=file]').files;

    function readAndPreview(file) {
        // Make sure `file.name` matches our extensions criteria
        if (/\.(jpe?g|png|gif)$/i.test(file.name)) {
            const reader = new FileReader();

            reader.addEventListener("load", function () {
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

function init(event) {

    // Make My account dropdown menu toggleable
    const dropdown = document.querySelector('.dropdown');
    const dropdownButton = document.querySelector('.dropdown button');

    if (dropdownButton) {
        dropdownButton.addEventListener('click', () => {
            dropdown.classList.toggle('is-active');
        });
    }

}

window.addEventListener("DOMContentLoaded", init);
