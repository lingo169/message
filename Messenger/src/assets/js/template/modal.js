//
// Passing img src to Bootstrap modal
//

const modalPreview = document.getElementById('modal-media-preview');

if (modalPreview) {
    modalPreview.addEventListener('show.bs.modal', function (event) {
        let button = event.relatedTarget;
        let recipient = button.getAttribute('data-theme-img-url');
        let modalImg = modalPreview.querySelector('.modal-preview-url')
        modalImg.src = recipient;
    })
}
