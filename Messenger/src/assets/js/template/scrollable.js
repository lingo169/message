//
// Scroll to end of the chat
//

export function scrollable(selector) {
    selector = document.querySelectorAll(selector);

    if (selector.length) {
        selector[selector.length - 1].scrollIntoView(false, {
            block: 'end'
        });
    }
};

window.onload = function() {
    scrollable('.chat-body-inner');
};