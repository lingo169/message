//
// Bootstrap Tooltips
//

import { Tooltip } from 'bootstrap';

const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');

tooltipTriggerList.forEach(tooltip => {
    new Tooltip(tooltip, {
        html: true
    });
});