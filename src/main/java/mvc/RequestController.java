package mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.Enumeration;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;


// @WebServlet(name = "RequestController", urlPatterns = { "/mavenproject2/requestcontroller" })
// @Controller
public class RequestController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public Timestamp subtrai(int dias, Timestamp t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -dias);
        return new Timestamp(calendar.getTime().getTime());
    }


    // Este controlador pode chamar vários handlers (tratadores de páginas)
    // Todos os hamdlers implementam a mesma interface.
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {

        request.setCharacterEncoding("UTF8");
        response.setCharacterEncoding("UTF8");
        String method = request.getMethod().toLowerCase();

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tempumidade", // Database URL
                "tempumidade", // User
                "tempumidade");

        try {
            request.setCharacterEncoding("UTF8");
            response.setCharacterEncoding("UTF8");

            String serialno_medidores;
            String nome;
            String tabela;

            if ("delete".equalsIgnoreCase(request.getParameter("operation"))) {
                tabela = request.getParameter("tabela2");

                Statement stmt4 = con.createStatement();
                stmt4.executeUpdate("DELETE FROM public.medidores WHERE tabela = '" + tabela + "';");

                // JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                // writer.beginObject();
                // writer.name("success").value(true);
                // writer.endObject();
                // response.getOutputStream().flush();
                // writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidores;");

                response.setContentType("application/json; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");

                ResultSetMetaData rsmd = rs.getMetaData();
                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginArray();
                while (rs.next()) {
                    writer.beginObject();
                    for (int idx = 1; idx <= rsmd.getColumnCount(); idx++) {
                        writer.name(rsmd.getColumnLabel(idx));
                        writer.value(rs.getString(idx));
                    }
                    System.out.println("next");
                    writer.endObject();
                }
                writer.endArray();
                response.getOutputStream().flush();
                writer.close();
                if (stmt != null) {
                    stmt.close();
                }

            } else if ("enviar".equalsIgnoreCase(request.getParameter("botaoSubmit"))) {
                serialno_medidores = request.getParameter("serialno_medidores0");
                nome = request.getParameter("nome0");
                tabela = request.getParameter("tabela0");

                Statement stmt4 = con.createStatement();
                String sqlQuery = "CREATE TABLE public." + tabela          + " (serialno int NOT NULL,"
                    + "medidor text NOT NULL," + "temperatura text NOT NULL," + "umidade text NOT NULL,"
                    + "datahora timestamp with time zone NOT NULL," + "serial text NOT NULL);"
                    + "CREATE SEQUENCE public." + tabela + "_serialno_seq " + "START WITH 1 INCREMENT BY 1 "
                    + "NO MINVALUE NO MAXVALUE CACHE 1;" + "ALTER SEQUENCE public." + tabela
                    + "_serialno_seq OWNED BY public." + tabela + ".serialno;" + " INSERT INTO public.medidores("
                    + "serialno_medidores,nome,tabela) " + "values(" + serialno_medidores + ",'" + nome + "','" + tabela
                    + "');";
                System.out.println(sqlQuery);
                stmt4.executeUpdate(sqlQuery);

                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginObject();
                writer.name("success");
                writer.value(true);
                writer.endObject();
                response.getOutputStream().flush();
                writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }

            } else if ("edit".equalsIgnoreCase(request.getParameter("operation"))) {
                serialno_medidores = request.getParameter("serialno_medidores1");
                nome = request.getParameter("nome1");
                tabela = request.getParameter("tabela1");

                Statement stmt4 = con.createStatement();
                System.out.println("UPDATE public.medidores SET serialno_medidores = '"
                + serialno_medidores + "', nome = '" + nome
                + "' WHERE tabela = '" + tabela + "';");
                stmt4.executeUpdate("UPDATE public.medidores SET serialno_medidores = '"
                                    + serialno_medidores + "', nome = '" + nome
                                    + "' WHERE tabela = '" + tabela + "';");
                
                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginObject();
                writer.name("success").value(true);
                writer.endObject();
                response.getOutputStream().flush();
                writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }
            }

        } catch (Exception e) {
            request.setCharacterEncoding("UTF8");
            response.setCharacterEncoding("UTF8");

            String serialno_medidores;
            String nome;
            String tabela;

            if ("delete".equalsIgnoreCase(request.getParameter("operation"))) {
                tabela = request.getParameter("tabela2");

                Statement stmt4 = con.createStatement();
                stmt4.executeUpdate("DELETE FROM public.medidores WHERE tabela = '" + tabela + "';");

                // JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                // writer.beginObject();
                // writer.name("success").value(true);
                // writer.endObject();
                // response.getOutputStream().flush();
                // writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidores;");

                response.setContentType("application/json; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");

                ResultSetMetaData rsmd = rs.getMetaData();
                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginArray();
                while (rs.next()) {
                    writer.beginObject();
                    for (int idx = 1; idx <= rsmd.getColumnCount(); idx++) {
                        writer.name(rsmd.getColumnLabel(idx));
                        writer.value(rs.getString(idx));
                    }
                    System.out.println("next");
                    writer.endObject();
                }
                writer.endArray();
                response.getOutputStream().flush();
                writer.close();
                if (stmt != null) {
                    stmt.close();
                }

            } else if ("enviar".equalsIgnoreCase(request.getParameter("botaoSubmit"))) {
                serialno_medidores = request.getParameter("serialno_medidores0");
                nome = request.getParameter("nome0");
                tabela = request.getParameter("tabela0");

                Statement stmt4 = con.createStatement();
                String sqlQuery = "CREATE TABLE public." + tabela          + " (serialno int NOT NULL,"
                    + "medidor text NOT NULL," + "temperatura text NOT NULL," + "umidade text NOT NULL,"
                    + "datahora timestamp with time zone NOT NULL," + "serial text NOT NULL);"
                    + "CREATE SEQUENCE public." + tabela + "_serialno_seq " + "START WITH 1 INCREMENT BY 1 "
                    + "NO MINVALUE NO MAXVALUE CACHE 1;" + "ALTER SEQUENCE public." + tabela
                    + "_serialno_seq OWNED BY public." + tabela + ".serialno;" + " INSERT INTO public.medidores("
                    + "serialno_medidores,nome,tabela) " + "values(" + serialno_medidores + ",'" + nome + "','" + tabela
                    + "');";
                System.out.println(sqlQuery);
                stmt4.executeUpdate(sqlQuery);

                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginObject();
                writer.name("success");
                writer.value(true);
                writer.endObject();
                response.getOutputStream().flush();
                writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }

            } else if ("edit".equalsIgnoreCase(request.getParameter("operation"))) {
                serialno_medidores = request.getParameter("serialno_medidores1");
                nome = request.getParameter("nome1");
                tabela = request.getParameter("tabela1");

                Statement stmt4 = con.createStatement();
                System.out.println("UPDATE public.medidores SET serialno_medidores = '"
                + serialno_medidores + "', nome = '" + nome
                + "' WHERE tabela = '" + tabela + "';");
                stmt4.executeUpdate("UPDATE public.medidores SET serialno_medidores = '"
                                    + serialno_medidores + "', nome = '" + nome
                                    + "' WHERE tabela = '" + tabela + "';");
                
                JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                writer.beginObject();
                writer.name("success").value(true);
                writer.endObject();
                response.getOutputStream().flush();
                writer.close();
                if (stmt4 != null) {
                    stmt4.close();
                }

                
            } 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
} 
