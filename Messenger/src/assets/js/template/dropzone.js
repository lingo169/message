//
// Dropzonejs
//

import { Dropzone } from 'dropzone';

const dzTemplate = `
<div class="theme-file-preview position-relative mx-2">
    <div class="avatar avatar-lg dropzone-file-preview">
        <span class="avatar-text rounded bg-secondary text-body file-title">
            <svg viewBox="0 0 24 24" width="24" height="24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1"><path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"></path><polyline points="13 2 13 9 20 9"></polyline></svg>
        </span>
    </div>

    <div class="avatar avatar-lg dropzone-image-preview">
        <img src="#" class="avatar-img rounded file-title" data-dz-thumbnail="" alt="" >
    </div>

    <a class="badge badge-circle bg-body text-white position-absolute top-0 end-0 m-2" href="#" data-dz-remove="">
        <svg viewBox="0 0 24 24" width="24" height="24" stroke="currentColor" stroke-width="2" fill="none" stroke-linecap="round" stroke-linejoin="round" class="css-i6dzq1"><line x1="18" y1="6" x2="6" y2="18"></line><line x1="6" y1="6" x2="18" y2="18"></line></svg>
    </a>
</div>
`;

if (document.querySelector('.chat-body-inner') && document.querySelector('.chat-body-inner')) {
    const watchHeight = document.querySelector('.chat-footer');

    function outputSize() {
        const target = document.querySelector('.chat-body-inner');

        if (target && watchHeight) {
            target.setAttribute('style', `padding-bottom: ${Number(watchHeight.offsetHeight)}px`);
        }
    }

    new ResizeObserver(outputSize).observe(watchHeight);
}


if (document.querySelector('[data-dropzone-area]')) {
    const dropzone = new Dropzone('[data-dropzone-area]', {
        url:               'post.php',
        clickable:         '#dz-btn',
        previewsContainer: '#dz-preview-row',
        previewTemplate:   dzTemplate
    });

    dropzone.on('addedfile', function(file) {
        let preview = document.querySelectorAll('.theme-file-preview')
        preview = preview[preview.length -1].querySelectorAll('.file-title');

        for (let i = 0; i < preview.length; i++) {
            preview[i].title = file.name;
        }


    });

    dropzone.on('addedfiles', function(file) {
        dropzone.previewsContainer.classList.add('dz-preview-moved', 'pb-10', 'pt-3', 'px-2');
    });

    dropzone.on('reset', function(file) {
        dropzone.previewsContainer.classList.remove('dz-preview-moved', 'pb-10', 'pt-3', 'px-2');
    });
}