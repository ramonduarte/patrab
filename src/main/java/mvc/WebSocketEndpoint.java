package mvc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ramon
 */

import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.EncodeException;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
// import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
// import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
// import com.google.gson.GsonBuilder;

import mvc.Message;
import mvc.MessageEncoder;
import mvc.MessageDecoder;


@ServerEndpoint(value="/endpoint",
                decoders = MessageDecoder.class, 
                encoders = MessageEncoder.class)
public class WebSocketEndpoint {
    private Session session;


    public Timestamp subtrai(int dias, Timestamp t) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, -dias);
        return new Timestamp(calendar.getTime().getTime());
    }


    public List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    
        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(rsmd.getColumnName(i),rs.getObject(i));
            }
            list.add(row);
        }
    
        return list;
    }

    
    @OnOpen
    public void onOpen(Session session) {
        try {

            Message message = new Message();
            message.setFrom(session.getId());
            message.setContent("content");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException,
    EncodeException, ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tempumidade", // Database URL
                "tempumidade", // User
                "tempumidade");

        try {
            if ("sensores".equals(message.getMedidores())) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidores;");
                List<HashMap<String, Object>> map = convertResultSetToList(rs);

                Gson gson = new Gson();
                session.getBasicRemote().sendText(gson.toJson(map));
                System.out.println(map);
                if (stmt != null) {
                    stmt.close();
                }
            }
            } catch (Exception e) {
                System.out.println(e);
            }
    }

    @OnError
    public void onError(Session session, java.lang.Throwable throwable) {
        System.out.println("--- OnError: "+throwable);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("--- OnClose: "+reason);
    }

}
