fetch("/myfinancetracker/getCategoryData")
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((data) => {
    console.log("Category Data:", data);
    renderCategoryBarGraph(data);
  })
  .catch((error) => {
    console.error("Error fetching category data:", error);
  });

fetch("/myfinancetracker/getExpenseData")
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((data) => {
    console.log("Category Data:", data);
    renderExpenseBarGraph(data);
  })
  .catch((error) => {
    console.error("Error fetching category data:", error);
  });

function renderCategoryChart(data) {
  const ctx = document.getElementById("categoryChart").getContext("2d");
  const categories = data.map((item) => item.category);
  const totals = data.map((item) => item.total);

  new Chart(ctx, {
    type: "pie",
    data: {
      labels: categories,
      datasets: [
        {
          label: "Income by Category",
          data: totals,
          backgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#4BC0C0",
            "#9966FF",
          ], // Adjust colors
        },
      ],
    },
  });
}

function renderExpenseChart(data) {
  const ctx = document.getElementById("categoryExpenseChart").getContext("2d");
  const categories = data.map((item) => item.category);
  const totals = data.map((item) => item.total);

  new Chart(ctx, {
    type: "pie",
    data: {
      labels: categories,
      datasets: [
        {
          label: "Expense by Category",
          data: totals,
          backgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#4BC0C0",
            "#9966FF",
          ], // Adjust colors
        },
      ],
    },
  });
}

function renderCategoryBarGraph(data) {
  const ctx = document.getElementById("categoryBarGraph").getContext("2d");
  const categories = data.map((item) => item.category);
  const totals = data.map((item) => item.total);

  new Chart(ctx, {
    type: "bar",
    data: {
      labels: categories,
      datasets: [
        {
          barPercentage: 0.6, // smaller = thinner bars
          categoryPercentage: 0.7,
          label: "Income by Category",
          data: totals,
          backgroundColor: "#36A2EB",
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    },
  });
}

function renderExpenseBarGraph(data) {
  const ctx = document.getElementById("expenseBarGraph").getContext("2d");
  const categories = data.map((item) => item.category);
  const totals = data.map((item) => item.total);

  new Chart(ctx, {
    type: "bar",
    data: {
      labels: categories,
      datasets: [
        {
          barPercentage: 0.6, // smaller = thinner bars
          categoryPercentage: 0.7,
          label: "Expense by Category",
          data: totals,
          backgroundColor: "#FF6384",
        },
      ],
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      scales: {
        y: {
          beginAtZero: true,
        },
      },
    },
  });
}
