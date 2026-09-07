/* ============================================
   Dashboard Page Logic
   ============================================ */

document.addEventListener('DOMContentLoaded', function() {
    initializeDashboard();
});

function initializeDashboard() {
    // Update current values
    updateDashboardValues();

    // Initialize charts
    initializeHistoricalChart();
    initializeForecastChart();
    initializeVesselChart();
    initializeRiskChart();
}

function updateDashboardValues() {
    // Current freight rate
    const currentRate = 85450;
    if (document.getElementById('currentFreight')) {
        document.getElementById('currentFreight').textContent = formatCurrency(currentRate);
    }

    // Forecast freight rate
    const forecastRate = 88920;
    if (document.getElementById('forecastFreight')) {
        document.getElementById('forecastFreight').textContent = formatCurrency(forecastRate);
    }

    // Recommended vessel
    if (document.getElementById('recommendedVessel')) {
        document.getElementById('recommendedVessel').textContent = 'Panamax';
    }

    // Risk level
    if (document.getElementById('riskLevel')) {
        document.getElementById('riskLevel').textContent = getRiskLevel();
    }

    // Route information
    const route = demoData.currentRoute;
    if (document.getElementById('routeOrigin')) {
        document.getElementById('routeOrigin').textContent = route.origin;
    }
    if (document.getElementById('routeDestination')) {
        document.getElementById('routeDestination').textContent = route.destination;
    }
    if (document.getElementById('cargotype')) {
        document.getElementById('cargotype').textContent = route.cargoType;
    }
    if (document.getElementById('cargoQuantity')) {
        document.getElementById('cargoQuantity').textContent = formatNumber(route.cargoQuantity) + ' tonnes';
    }
    if (document.getElementById('contractDuration')) {
        document.getElementById('contractDuration').textContent = route.contractDuration;
    }
}

function initializeHistoricalChart() {
    const canvasElement = document.getElementById('historicalChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');
    const data = demoData.freightHistory;

    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map(d => d.date),
            datasets: [{
                label: 'Freight Rate (₹)',
                data: data.map(d => d.rate),
                borderColor: chartColors.primary,
                backgroundColor: 'rgba(37, 99, 235, 0.05)',
                borderWidth: 3,
                fill: true,
                tension: 0.4,
                pointRadius: 5,
                pointBackgroundColor: chartColors.primary,
                pointBorderColor: '#fff',
                pointBorderWidth: 2,
                pointHoverRadius: 7
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            },
            scales: {
                y: {
                    beginAtZero: false,
                    title: {
                        display: true,
                        text: 'Freight Rate (₹)'
                    }
                }
            }
        }
    });

    return chart;
}

function initializeForecastChart() {
    const canvasElement = document.getElementById('forecastChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');
    const data = demoData.forecastData;

    const chart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: data.map(d => d.date),
            datasets: [
                {
                    label: 'Historical Rate',
                    data: data.map(d => d.historical),
                    borderColor: chartColors.primary,
                    backgroundColor: 'rgba(37, 99, 235, 0.05)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointRadius: 4,
                    pointBackgroundColor: chartColors.primary,
                    pointBorderColor: '#fff',
                    pointBorderWidth: 2
                },
                {
                    label: 'Forecast Rate',
                    data: data.map(d => d.forecast),
                    borderColor: chartColors.warning,
                    backgroundColor: 'rgba(245, 158, 11, 0.05)',
                    borderWidth: 2,
                    borderDash: [5, 5],
                    fill: true,
                    tension: 0.4,
                    pointRadius: 4,
                    pointBackgroundColor: chartColors.warning,
                    pointBorderColor: '#fff',
                    pointBorderWidth: 2
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
            },
            scales: {
                y: {
                    beginAtZero: false
                }
            }
        }
    });

    return chart;
}

function initializeVesselChart() {
    const canvasElement = document.getElementById('vesselChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');
    const vessels = demoData.vesselCompatibility;

    // For this cargo (70,000 tonnes), calculate suitability
    const cargoQuantity = 70000;
    const vesselScores = vessels.map(v => {
        let score = 100;
        // Check if capacity is sufficient
        if (v.capacity < cargoQuantity) {
            score = Math.round((v.capacity / cargoQuantity) * 100);
        }
        return score;
    });

    const chart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: vessels.map(v => v.type),
            datasets: [{
                label: 'Suitability Score',
                data: vesselScores,
                backgroundColor: [
                    'rgba(239, 68, 68, 0.7)',    // Handysize - Not suitable
                    'rgba(245, 158, 11, 0.7)',   // Supramax - Warning
                    'rgba(16, 185, 129, 0.7)',   // Panamax - Suitable
                    'rgba(239, 68, 68, 0.7)'     // Capesize - Not suitable
                ],
                borderColor: [
                    '#ef4444',
                    '#f59e0b',
                    '#10b981',
                    '#ef4444'
                ],
                borderWidth: 2
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            indexAxis: 'y',
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            },
            scales: {
                x: {
                    beginAtZero: true,
                    max: 100
                }
            }
        }
    });

    return chart;
}

function initializeRiskChart() {
    const canvasElement = document.getElementById('riskChart');
    if (!canvasElement) return;

    const ctx = canvasElement.getContext('2d');
    const riskFactors = demoData.riskFactors;
    const score = calculateRiskScore();

    const chart = new Chart(ctx, {
        type: 'radar',
        data: {
            labels: [
                'Freight Volatility',
                'Port Congestion',
                'Demand Uncertainty',
                'Vessel Compatibility'
            ],
            datasets: [{
                label: 'Risk Score',
                data: [
                    riskFactors.freightVolatility,
                    riskFactors.portCongestion,
                    riskFactors.demandUncertainty,
                    riskFactors.vesselCompatibility
                ],
                borderColor: chartColors.danger,
                backgroundColor: 'rgba(239, 68, 68, 0.1)',
                borderWidth: 2,
                pointRadius: 5,
                pointBackgroundColor: chartColors.danger
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: true,
            scales: {
                r: {
                    beginAtZero: true,
                    max: 100
                }
            },
            plugins: {
                legend: {
                    display: true,
                    position: 'top'
                }
            }
        }
    });

    return chart;
}
