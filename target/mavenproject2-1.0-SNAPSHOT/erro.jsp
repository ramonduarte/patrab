<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERRO</title>
    </head>
    <body>
    <center>
        <br>
        <br>
        <br>
        <br>
        <h1>ERRO!</h1>
        
        <%=
            "<span style=\"font-size:18pt;\">"+
            (String)request.getAttribute("EXCESSAO_CONTROLLER")+
            "</span>"
        %>
        
    </center>
</body>
</html>
