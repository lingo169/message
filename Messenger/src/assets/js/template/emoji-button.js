//
// Emoji Button
//

import { isMobile } from './ismobile.js';
import { EmojiButton } from 'emoji-button';

const emojiForm = document.querySelectorAll('[data-emoji-form]');

if ( !isMobile.any() ) {
    emojiForm.forEach(form => {
        const button = form.querySelector('[data-emoji-btn]');
        const picker = new EmojiButton({
            autoHide:           false,
            emojiSize:          '1.125rem',
            position:           'top',
            zIndex:              1041,
            recentsCount:        15,
            showSearch:          true,
            showPreview:         false,
            showCategoryButtons: false,
            emojisPerRow:        10,
            rows:                8,
            inputClass:         'form-control',
            initialCategory:    (localStorage.getItem('emojiPicker.recent') === null) ? 'smileys' : 'recents',
            i18n: {
                search: 'Search',
                categories: {
                    recents: 'Frequently Used',
                    smileys: 'Faces & Emotion'
                },
                notFound: 'Oops! Nothing found!'
            }
        });

        picker.on('emoji', selection => {
            form.querySelector('[data-emoji-input]').value += selection.emoji;
        });

        button.addEventListener('click',  () => {
            picker.pickerVisible ? picker.hidePicker() : picker.showPicker(button);
        });
    });

} else {
    emojiForm.forEach(form => {
        form.querySelector('[data-emoji-btn]').style.display = 'none';
    });
}