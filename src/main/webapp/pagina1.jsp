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

        <%-- Bibliotecas do D3.js --%>
        <script src="https://d3js.org/d3.v5.min.js"></script>
        <%-- <script src="https://cdn.plot.ly/plotly-latest.min.js"></script> --%>
        <%-- <script src="js/d3.v5.min.js"></script> --%>
        <script src="js/plotly-latest.min.js"></script>

        <%-- jQuery --%>
        <script src="js/jquery-3.3.1.min.js"></script>

        <%-- Bibliotecas do React.js --%>
        <script src="https://unpkg.com/react@15/dist/react.js"></script>
        <script src="https://unpkg.com/react-dom@15/dist/react-dom.js"></script>

        <title>S.M.A.R.T. Home 2019® - Home Automation for the Nation</title>

    </head>
    
    <!-- ========================================= -->
    <!-- Exemplo feito SEM TAGS, só com scriplets. -->
    <!-- ========================================= -->
    
    <body>
        <div id="root"></div>

        <br>
        <br>
        <div id=title></div>
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
                    <div class="form-group col-sm-1" style="flex: 0 0 12%;max-width: 12%;" id="groupPeriodo">
                        <select class="custom-select" id="selectPeriodo" name="periodo">
                            <option selected value="z">Período</option>
                            <option value="d">Diário</option>
                            <option value="s">Semanal</option>
                            <option value="m">Mensal</option>
                            <option value="a">Anual</option>
                        </select>
                        </div>
                    <div class="form-group col-sm-3" id="groupDatafinal">
                        <%-- <input type="date" id="start" name="datafinal"
                            value="2019-05-31" class="custom-select"> --%>
                    </div>
                    <div class="form-group col-sm-2"  style="flex: 0 0 13%;max-width: 13% !important;" id="groupTabela">
                        <label class="switch">
                            <input type="checkbox" name="tabela" id="selectTabela">
                            <span class="slider round"></span>
                        </label>
                        <span class="badge badge-primary" style="vertical-align: sub">Gráfico</span>
                    </div>
                    <div class="form-group col-sm-2" align="left" style="flex: 0 0 12%;max-width: 12%;" id="groupLer">
                        <button id="botaoLer" type="button"role="button" class="btn btn-primary">LER</button>
                    </div>
                    <div class="form-group col-sm-2" align="right" style="padding: 0 0 0 30px;" id="groupWs">
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

        <script>
            window.onload = function() {
                class NavbarBrand extends React.Component {
                    render() {
                        return React.createElement('a',
                                                   {href: this.props.href,
                                                    className: "navbar-brand"},
                                                    this.props.texto);
                    }
                }
                class Navbar extends React.Component {
                    render() {
                        return React.createElement('nav',
                                                {className: "navbar navbar-dark sticky-top bg-dark"},
                                                React.createElement(AnchoredButton,
                                                                    {btnClass: "btn-primary",
                                                                     href: "pagina2.jsp",
                                                                     texto: "Cadastrar"}),
                                                React.createElement(NavbarBrand,
                                                                    {href: "/mavenproject2/",
                                                                     texto: "S.M.A.R.T. Home 2019"},
                                                                     React.createElement(Icon, {size: "lg", icon: "home"}),
                                                                     React.createElement(Sup, "®")),
                                                React.createElement(AnchoredButton,
                                                                    {btnClass: "btn-primary",
                                                                     href: "google.com",
                                                                     texto: "Sair"}),);
                    }
                }
                class AnchoredButton extends React.Component {
                    render() {
                        return React.createElement('a',
                                                   {className: "btn " + this.props.btnClass,
                                                    href: this.props.href},
                                                   this.props.texto);
                    }
                }
                class Icon extends React.Component {
                    render() {
                        return React.createElement('i',
                                                   {className: "fas fa-" + this.props.size + " fa-" + this.props.icon});
                    }
                }
                class Sup extends React.Component {
                    render() {
                        return React.createElement('sup');
                    }
                }
                
                ReactDOM.render(
                    React.createElement(Navbar),
                    document.getElementById('root')
                );

                class Title extends React.Component {
                    render() {
                        return React.createElement('h1', {className: "display-1", style: this.props.style}, this.props.texto);
                    }
                }

                ReactDOM.render(
                    React.createElement(Title, {style: {textAlign: "center"}, texto: "Medidas"}),
                    document.getElementById('title')
                );

                class Option extends React.Component {
                    render() {
                        return React.createElement('option',
                                                    {value: this.props.value,
                                                    selected: this.props.selected},
                                                    this.props.texto);
                    }
                }

                class SelectPeriodo extends React.Component {
                    render() {
                        return React.createElement('select',
                                                   {className: "custom-select",
                                                    style: this.props.style,
                                                    id: this.props.id,
                                                    name: this.props.name,
                                                    value: "z"},
                                                    this.props.texto,
                                                    React.createElement(Option, {value: "z", selected: "true", texto: "Período"}),
                                                    React.createElement(Option, {value: "d", selected: false, texto: "Diário"}),
                                                    React.createElement(Option, {value: "s", selected: false, texto: "Semanal"}),
                                                    React.createElement(Option, {value: "m", selected: false, texto: "Mensal"}),
                                                    React.createElement(Option, {value: "a", selected: false, texto: "Anual"}));
                    }
                }

                class Input extends React.Component {
                    render() {
                        return React.createElement('input', {type: this.props.type, id: this.props.id, name: this.props.name, value: this.props.value, className: this.props.className});
                    }
                }
                        
                ReactDOM.render(
                    React.createElement(Input, {type: "date", id: "start", name: "datafinal", defaultValue: "2019-05-31", className: "custom-select"}),
                    document.getElementById('groupDatafinal')
                );
                // ReactDOM.render(
                //     React.createElement(Title, {style: {textAlign: "center"}, texto: "Medidas"}),
                //     document.getElementById('groupTabela')
                // );
                // ReactDOM.render(
                //     React.createElement(Title, {style: {textAlign: "center"}, texto: "Medidas"}),
                //     document.getElementById('groupLer')
                // );
                // ReactDOM.render(
                //     React.createElement(Title, {style: {textAlign: "center"}, texto: "Medidas"}),
                //     document.getElementById('groupWs')
                // );
            };
        </script>

        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </body>
</html>
