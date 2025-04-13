document.addEventListener("DOMContentLoaded", function () {
  const typeSelect = document.getElementById("type");
  const categorySelect = document.getElementById("category");

  const categories = {
    income: [
      "Salary",
      "Freelance",
      "Return On Investment",
      "Gifts",
      "Other Income",
    ],
    expense: [
      "Food",
      "Transport",
      "Utilities",
      "Entertainment",
      "Other Expenses",
    ],
  };

  if (typeSelect && categorySelect) {
    typeSelect.addEventListener("change", function () {
      const selectedType = this.value;
      categorySelect.innerHTML = '<option value="all">Select Category</option>';
      if (selectedType !== "all") {
        categories[selectedType].forEach(function (category) {
          const option = document.createElement("option");
          option.value = category;
          option.textContent = category;
          categorySelect.appendChild(option);
        });
      }
    });
  }

  const filterform = document.getElementById("filter-form");
  if (filterform) {
    filterform.addEventListener("submit", function (event) {
      event.preventDefault();
      filtertTransactions();
    });
  }

  function filtertTransactions() {
    const type = document.getElementById("type").value;
    const category = document.getElementById("category").value;
    const startDate = document.getElementById("start-date").value;
    const endDate = document.getElementById("end-date").value;

    const rows = document.querySelectorAll(".transactions-list tbody tr");

    rows.forEach((row) => {
      const rowType = row
        .querySelector("td:nth-child(2)")
        .textContent.toLowerCase();
      const rowCategory = row.querySelector("td:nth-child(3)").textContent;
      const rowDate = row.querySelector("td:nth-child(1)").textContent;

      let showRow = true;

      // Filter by type
      if (type !== "all" && rowType !== type) {
        showRow = false;
      }

      // Filter by category
      if (category !== "all" && rowCategory !== category) {
        showRow = false;
      }

      // Filter by date range
      if (startDate && new Date(rowDate) < new Date(startDate)) {
        showRow = false;
      }

      if (endDate && new Date(rowDate) > new Date(endDate)) {
        showRow = false;
      }

      row.style.display = showRow ? "" : "none";
    });
  }
});
