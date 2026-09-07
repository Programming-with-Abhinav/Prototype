/* ============================================
   SIH26006 - API Utilities and Demo Data
   ============================================ */

// User Authentication
function logout() {
    sessionStorage.clear();
    window.location.href = 'index.html';
}

// Initialize page
document.addEventListener('DOMContentLoaded', function() {
    // Check authentication
    const user = sessionStorage.getItem('user');
    if (!user && !window.location.href.includes('index.html')) {
        window.location.href = 'index.html';
    }

    // Set user info if exists
    if (user && document.getElementById('username')) {
        document.getElementById('username').textContent = user;
    }

    // Add responsive CSS to all pages
    if (!document.querySelector('link[href*="responsive.css"]')) {
        const link = document.createElement('link');
        link.rel = 'stylesheet';
        link.href = 'css/responsive.css';
        document.head.appendChild(link);
    }
});

// ============================================
// DEMO DATA
// ============================================

const demoData = {
    // Current Route
    currentRoute: {
        origin: 'Australia',
        destination: 'Paradip',
        cargoType: 'Coal',
        cargoQuantity: 70000,
        contractDuration: '3 Months'
    },

    // Freight Rates (Last 12 months)
    freightHistory: [
        { date: 'Jan 2025', rate: 78500 },
        { date: 'Feb 2025', rate: 81200 },
        { date: 'Mar 2025', rate: 79800 },
        { date: 'Apr 2025', rate: 82100 },
        { date: 'May 2025', rate: 84300 },
        { date: 'Jun 2025', rate: 83500 },
        { date: 'Jul 2025', rate: 85100 },
        { date: 'Aug 2025', rate: 86200 },
        { date: 'Sep 2025', rate: 85450 },
        { date: 'Oct 2025', rate: 86800 },
        { date: 'Nov 2025', rate: 87500 },
        { date: 'Dec 2025', rate: 86100 }
    ],

    // Forecast data
    forecastData: [
        { date: 'Dec 5', historical: 86100, forecast: null },
        { date: 'Dec 10', historical: null, forecast: 86500 },
        { date: 'Dec 15', historical: null, forecast: 87200 },
        { date: 'Dec 20', historical: null, forecast: 87800 },
        { date: 'Dec 25', historical: null, forecast: 88500 },
        { date: 'Dec 30', historical: null, forecast: 88920 },
        { date: 'Jan 5', historical: null, forecast: 89200 }
    ],

    // Vessel Compatibility
    vesselCompatibility: [
        { type: 'Handysize', capacity: 35000, draft: 9.2, loa: 190, beam: 28, paradip: 'Suitable', visakhapatnam: 'Suitable' },
        { type: 'Supramax', capacity: 52000, draft: 10.5, loa: 210, beam: 30, paradip: 'Suitable', visakhapatnam: 'Suitable' },
        { type: 'Panamax', capacity: 65000, draft: 11.8, loa: 229, beam: 32, paradip: 'Suitable', visakhapatnam: 'Suitable' },
        { type: 'Capesize', capacity: 170000, draft: 15.5, loa: 290, beam: 45, paradip: 'Not Suitable', visakhapatnam: 'Warning' }
    ],

    // Risk Factors
    riskFactors: {
        freightVolatility: 50,
        portCongestion: 25,
        demandUncertainty: 55,
        vesselCompatibility: 20
    },

    // Ports
    ports: [
        { name: 'Paradip', maxDraft: 12.5, maxLOA: 250, maxBeam: 35, congestion: 65, capacity: 120 },
        { name: 'Visakhapatnam', maxDraft: 13.5, maxLOA: 275, maxBeam: 38, congestion: 72, capacity: 95 },
        { name: 'Gangavaram', maxDraft: 14.0, maxLOA: 280, maxBeam: 40, congestion: 55, capacity: 100 },
        { name: 'Gopalpur', maxDraft: 12.0, maxLOA: 230, maxBeam: 32, congestion: 48, capacity: 50 },
        { name: 'Dhamra', maxDraft: 13.0, maxLOA: 260, maxBeam: 37, congestion: 58, capacity: 80 },
        { name: 'Haldia', maxDraft: 10.5, maxLOA: 210, maxBeam: 30, congestion: 70, capacity: 45 }
    ]
};

// ============================================
// CHART CONFIGURATION
// ============================================

const chartColors = {
    primary: '#2563eb',
    secondary: '#10b981',
    warning: '#f59e0b',
    danger: '#ef4444',
    gray: '#e5e7eb'
};

// ============================================
// UTILITY FUNCTIONS
// ============================================

function formatCurrency(value) {
    return new Intl.NumberFormat('en-IN', {
        style: 'currency',
        currency: 'INR',
        minimumFractionDigits: 0
    }).format(value);
}

function formatNumber(value) {
    return new Intl.NumberFormat('en-IN').format(value);
}

function calculateRiskScore() {
    const factors = demoData.riskFactors;
    const total = (factors.freightVolatility + 
                  factors.portCongestion + 
                  factors.demandUncertainty + 
                  factors.vesselCompatibility) / 4;
    return Math.round(total);
}

function getRiskLevel() {
    const score = calculateRiskScore();
    if (score <= 30) return 'LOW';
    if (score <= 60) return 'MEDIUM';
    return 'HIGH';
}

function getRiskColor(score) {
    if (score <= 30) return chartColors.secondary;
    if (score <= 60) return chartColors.warning;
    return chartColors.danger;
}

// ============================================
// NAVIGATION HELPERS
// ============================================

function goToDashboard() {
    window.location.href = 'dashboard.html';
}

function goToForecast() {
    window.location.href = 'forecast.html';
}

function goToVessels() {
    window.location.href = 'vessels.html';
}

function goToPorts() {
    window.location.href = 'ports.html';
}

function goToRecommendations() {
    window.location.href = 'recommendations.html';
}

function goToAlerts() {
    window.location.href = 'alerts.html';
}

function goToAnalytics() {
    window.location.href = 'analytics.html';
}

function goToAbout() {
    window.location.href = 'about.html';
}

// ============================================
// DEMO SCENARIO
// ============================================

function runDemo() {
    alert('Demo scenario loaded:\n\n' +
          'Route: Australia → Paradip\n' +
          'Cargo: Coal, 70,000 tonnes\n' +
          'Contract: 3 Months\n' +
          'Recommended Vessel: Panamax\n' +
          'Risk Level: Medium\n\n' +
          'View the dashboard to see complete analysis.');
}

// ============================================
// GENERATE FORECAST
// ============================================

function generateNewForecast() {
    document.getElementById('forecastForm').reset();
    document.getElementById('forecastResults').style.display = 'none';
    window.scrollTo(0, 0);
}

function createForecastChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    const chartData = demoData.forecastData;
    const labels = chartData.map(d => d.date);
    const historicalData = chartData.map(d => d.historical);
    const forecastData = chartData.map(d => d.forecast);

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Historical Rate',
                    data: historicalData,
                    borderColor: chartColors.primary,
                    backgroundColor: 'rgba(37, 99, 235, 0.05)',
                    borderWidth: 2,
                    fill: true,
                    tension: 0.4,
                    pointRadius: 4,
                    pointBackgroundColor: chartColors.primary
                },
                {
                    label: 'Forecast Rate',
                    data: forecastData,
                    borderColor: chartColors.warning,
                    backgroundColor: 'rgba(245, 158, 11, 0.05)',
                    borderWidth: 2,
                    borderDash: [5, 5],
                    fill: true,
                    tension: 0.4,
                    pointRadius: 4,
                    pointBackgroundColor: chartColors.warning
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
                    beginAtZero: false,
                    title: {
                        display: true,
                        text: 'Freight Rate (₹)'
                    }
                }
            }
        }
    });
}

function createRiskChart(canvasId) {
    const ctx = document.getElementById(canvasId);
    if (!ctx) return;

    const riskData = demoData.riskFactors;
    const score = calculateRiskScore();

    new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Freight Volatility', 'Port Congestion', 'Demand Uncertainty', 'Vessel Compatibility', 'Safe Margin'],
            datasets: [{
                data: [
                    riskData.freightVolatility,
                    riskData.portCongestion,
                    riskData.demandUncertainty,
                    riskData.vesselCompatibility,
                    100 - score
                ],
                backgroundColor: [
                    chartColors.danger,
                    chartColors.warning,
                    chartColors.warning,
                    chartColors.primary,
                    '#e0e7ff'
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
