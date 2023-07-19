<%@ page import="com.narola.finalproject.model.RazorpayPaymentDetails" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Payment Successful</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            background-color: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            font-family: Arial, sans-serif;
        }

        .container {
            width: 700px;
            height: 500px;
            border: 1px solid #ccc;
            padding: 20px;
            background-color: #fff;
            text-align: left;
            font-size: large;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .success-image {
            text-align: center;
            margin-bottom: 20px;
        }

        .details {
            display: flex;
            flex-direction: row;
            margin-bottom: 20px;
            align-items: baseline;

        }

        .details .label {
            width: 150px;
            font-weight: bold;
            margin-left: 70px;
        }

        .details .value {
            font-weight: normal;
            margin-left: 190px;
        }

        .amount-paid {
            font-weight: bold;
        }

        .buttons {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .button {
            margin: 0 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .button:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
<% RazorpayPaymentDetails razorpayPaymentDetails = (RazorpayPaymentDetails) request.getAttribute("razorpayPaymentDetails");%>
<% if(razorpayPaymentDetails.getStatus().equals("failed")){%>
<div class="container">
    <div class="success-image">
        <img style="height: 80%; width:100%" src="/static/MenuItemsImgs/failure.png" alt="Success">
    </div>
    <div class="buttons">
        <a href = "/myOrders"class="button">My Orders</a>
    </div>
</div>
<%}else{%>
<div class="container">

    <div class="success-image">
        <img src="/static/MenuItemsImgs/success.png" alt="Success">
    </div>
    <div class="details">
        <span class="label">Payment Type:</span>
        <span class="value"><%= razorpayPaymentDetails.getPaymentMethod()%></span>
    </div>
    <div class="details">
        <span class="label">Details:</span>
        <span class="value"><%= razorpayPaymentDetails.getPaymentDetails()%></span>
    </div>
    <div class="details">
        <span class="label">Mobile:</span>
        <span class="value"><%= razorpayPaymentDetails.getMobileNo()%></span>
    </div>
    <div class="details">
        <span class="label">Email:</span>
        <span class="value"><%= razorpayPaymentDetails.getEmail()%></span>
    </div>
    <div class="details">
        <span class="label">Amount Paid:</span>
        <span class="value amount-paid"><%= razorpayPaymentDetails.getCurrency() + " " + razorpayPaymentDetails.getAmount()%></span>
    </div>
    <div class="details">
        <span class="label">Transaction ID:</span>
        <span class="value"><%= razorpayPaymentDetails.getPaymentId()%></span>
    </div>

    <div class="buttons">
        <a href = "/myOrders"class="button">My Orders</a>
    </div>
</div>
<%}%>
</body>
</html>

