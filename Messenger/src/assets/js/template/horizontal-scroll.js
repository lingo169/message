//
// Horizontal scroll
//

[].forEach.call(document.querySelectorAll('[data-horizontal-scroll]'), function (el) {
    function scrollHorizontally(e) {
        e = window.event || e;
        const delta = Math.max(-1, Math.min(1, (e.wheelDelta || -e.detail)));
        el.scrollLeft -= (delta*28);
        e.preventDefault();
    }

    if (el.addEventListener) {
        el.addEventListener('mousewheel', scrollHorizontally, false);
        el.addEventListener('DOMMouseScroll', scrollHorizontally, false);
    } else {
        el.attachEvent('onmousewheel', scrollHorizontally);
    }
});