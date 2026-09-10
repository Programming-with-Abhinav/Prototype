const chart = document.getElementById("freightChart");
if (chart && window.Chart) {
  new Chart(chart, {
    type: "line",
    data: {
      labels: ["Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      datasets: [
        {
          label: "Historical",
          data: [21800, 22400, 22100, 22900, 23500, 24800, null, null, null],
          borderColor: "#176b87",
          borderWidth: 2.5,
          tension: 0.35,
          pointRadius: 3,
          pointBackgroundColor: "#176b87",
        },
        {
          label: "Forecast",
          data: [null, null, null, null, null, 24800, 25550, 26400, 27150],
          borderColor: "#08a6a6",
          borderWidth: 2.5,
          borderDash: [5, 5],
          tension: 0.35,
          pointRadius: 3,
          pointBackgroundColor: "#08a6a6",
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { display: false },
        tooltip: {
          callbacks: { label: (c) => " ₹ " + c.raw?.toLocaleString("en-IN") },
        },
      },
      scales: {
        x: {
          grid: { display: false },
          ticks: { font: { size: 10 }, color: "#7890a2" },
        },
        y: {
          grid: { color: "#edf2f4" },
          ticks: {
            font: { size: 10 },
            color: "#7890a2",
            callback: (v) => "₹" + v / 1000 + "k",
          },
        },
      },
    },
  });
}
document
  .querySelector(".menu-button")
  ?.addEventListener("click", () =>
    document.querySelector(".sidebar").classList.toggle("open"),
  );
document.getElementById("runDemo")?.addEventListener("click", (e) => {
  e.currentTarget.textContent = "✓ Demo scenario loaded";
  setTimeout(() => (e.currentTarget.textContent = "↻ Run demo"), 1800);
});
