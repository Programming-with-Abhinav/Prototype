/* ============================================
   Forecast Page Logic
   ============================================ */

document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('forecastForm');
    if (form) {
        form.addEventListener('submit', handleForecastSubmit);
    }
});

function handleForecastSubmit(event) {
    event.preventDefault();

    // Get form values
    const origin = document.getElementById('origin').value;
    const destination = document.getElementById('destination').value;
    const cargoType = document.getElementById('cargoType').value;
    const quantity = document.getElementById('quantity').value;
    const vesselType = document.getElementById('vesselType').value;
    const contractDuration = document.getElementById('contractDuration').value;
    const forecastPeriod = document.getElementById('forecastPeriod').value;

    // Validate
    if (!origin || !destination || !cargoType || !quantity || !vesselType || !contractDuration) {
        alert('Please fill in all required fields');
        return;
    }

    // Generate forecast results
    generateForecastResults(origin, destination, cargoType, quantity, vesselType, contractDuration, forecastPeriod);

    // Show results section
    document.getElementById('forecastResults').style.display = 'block';
    window.scrollTo(0, document.getElementById('forecastResults').offsetTop - 100);
}

function generateForecastResults(origin, destination, cargoType, quantity, vesselType, contractDuration, forecastPeriod) {
    // Base freight rate (demo data)
    const baseRate = 85450;
    const currentRate = baseRate;
    
    // Generate forecast based on period
    const daysAhead = parseInt(forecastPeriod);
    const trendFactor = 0.004; // 0.4% daily increase
    const volatility = Math.random() * 2000 - 1000; // Random volatility
    const forecastedRate = Math.round(currentRate * (1 + (trendFactor * daysAhead)) + volatility);

    // Determine trend
    const rateDifference = forecastedRate - currentRate;
    const trend = rateDifference > 0 ? 'Increasing' : rateDifference < 0 ? 'Decreasing' : 'Stable';
    const percentageChange = Math.round((rateDifference / currentRate) * 100 * 10) / 10;

    // Determine market risk
    const riskFactor = Math.abs(percentageChange);
    let marketRisk = 'Low';
    if (riskFactor > 5) marketRisk = 'High';
    else if (riskFactor > 2) marketRisk = 'Medium';

    // Update result cards
    document.getElementById('resultCurrent').textContent = formatCurrency(currentRate);
    document.getElementById('resultForecast').textContent = formatCurrency(forecastedRate);
    document.getElementById('resultTrend').textContent = trend + ' (' + (percentageChange > 0 ? '+' : '') + percentageChange + '%)';
    document.getElementById('resultRisk').textContent = marketRisk;

    // Generate recommendation text
    let recommendationText = '';
    if (trend === 'Increasing') {
        recommendationText = 'Market conditions show ' + trend.toLowerCase() + ' freight rates. ' +
            'Consider entering the market sooner to lock in rates before further increases. ';
    } else if (trend === 'Decreasing') {
        recommendationText = 'Freight rates are trending downward. ' +
            'Monitor the market closely before committing to contracts. Consider waiting for stabilization. ';
    } else {
        recommendationText = 'Freight rates are stable. ' +
            'This is a favorable window for chartering decisions with predictable costs. ';
    }

    if (marketRisk === 'Low') {
        recommendationText += 'Risk profile is favorable - proceed with chartering.';
    } else if (marketRisk === 'Medium') {
        recommendationText += 'Moderate risk detected - consider short-term contracts.';
    } else {
        recommendationText += 'High volatility detected - recommend caution and careful timing.';
    }

    document.getElementById('resultRecommendation').textContent = recommendationText;

    // Initialize/update forecast detail chart
    initializeForecastDetailChart();
    initializeRiskAssessmentChart();
}

function initializeForecastDetailChart() {
    const canvasElement = document.getElementById('forecastDetailChart');
    if (!canvasElement) return;

    // Destroy existing chart if any
    if (canvasElement.chart) {
        canvasElement.chart.destroy();
    }

    const ctx = canvasElement.getContext('2d');
    const data = demoData.forecastData;

    canvasElement.chart = new Chart(ctx, {
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

function initializeRiskAssessmentChart() {
    const canvasElement = document.getElementById('riskAssessmentChart');
    if (!canvasElement) return;

    // Destroy existing chart if any
    if (canvasElement.chart) {
        canvasElement.chart.destroy();
    }

    const ctx = canvasElement.getContext('2d');
    const riskFactors = demoData.riskFactors;
    const score = calculateRiskScore();

    canvasElement.chart = new Chart(ctx, {
        type: 'doughnut',
        data: {
            labels: ['Freight Volatility', 'Port Congestion', 'Demand Uncertainty', 'Vessel Compatibility', 'Safe Margin'],
            datasets: [{
                data: [
                    riskFactors.freightVolatility,
                    riskFactors.portCongestion,
                    riskFactors.demandUncertainty,
                    riskFactors.vesselCompatibility,
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
