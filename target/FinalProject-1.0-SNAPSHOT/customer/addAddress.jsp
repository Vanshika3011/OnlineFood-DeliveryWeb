<%@ page import="com.narola.finalproject.dao.LocationDao" %>
<%@ page import="com.narola.finalproject.model.State" %>
<!DOCTYPE html>
<html>
<head>
  <title>Address Details</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
      background-color: #ffffff;
    }

    h2 {
      margin-bottom: 20px;
      text-align: center;
    }

    .form-container {
      max-width: 500px;
      margin: 0 auto;
      padding: 20px;

      background-color: #F9DFA2;

    }

    .form-group {
      margin-bottom: 20px;
    }

    label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }

    input[type="text"],
    select {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }

    .address-type-other {
      display: none;
    }

    .address-type-other input[type="text"] {
      width: 100%;
    }

    .submit-button {
      background-color: #4caf50;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 16px;
      width: 100%;
    }

    .submit-button:hover {
      background-color: #45a049;
    }

    .error-message {
      color: red;
      margin-top: 5px;
    }
  </style>
  <script>
    function toggleOtherAddressType() {
      var addressType = document.getElementById("address-type").value;
      var otherAddressTypeField = document.querySelector(".address-type-other");

      if (addressType === "other") {
        otherAddressTypeField.style.display = "block";
        otherAddressTypeField.querySelector("input[type='text']").required = true;
      } else {
        otherAddressTypeField.style.display = "none";
        otherAddressTypeField.querySelector("input[type='text']").required = false;
      }
    }

      function updateCities(stateId) {
      var cityDropdown = document.getElementById("city");
      cityDropdown.innerHTML = "";
      var citiesJspUrl = "/cities.jsp?stateId=" + stateId;

      var xhr = new XMLHttpRequest();
      xhr.onreadystatechange = function () {
      if (xhr.readyState === 4 && xhr.status === 200) {
      cityDropdown.innerHTML = xhr.responseText;
    }
    };
      xhr.open("GET", citiesJspUrl, true);
      xhr.send();
    }
  </script>
</head>
<body>
<jsp:include page="customerDashboard.jsp" />
<div class="form-container" >
  <h2>Address Details</h2>


  <form action="/addAddressPage" method="post">

    <p style="color: red">${requestScope.addressTypeError}</p>
    <div class="form-group">
      <label for="address-type">Type</label>
      <select id="address-type" name="address-type" onchange="toggleOtherAddressType()">
        <option value="home">Home</option>
        <option value="work">Work</option>
        <option value="friend">Friend</option>
        <option value="other">Other</option>
      </select>
    </div>

    <div class="form-group address-type-other">
      <label for="other-address-type">Other Type</label>
      <input type="text" id="other-address-type" name="other-address-type">
    </div>

    <p style="color: red">${requestScope.userAddError}</p>
    <div class="form-group">
      <label for="street1">Street 1</label>
      <input type="text" id="street1" name="street1" >
    </div>

    <div class="form-group">
      <label for="street2">Street 2</label>
      <input type="text" id="street2" name="street2">
    </div>

    <p style="color: red">${requestScope.landmarkError}</p>
    <div class="form-group">
      <label for="landmark">Landmark</label>
      <input type="text" id="landmark" name="landmark">
    </div>

    <p style="color: red">${requestScope.stateIdError}</p>
    <div class="form-group">
      <label for="state">State</label>
      <select id="state" name="state" onchange="updateCities(this.value)">
        <option value=" ">Select state</option>
        <% LocationDao locationDao = new LocationDao();
          for (State state : locationDao.getStates()) { %>
        <option value="<%= state.getStateId() %>"><%= state.getStateName() %></option>
        <% } %>
      </select>
    </div>

    <p style="color: red">${requestScope.cityIdError}</p>
    <div class="form-group">
      <label for="city">City</label>
      <select id="city" name="city">
        <jsp:include page="/cities.jsp"/>
      </select>
    </div>

    <p style="color: red">${requestScope.pincodeError}${requestScope.pincodeError1}</p>
    <div class="form-group">
      <label for="pincode">Pincode</label>
      <input type="text" id="pincode" name="pincode" >
    </div>

    <button type="submit">Save Address</button>

  </form>
</div>
</body>
</html>
