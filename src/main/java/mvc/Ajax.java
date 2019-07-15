/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

/**
 *
 * @author ramon
 */
public class Ajax extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */

    private static final long serialVersionUID = 1L;

    public Timestamp subtrai(int dias, Timestamp t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -dias);
        return new Timestamp(calendar.getTime().getTime());
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        HttpServletRequest request2 = request;
        request2.setCharacterEncoding("UTF8");
        response.setCharacterEncoding("UTF8");
        String method = request2.getMethod().toLowerCase();

        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tempumidade", // Database URL
                "tempumidade", // User
                "tempumidade");

        try {
            if ("post".equals(method)) {
                // System.out.println(request2.getParameter("medidores"));
                if ("medidores".equals(request2.getParameter("medidores"))) {
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
                } else if ("sensores".equals(request2.getParameter("medidores"))) {
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
                        writer.endObject();
                    }
                    writer.endArray();
                    response.getOutputStream().flush();
                    writer.close();
                    if (stmt != null) {
                        stmt.close();
                    }
                } else {
                    String medidor = request2.getParameter("medidor");
                    String periodo = request2.getParameter("periodo");
                    String r_datafinal = request2.getParameter("datafinal");
                    System.out.println(medidor + " " + periodo + " " + r_datafinal);
                    if (r_datafinal == null) {
                        r_datafinal = "2019-05-13T16:54:00.0";
                    }
                    if (" ".equals(periodo)) {
                        periodo = "z";
                    }
                    r_datafinal = r_datafinal + "T00:00:00.0";
                    String[] s_datafinal = r_datafinal.split("T");
                    System.out.println(r_datafinal);
                    System.out.println(s_datafinal[0] + " " + s_datafinal[1] + ":00");
                    Timestamp datafinal = Timestamp.valueOf(s_datafinal[0] + " " + s_datafinal[1]);
                    Timestamp datainicial;
                    System.out.println("switch block");

                    switch (periodo) {
                    case "s":
                        datainicial = subtrai(7, datafinal);
                        break;
                    case "m":
                        datainicial = subtrai(30, datafinal);
                        break;
                    case "a":
                        datainicial = subtrai(365, datafinal);
                        break;
                    case "d":
                        datainicial = datafinal;
                        break;
                    default:
                        datainicial = subtrai(36500, datafinal);
                        break;
                    }

                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM public." + medidor + " WHERE datahora BETWEEN '"
                            + datainicial + "00' AND '" + datafinal + "00';");
                    System.out.println("SELECT * FROM public." + medidor + " WHERE datahora BETWEEN '"
                    + datainicial + "00' AND '" + datafinal + "00';");

                    response.setContentType("application/json; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    
                    ResultSetMetaData rsmd = rs.getMetaData();
                    JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                    writer.beginArray();
                    while(rs.next()) {
                        writer.beginObject();
                        for(int idx=1; idx<=rsmd.getColumnCount(); idx++) {
                            writer.name(rsmd.getColumnLabel(idx));
                            writer.value(rs.getString(idx));
                        }
                        System.out.println("next");
                        writer.endObject();
                    }
                    writer.endArray(); 
                    response.getOutputStream().flush();
                    writer.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

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
