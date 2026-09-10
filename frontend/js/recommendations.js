const savedForecast = sessionStorage.getItem("freightForecast");

if (savedForecast) {
  const forecast = JSON.parse(savedForecast);
  const { request } = forecast;
  document.querySelector(".page-intro").textContent = `For ${request.origin} → ${request.destination} · ${request.cargoType} · ${Number(request.quantityTonnes).toLocaleString("en-IN")} tonnes · ${request.contractMonths}-month contract`;
  document.querySelector(".content-wrap h1").textContent = forecast.compatibility.overall === "SUITABLE"
    ? `Charter a ${request.preferredVessel} in the short term.`
    : "Review the vessel and port fit before chartering.";
  const strategy = document.querySelector(".strategy");
  strategy.querySelector("h2").textContent = forecast.marketEntryWindow;
  strategy.querySelector("p").textContent = forecast.recommendation;
}
