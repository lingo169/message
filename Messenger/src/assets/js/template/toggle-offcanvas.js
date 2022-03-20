//
// Toggle chat
//

(function toggleChat() {
    const main = document.querySelector('.main');
    const elements = document.querySelectorAll('[data-toggle-chat]');

    Array.prototype.forEach.call(elements, (element) => {
        element.addEventListener('click', () => {
            main.classList.toggle('is-visible');
        }, false);
    });
})();