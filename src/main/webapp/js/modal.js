function showModal(options) {
    const { title, message, type = 'info', confirmText = 'OK', cancelText = 'Cancel', onConfirm = null } = options;
    
    // Remove existing modal if any
    const existing = document.getElementById('cs-modal-overlay');
    if (existing) existing.remove();

    const overlay = document.createElement('div');
    overlay.id = 'cs-modal-overlay';
    overlay.className = 'cs-modal-overlay';

    let iconHtml = '';
    if (type === 'danger') iconHtml = '<div class="cs-modal-icon danger"><i data-lucide="alert-triangle"></i></div>';
    else if (type === 'success') iconHtml = '<div class="cs-modal-icon success"><i data-lucide="check-circle"></i></div>';
    else iconHtml = '<div class="cs-modal-icon info"><i data-lucide="info"></i></div>';

    let buttonsHtml = '';
    if (onConfirm) {
        buttonsHtml = `
            <button class="btn btn-ghost cs-modal-cancel" style="margin-right: 8px;">${cancelText}</button>
            <button class="btn ${type === 'danger' ? 'btn-danger' : 'btn-primary'} cs-modal-confirm">${confirmText}</button>
        `;
    } else {
        buttonsHtml = `<button class="btn btn-primary cs-modal-confirm">${confirmText}</button>`;
    }

    overlay.innerHTML = `
        <div class="cs-modal">
            ${iconHtml}
            <div class="cs-modal-content">
                <h3>${title}</h3>
                <p>${message}</p>
            </div>
            <div class="cs-modal-actions" style="margin-top: 24px; text-align: right;">
                ${buttonsHtml}
            </div>
        </div>
    `;

    document.body.appendChild(overlay);
    if (typeof lucide !== 'undefined') lucide.createIcons();

    setTimeout(() => overlay.classList.add('visible'), 10);

    const closeModal = () => {
        overlay.classList.remove('visible');
        setTimeout(() => overlay.remove(), 250);
    };

    const confirmBtn = overlay.querySelector('.cs-modal-confirm');
    if (confirmBtn) {
        confirmBtn.addEventListener('click', () => {
            if (onConfirm) onConfirm();
            closeModal();
        });
    }

    const cancelBtn = overlay.querySelector('.cs-modal-cancel');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', closeModal);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    document.body.addEventListener('click', (e) => {
        // Find closest element with data-confirm
        const el = e.target.closest('[data-confirm]');
        if (!el) return;

        e.preventDefault();
        const message = el.getAttribute('data-confirm');
        const title = el.getAttribute('data-confirm-title') || 'Confirm Action';
        const type = el.getAttribute('data-confirm-type') || 'danger';
        const isFormButton = el.tagName === 'BUTTON' && el.type === 'submit';
        
        showModal({
            title: title,
            message: message,
            type: type,
            onConfirm: () => {
                if (el.tagName === 'A') {
                    window.location.href = el.href;
                } else if (isFormButton && el.form) {
                    // Create a hidden input to simulate button click if it has a name
                    if (el.name) {
                        const hidden = document.createElement('input');
                        hidden.type = 'hidden';
                        hidden.name = el.name;
                        hidden.value = el.value;
                        el.form.appendChild(hidden);
                    }
                    el.form.submit();
                } else if (el.tagName === 'BUTTON' && !el.form) {
                    // It might have an onclick that we can execute?
                    // Usually it's better to stick to standard forms or links
                    const url = el.getAttribute('data-url');
                    if (url) window.location.href = url;
                }
            }
        });
    });
});
