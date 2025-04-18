function pop(event) {
  event.preventDefault(); // Prevent the form from submitting
  const confirmBox = document.querySelector(".confirm");
  confirmBox.style.display = "flex"; // Show the popup dialog

  return new Promise((resolve) => {
    // Handle the "OK" button click
    document.querySelector(".confirm--button--ok").onclick = function () {
      confirmBox.style.display = "none"; // Hide the popup
      resolve(true); // Resolve the promise with true
    };

    // Handle the "Cancel" button click
    document.querySelector(".confirm--button--cancel").onclick = function () {
      confirmBox.style.display = "none"; // Hide the popup
      resolve(false); // Resolve the promise with false
    };
  }).then((result) => {
    if (result) {
      // If user confirms, submit the form programmatically
      event.target.submit();
    }
  });
}
