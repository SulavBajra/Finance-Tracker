document.addEventListener("DOMContentLoaded", function () {
  const typeSelect = document.getElementById("type");
  const categorySelect = document.getElementById("category");

  const categories = {
    income: [
      "Salary",
      "Freelance",
      "Return on Investments",
      "Received Gifts",
      "Other Income",
    ],
    expense: [
      "Food",
      "Transport",
      "Housing",
      "Entertainment",
      "Healthcare",
      "Education",
      "Shopping",
      "Other Expense",
    ],
  };

  if (typeSelect && categorySelect) {
    typeSelect.addEventListener("change", function () {
      const selectedType = this.value;
      categorySelect.innerHTML = '<option value="">Select Category</option>';

      if (selectedType) {
        categories[selectedType].forEach((category) => {
          const option = document.createElement("option");
          option.value = category;
          option.textContent = category;
          categorySelect.appendChild(option);
        });
      }
    });
  }

  // Form validation
  const form = document.querySelector("form");
  if (form) {
    form.addEventListener("submit", function (e) {
      let valid = true;

      // Validate amount
      const amount = document.getElementById("amount");
      if (!amount.value || parseFloat(amount.value) <= 0) {
        valid = false;
        amount.style.borderColor = "red";
      } else {
        amount.style.borderColor = "";
      }

      // Validate type
      const type = document.getElementById("type");
      if (!type.value) {
        valid = false;
        type.style.borderColor = "red";
      } else {
        type.style.borderColor = "";
      }

      // Validate category
      const category = document.getElementById("category");
      if (!category.value) {
        valid = false;
        category.style.borderColor = "red";
      } else {
        category.style.borderColor = "";
      }

      // Validate date
      const date = document.getElementById("transactionDate");
      if (!date.value) {
        valid = false;
        date.style.borderColor = "red";
      } else {
        date.style.borderColor = "";
      }

      if (!valid) {
        e.preventDefault();
        alert("Please fill all required fields correctly");
      }
    });
  }
});
