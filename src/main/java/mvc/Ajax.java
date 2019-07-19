/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;

import java.io.IOException;
import java.io.OutputStreamWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.google.gson.stream.JsonWriter;
import com.google.gson.*;


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
                    if (r_datafinal == null) {
                        r_datafinal = "2019-05-13T16:54:00.0";
                    }
                    if (" ".equals(periodo)) {
                        periodo = "z";
                    }
                    r_datafinal = r_datafinal + "T00:00:00.0";
                    String[] s_datafinal = r_datafinal.split("T");
                    System.out.println(s_datafinal[0] + " " + s_datafinal[1] + ":00");
                    Timestamp datafinal = Timestamp.valueOf(s_datafinal[0] + " " + s_datafinal[1]);
                    Timestamp datainicial;

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

                    if (medidor.equals("all")) {
                        Statement stmt2 = con.createStatement();
                        String query2 = "SELECT tabela FROM public.medidores;";
                        ResultSet rs2 = stmt2.executeQuery(query2);
                        ResultSetMetaData rsmd2 = rs2.getMetaData();

                        response.setContentType("application/json; charset=UTF-8");
                        response.setCharacterEncoding("UTF-8");
                        JsonWriter writer = new JsonWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
                        writer.beginArray();

                        while (rs2.next()) {
                            for (int idx = 1; idx <= rsmd2.getColumnCount(); idx++) {
                                Statement stmt = con.createStatement();
                                String query =  "SELECT * FROM public."
                                                + rs2.getString(idx)
                                                + " WHERE datahora BETWEEN '"
                                                + datainicial + "00' AND '"
                                                + datafinal + "00';";
                                ResultSet rs = stmt.executeQuery(query);
                                ResultSetMetaData rsmd = rs.getMetaData();
                                
                                while (rs.next()) {
                                    writer.beginObject();
                                    for(int i=1; i<=rsmd.getColumnCount(); ++i) {
                                        writer.name(rsmd.getColumnLabel(i));
                                        writer.value(rs.getString(i));
                                        System.out.println(rsmd.getColumnLabel(i) + " " + rs.getString(i));
                                    }
                                    writer.endObject();
                                }
    
                                if (stmt != null) {
                                    stmt.close();
                                }
                            }
                        }

                        writer.endArray();
                        response.getOutputStream().flush();
                        writer.close();

                        if (stmt2 != null) {
                            stmt2.close();
                        }
                    } else {
                        Statement stmt = con.createStatement();
                        String query = "SELECT * FROM public." + medidor
                                       + " WHERE datahora BETWEEN '" + datainicial
                                       + "00' AND '" + datafinal + "00';";
                        System.out.println(query);
                        ResultSet rs = stmt.executeQuery(query);
    
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
                            writer.endObject();
                        }
                        writer.endArray();
                        response.getOutputStream().flush();
                        writer.close();
                    }
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
            e.printStackTrace();
        } catch (SQLException e) {
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
            e.printStackTrace();
        } catch (SQLException e) {
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
