// Reusable browser client for the Spring Boot API. It is ready for future pages.
const FreightApi = {
  baseUrl: localStorage.getItem("freightApiUrl") || "http://localhost:8080/api",
  async request(path, options = {}) {
    const response = await fetch(this.baseUrl + path, {
      headers: {
        "Content-Type": "application/json",
        ...(options.headers || {}),
      },
      ...options,
    });
    if (!response.ok) {
      const body = await response.json().catch(() => ({}));
      throw new Error(body.message || "Service unavailable.");
    }
    return response.json();
  },
  forecast(data) {
    return this.request("/forecast", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },
  weather(port) {
    return this.request("/weather/" + encodeURIComponent(port));
  },
};
