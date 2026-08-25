(function () {
    var STATUS_MAP = [
        { match: ['done', 'complete', 'submitted'], key: 'done' },
        { match: ['progress', 'in progress', 'started'], key: 'progress' },
        { match: ['overdue', 'late', 'missed'], key: 'overdue' },
        { match: ['pending', 'not started', 'todo', 'to do'], key: 'pending' }
    ];

    function classify(text) {
        var lower = text.trim().toLowerCase();
        for (var i = 0; i < STATUS_MAP.length; i++) {
            for (var j = 0; j < STATUS_MAP[i].match.length; j++) {
                if (lower.indexOf(STATUS_MAP[i].match[j]) !== -1) {
                    return STATUS_MAP[i].key;
                }
            }
        }
        return null;
    }

    function enhance() {
        var body = document.getElementById('assignment-rows');
        var countEl = document.getElementById('row-count');
        if (!body) return;

        var rows = body.querySelectorAll('tr');
        var dataRowCount = 0;

        rows.forEach(function (row) {
            var cells = row.querySelectorAll('td');
            if (cells.length < 2) return;
            dataRowCount++;

            var statusCell = cells[cells.length - 1];
            if (statusCell.dataset.enhanced === 'true') return;

            var text = statusCell.textContent;
            var key = classify(text);

            if (key) {
                statusCell.setAttribute('data-status', key);
                statusCell.innerHTML = '<span class="status-pill">' + text.trim() + '</span>';
            }
            statusCell.dataset.enhanced = 'true';
        });

        if (countEl) {
            countEl.textContent = dataRowCount > 0
                ? dataRowCount + (dataRowCount === 1 ? ' item' : ' items')
                : '';
        }
    }

    var target = document.getElementById('assignment-rows');
    if (target) {
        var observer = new MutationObserver(enhance);
        observer.observe(target, { childList: true, subtree: true });
    }

    document.addEventListener('DOMContentLoaded', enhance);
    enhance();
})();
