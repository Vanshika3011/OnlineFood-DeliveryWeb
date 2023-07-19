
<%@ page import="java.util.List" %>
<%@ page import="com.narola.finalproject.model.City" %>
<%@ page import="com.narola.finalproject.dao.LocationDao" %>
<% LocationDao locationDao = new LocationDao();%>
<% String id = request.getParameter("stateId"); %>
<% int stateId = 0; %>
<% if (id != null) {
    stateId = Integer.parseInt(id);
} %>
<% List<City> cities = locationDao.getCities(stateId); %>
<option value="">Select city</option>
<% for (City city : cities) { %>
<option value="<%= city.getCityId() %>"><%= city.getCityName() %>
</option>
<% } %>
