function validateForm() {
  const username = document.getElementById("username").value.trim();
  const email = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value;
  const repassword = document.getElementById("repassword").value;

  if (username === "" || email === "" || password === "" || repassword === "") {
    alert("All fields are required.");
    return false;
  }

  if (!/^[a-zA-Z0-9_]{3,20}$/.test(username)) {
    alert(
      "Username must be 3-20 characters long and can only contain letters, numbers, and underscores."
    );
    return false;
  }

  if (!/^(?!\d+$)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
    alert("Please enter a valid email address.");
    return false;
  }

  if (password.length < 8) {
    alert("Password must be at least 8 characters long.");
    return false;
  }

  if (password !== repassword) {
    alert("Passwords do not match.");
    return false;
  }

  return true;
}
