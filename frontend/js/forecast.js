document.getElementById("forecastForm")?.addEventListener("submit", async (event) => {
  event.preventDefault();
  const form = event.currentTarget;
  const result = document.getElementById("forecastResult");
  const submit = form.querySelector('button[type="submit"]');
  const formData = new FormData(form);
  const payload = {
    origin: formData.get("origin"), destination: formData.get("destination"), cargoType: formData.get("cargoType"),
    quantityTonnes: Number(formData.get("quantityTonnes")), preferredVessel: formData.get("preferredVessel"),
    contractMonths: Number(formData.get("contractMonths")),
  };
  submit.disabled = true;
  submit.textContent = "Generating forecast...";
  try {
    const forecast = await FreightApi.forecast(payload);
    sessionStorage.setItem("freightForecast", JSON.stringify({ ...forecast, request: payload }));
    result.querySelector(".status").textContent = forecast.compatibility.overall;
    result.querySelector("h2").textContent = `₹ ${forecast.predictedFreight.toLocaleString("en-IN")} expected freight rate`;
    result.querySelector("p").textContent = `${forecast.trend}. ${forecast.recommendation}`;
    result.hidden = false;
    result.scrollIntoView({ behavior: "smooth", block: "center" });
  } catch (error) {
    result.querySelector(".status").textContent = "CONNECTION REQUIRED";
    result.querySelector("h2").textContent = "Forecast could not be generated";
    result.querySelector("p").textContent = `${error.message} Start the backend at http://localhost:8080 and try again.`;
    result.hidden = false;
  } finally {
    submit.disabled = false;
    submit.textContent = "Generate forecast →";
  }
});
