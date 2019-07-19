<%@ page import = "java.io.*, java.util.*, java.sql.*, java.time.*"%>
<%@ page import = "javax.servlet.http.*, javax.servlet.*" %>
<%@ page import = "com.postgresql.jdbc.Driver.*" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%-- CSS do Bootstrap 4 --%>
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <%-- CSS específico do projeto --%>
        <link rel="stylesheet" href="css/style.css">
        <%-- Símbolos utilizados nos botões (FontAwesome)--%>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css" integrity="sha384-oS3vJWv+0UjzBfQzYUhtDYW+Pj2yciDJxpsK1OYPAYjqT085Qq/1cq5FLXAZQ7Ay" crossorigin="anonymous">
        <%-- <link rel="stylesheet" href="css/font-awesome.all.css"> --%>

        <%-- Gráficos D3.js --%>
        <script src="https://d3js.org/d3.v5.min.js"></script>
        <%-- <script src="https://cdn.plot.ly/plotly-latest.min.js"></script> --%>
        <%-- <script src="js/d3.v5.min.js"></script> --%>
        <script src="js/plotly-latest.min.js"></script>

        <%-- jQuery --%>
        <script src="js/jquery-3.3.1.min.js"></script>


        <title>S.M.A.R.T. Home 2019® - Home Automation for the Nation</title>

    </head>
    
    <!-- ========================================= -->
    <!-- Exemplo feito SEM TAGS, só com scriplets. -->
    <!-- ========================================= -->
    
    <body>
        <nav class="navbar navbar-dark sticky-top bg-dark">
            <a class="btn btn-primary" href="pagina2.jsp">Cadastrar</a>
            
            <a class="navbar-brand" href="/mavenproject2/">
                <i class="fas fa-lg fa-home"></i>
                S.M.A.R.T. Home 2019<sup>®</sup>
            </a>
            <a class="btn btn-primary" href="google.com">Sair</a>
        </nav>
        <br>
        <br>
        <h1 class="display-1" style="text-align: center">Medidas</h1>
        <br>
        <br>

        <div class="container">
            <form action="/mavenproject2/requestcontroller" method="get">
                <div class="row">
                    <div class="form-group col-sm-2">
                        <select class="custom-select" id="selectMedida" name="medidor">
                            <option selected value="all">Medidor</option>
                            <%  
                                Class.forName("org.postgresql.Driver");
                                Connection con = DriverManager.getConnection(
                                        "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                                        "tempumidade",                                  //User
                                        "tempumidade"); 
                                Statement stmt = con.createStatement();
                                ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidores;");

                                while(rs.next()){ 
                            %>
                            <option value="<%= rs.getString(3) %>"><%= rs.getString("nome") %></option>
                            <% 
                                }
                                if (stmt != null) { stmt.close(); }
                            %>
                        </select>
                    </div>
                    <div class="form-group col-sm-1" style="flex: 0 0 12%;max-width: 12%;">
                        <select class="custom-select" id="selectPeriodo" name="periodo">
                            <option selected value="z">Período</option>
                            <option value="d">Diário</option>
                            <option value="s">Semanal</option>
                            <option value="m">Mensal</option>
                            <option value="a">Anual</option>
                        </select>
                    </div>
                    <div class="form-group col-sm-3">
                        <input type="date" id="start" name="datafinal"
                            value="2019-05-31" class="custom-select">
                    </div>
                    <div class="form-group col-sm-2"  style="flex: 0 0 13%;max-width: 13% !important;">
                        <label class="switch">
                            <input type="checkbox" name="tabela" id="selectTabela">
                            <span class="slider round"></span>
                        </label>
                        <span class="badge badge-primary" style="vertical-align: sub">Gráfico</span>
                    </div>
                    <div class="form-group col-sm-2" align="left" style="flex: 0 0 12%;max-width: 12%;" >
                        <!-- <button id="botaoLer" type="submit" role="button" class="btn btn-primary">LER</button> -->
                        <button id="botaoLer" type="button"role="button" class="btn btn-primary">LER</button>
                    </div>
                    <div class="form-group col-sm-2" align="right" style="padding: 0 0 0 30px;">
                        <label class="switch">
                            <input type="checkbox" name="ws" id="selectWs">
                            <span class="slider round websocket"></span>
                        </label>
                        <span class="badge badge-success" style="vertical-align: sub">WebSocket</span>
                    </div>
                </div>
            </form>
            </div>
            <div class="row">
                <table id="tabelaMedidas" class="table table-hover" style="margin:0 10% 0 10%;max-width:80%;">
                    <thead> 
                        <tr>
                            <th>Medidor</th>
                            <th>Temperatura</th>                    
                            <th>Umidade</th>
                            <th>Data & Hora</th>
                            <th>Serial</th>
                        </tr>
                    </thead>
                    <%  
                        Class.forName("org.postgresql.Driver");
                        Connection con3 = DriverManager.getConnection(
                                "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                                "tempumidade",                                  //User
                                "tempumidade"); 
                        Statement stmt3 = con3.createStatement();
                        String query3 = "SELECT tabela FROM public.medidores;";
                        ResultSet rs3 = stmt3.executeQuery(query3);
                        ResultSetMetaData rsmd3 = rs3.getMetaData();

                        while (rs3.next()) {
                            for (int idx = 1; idx <= rsmd3.getColumnCount(); idx++) {
                                Statement stmt5 = con3.createStatement();
                                String query5 = "SELECT * FROM public."
                                                + rs3.getString(idx) + ";";
                                ResultSet rs5 = stmt5.executeQuery(query5);
                                ResultSetMetaData rsmd5 = rs5.getMetaData();
                                
                                while (rs5.next()) {
                    %>
                    <tbody>
                        <tr>
                            <td><%= rs5.getString(2) %></td>
                            <td><%= rs5.getString(3) %></td>
                            <td><%= rs5.getString(4) %></td>
                            <td><%= rs5.getString(5) %></td>
                            <td><%= rs5.getString(6) %></td>
                        </tr>
                    </tbody>
                    <% 
                                }
                                if (stmt5 != null) { stmt5.close(); }
                            }
                        }
                        if (stmt3 != null) { stmt3.close(); }
                    %>
                </table>
            </div>
            </div>

            <div id="plotly" style="width: 80%;margin: auto;display:none">

            </div>


            
        </div>

        <%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script> --%>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </body>
</html>
