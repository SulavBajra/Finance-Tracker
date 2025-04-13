fetch("/myfinancetracker/getCategoryData")
  .then((response) => {
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return response.json();
  })
  .then((data) => {
    console.log("Category Data:", data);
    renderCategoryChart(data);
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
    renderExpenseChart(data);
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
