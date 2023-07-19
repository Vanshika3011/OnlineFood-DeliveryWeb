<%@ page import="com.narola.finalproject.dao.OrderPaymentDetails" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Payment</title>
</head>
<script type="text/javascript">
    window.onload = function () {
        document.forms["paymentForm"].submit();
    };
</script>

<body>
<% OrderPaymentDetails orderPaymentDetails = (OrderPaymentDetails) request.getAttribute("orderPaymentDetails");%>

<form name="paymentForm" method="POST" action="https://api.razorpay.com/v1/checkout/embedded">
    <input type="hidden" name="key_id" value="<%= request.getServletContext().getInitParameter("key_id")%>"/>
    <input type="hidden" name="amount" value=<%= orderPaymentDetails.getAmount()%>/>
    <input type="hidden" name="order_id" value="<%= orderPaymentDetails.getRazorPayOrderId()%>"/>
    <input type="hidden" name="name" value="Foodies"/>
    <input type="hidden" name="description"
           value="An Indian multinational restaurant aggregator and food delivery company"/>
    <input type="hidden" name="image" value="https://cdn.razorpay.com/logos/BUVwvgaqVByGp2_large.jpg"/>
    <input type="hidden" name="prefill[name]"
           value="<%= orderPaymentDetails.getCustomerFirstName() + " " + orderPaymentDetails.getCustomerLastName()%>"/>
    <input type="hidden" name="prefill[contact]" value="<%= orderPaymentDetails.getContact()%>"/>
    <input type="hidden" name="prefill[email]" value="<%= orderPaymentDetails.getEmail()%>"/>
    <input type="hidden" name="notes[shipping address]"
           value="<%= orderPaymentDetails.getUserAddress().getStreetLine1() + " ," +orderPaymentDetails.getUserAddress().getStreetLine2() + " ," +orderPaymentDetails.getUserAddress().getLandmark() + " " +orderPaymentDetails.getUserAddress().getCity().getCityName() + " " +orderPaymentDetails.getUserAddress().getPincode() + " " +orderPaymentDetails.getUserAddress().getState().getStateName()%>"/>
    <input type="hidden" name="callback_url" value="http://localhost:8080/handle-payment-response"/>
    <input type="hidden" name="cancel_url" value="http://localhost:8080/handle-payment-response"/>
</form>
</body>
</html>
