<jsp:useBean id="ping" class="w4engineping.Ping"/>
<html>
<p><%= ping.getResult("admin", "admin", 10) %></p>
</html>
