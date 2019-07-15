<%@ page import = "java.io.*, java.util.*, java.sql.*, java.time.*"%>
<%@ page import = "javax.servlet.http.*, javax.servlet.*" %>
<%@ page import = "com.postgresql.jdbc.Driver.*" %>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

        <title>S.M.A.R.T. Home 2019®</title>
        
        <style>
            table {
                width: 100%; 
                border-collapse: collapse; 
            }
            /* tr:nth-of-type(odd) { background: #eee; } */
            th { 
                background: #333; 
                color: white; 
                font-weight: bold; 
            }
            td, th { 
                padding: 6px; 
                border: 1px solid #ccc; 
                text-align: left; 
            }
            
            @media only screen and (max-width: 760px),
            (min-device-width: 768px) and (max-device-width: 1024px)  {
                table, thead, tbody, th, td, tr { display: block; }

                thead tr { 
                        position: absolute;
                        top: -9999px;
                        left: -9999px;
                }

                tr { border: 1px solid #ccc; }

                td { 
                        border: none;
                        border-bottom: 1px solid #eee; 
                        position: relative;
                        padding-left: 50%; 
                }

                td:before { 
                        position: absolute;
                        top: 6px;
                        left: 6px;
                        width: 45%; 
                        padding-right: 10px; 
                        white-space: nowrap;
                }

                td:nth-of-type(1):before { content: "Serial #"; }
                td:nth-of-type(2):before { content: "Medidor"; }
                td:nth-of-type(3):before { content: "Temperatura"; }
                td:nth-of-type(4):before { content: "Umidade"; }
                td:nth-of-type(5):before { content: "Data & Hora"; }
                td:nth-of-type(6):before { content: "Serial"; }
        }
        </style>
    </head>
    
    <!-- ========================================= -->
    <!-- Exemplo feito SEM TAGS, só com scriplets. -->
    <!-- ========================================= -->
    
    <body>
        <center>
        <br>
        <br>
        <h1 class="display-1">S.M.A.R.T. Home 2019®</h1>
        <br>
        <br>

        <div class="container-fluid">
            <div class="row">
                <form action="changeMedidor" method="post">
                    <div class="form-check form-check-inline">
                        <label class="form-check-label" for="selectMedidor">Medidor</label>
                        <input class="form-check-input" type="select" id="selectMedidor" value="option1">
                    </div>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label" for="selectPeriodo">Período</label>
                        <input class="form-check-input" type="select" id="selectPeriodo" value="option1">
                    </div>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label" for="selectDataFinal">Data Final</label>
                        <input class="form-check-input" type="text" id="selectDataFinal" value="option1">
                    </div>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label" for="selectVisualizacao">Visualização</label>
                        <input class="form-check-input" type="radio" id="selectVisualizacao" value="option1">
                    </div>
                    <div class="form-check form-check-inline">
                        <label class="form-check-label" for="doRead">Ler/Reler</label>
                        <input class="form-check-input" type="button" id="doRead" value="option1">
                    </div>
                </form>


                <table class="table table-hover">
                    <thead> 
                    <tr>
                            <th>Serial #</th>
                            <th>Medidor</th>
                            <th>Temperatura</th>                    
                            <th>Umidade</th>
                            <th>Data & Hora</th>
                            <th>Serial</th>
                        </tr>
                    </thead>
                    <tbody>
                <%  
                    Class.forName("org.postgresql.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                            "tempumidade",                                  //User
                            "tempumidade"); 
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidor001;");

                    while(rs.next()){ 
                %>
                        <tr>
                            <td><%= rs.getInt("serialno") %></td>
                            <td><%= rs.getString("medidor") %></td>
                            <td><%= rs.getInt("temperatura") %></td>
                            <td><%= rs.getInt("umidade") %></td>
                            <td><%= rs.getString("datahora") %></td>
                            <td><%= rs.getString("serial") %></td>
                        </tr>
                <% 
                    }
                    if (stmt != null) { stmt.close(); }
                %>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="container" style="margin-top:20px">
            <div class="row" style="display: block">
                <form method="POST" id="novo_medidor" action="/controller">

                    <h3 class="display-5">Adicionar medidor:</h2>

                    <table class="table table-hover">
                        <thead> 
                        <tr>
                                <th>Medidor</th>
                                <th>Temperatura</th>                    
                                <th>Umidade</th>
                                <th>Data & Hora</th>
                                <th>Serial</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><input type="text" name="medidor"></td>
                                <td><input type="text" name="temperatura"></td>
                                <td><input type="text" name="umidade"></td>
                                <td><input type="text" name="datahora"></td>
                                <td><input type="text" name="serial"></td>
                            </tr>
                        </tbody>
                    </table>
                    <input class="btn btn-outline-primary" type="submit" name="botaoSubmit"
                    value="ENVIAR"/>
                </form>
                    <%
                        PreparedStatement pstatement = null;
                        int updateQuery = 0;

                        String medidor = request.getParameter("medidor");
                        String temperatura = request.getParameter("temperatura");
                        String umidade = request.getParameter("umidade");
                        String datahora = request.getParameter("datahora");
                        String serial = request.getParameter("serial");

                        if (medidor != null && temperatura != null && umidade != null
                        && datahora != null && serial != null) {
                            if (medidor!= "" && temperatura != "" && umidade != ""
                            && datahora != "" && serial != "") {
                                try {
                                    Class.forName("org.postgresql.Driver");
                                    Connection con2 = DriverManager.getConnection(
                                            "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                                            "tempumidade",                                  //User
                                            "tempumidade"); 
                                    Statement stmt2 = con2.createStatement();
                                    String queryString = "INSERT INTO "
                                                        + "medidor001(medidor,temperatura,umidade,datahoraserial)"
                                                        + " values(?,?,?,?,?)";
                                    pstatement = con2.prepareStatement(queryString);
                                    pstatement.setString(1, medidor);
                                    pstatement.setString(2, temperatura);
                                    pstatement.setString(3, umidade);
                                    pstatement.setString(4, datahora);
                                    pstatement.setString(5, serial);
                                    updateQuery = pstatement.executeUpdate();
                                    if (updateQuery != 0) { %>
                                        <br>
                                        <table style="background-color: #E3E4FA;" WIDTH="30%" border="1">
                                            <tr><th>Data is inserted successfully in database.</th></tr>
                                        </table>

                                <%
                                    }
                                } catch (Exception e) {
                                    out.println("Unable to connect to batabase.");
                                } finally {
                                    // close all the connections.
                                    pstatement.close();
                                }
                            }
                        }
                        %>
            </div>
        </div>
        </center>
    </body>
</html>
