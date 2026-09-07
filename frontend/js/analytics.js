/* ============================================
   Analytics Page Logic
   ============================================ */

document.addEventListener('DOMContentLoaded', function() {
    initializeAnalytics();
});

function initializeAnalytics() {
    // Initialize charts
    initializeRoutePerformanceChart();
    initializeVesselUtilizationChart();
    initializePortCongestionChart();
    initializeFreightIndexChart();
}

function initializeRoutePerformanceChart() {
    const canvasElement = document.getElementById('routePerformanceChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Month 1', 'Month 2', 'Month 3', 'Month 4', 'Month 5', 'Month 6'],
            datasets: [
                {
                    label: 'Australia → Paradip',
                    data: [85450, 86200, 87100, 88300, 87500, 86100],
                    borderColor: '#2563eb',
                    backgroundColor: 'rgba(37, 99, 235, 0.05)',
                    tension: 0.4,
                    fill: true,
                    pointRadius: 4,
                    pointBackgroundColor: '#2563eb'
                },
                {
                    label: 'USA → Visakhapatnam',
                    data: [92300, 93100, 91800, 94200, 92500, 93400],
                    borderColor: '#10b981',
                    backgroundColor: 'rgba(16, 185, 129, 0.05)',
                    tension: 0.4,
                    fill: true,
                    pointRadius: 4,
                    pointBackgroundColor: '#10b981'
                }
            ]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            }
        }
    });
}

function initializeVesselUtilizationChart() {
    const canvasElement = document.getElementById('vesselUtilizationChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Handysize', 'Supramax', 'Panamax', 'Capesize'],
            datasets: [{
                data: [18, 28, 35, 19],
                backgroundColor: [
                    '#3b82f6',
                    '#10b981',
                    '#f59e0b',
                    '#ef4444'
                ]
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    position: 'bottom'
                }
            }
        }
    });
}

function initializePortCongestionChart() {
    const canvasElement = document.getElementById('portCongestionChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');
    const ports = demoData.ports;

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ports.map(p => p.name),
            datasets: [{
                label: 'Congestion Level (%)',
                data: ports.map(p => p.congestion),
                backgroundColor: ports.map(p => {
                    if (p.congestion > 70) return 'rgba(239, 68, 68, 0.7)';
                    if (p.congestion > 60) return 'rgba(245, 158, 11, 0.7)';
                    return 'rgba(16, 185, 129, 0.7)';
                }),
                borderColor: ports.map(p => {
                    if (p.congestion > 70) return '#ef4444';
                    if (p.congestion > 60) return '#f59e0b';
                    return '#10b981';
                }),
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            indexAxis: 'y',
            scales: {
                x: {
                    max: 100
                }
            }
        }
    });
}

function initializeFreightIndexChart() {
    const canvasElement = document.getElementById('freightIndexChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');

    new Chart(ctx, {
        type: 'area',
        data: {
            labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
            datasets: [{
                label: 'Freight Index',
                data: [78, 81, 79, 82, 84, 83, 85, 86, 85, 86, 87, 86],
                borderColor: '#2563eb',
                backgroundColor: 'rgba(37, 99, 235, 0.2)',
                borderWidth: 2,
                fill: true,
                tension: 0.4,
                pointRadius: 4,
                pointBackgroundColor: '#2563eb'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    display: true
                }
            }
        }
    });
}
