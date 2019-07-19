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

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.EncodeException;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.*;

import mvc.Message;
import mvc.MessageEncoder;
import mvc.MessageDecoder;


@ServerEndpoint(value="/endpoint",
                decoders = MessageDecoder.class, 
                encoders = MessageEncoder.class)
public class WebSocketEndpoint {
    private Session session;
    private HashMap<String, Object> lastMessage = new HashMap<String, Object>(2);

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
            message.setContent("");
            lastMessage.put(session.getId().toString(), message);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        onMessage(session, (Message) lastMessage.get(session.getId()));
                    } catch (IOException | ClassNotFoundException | EncodeException | SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            };
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, 0, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException,
    EncodeException, ClassNotFoundException, SQLException {
        lastMessage.put(session.getId().toString(), message);
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tempumidade", // Database URL
                "tempumidade", // User
                "tempumidade");

        try {
            if ("medidores".equals(message.getMedidores())) {
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM public.medidores;");
                List<HashMap<String, Object>> map = convertResultSetToList(rs);

                Gson gson = new Gson();
                session.getBasicRemote().sendText(gson.toJson(map));
                System.out.println(map);
                if (stmt != null) {
                    stmt.close();
                }
            } else if ("others".equals(message.getMedidores())) {
                String medidor = message.getMedidor();
                String periodo = message.getPeriodo();
                String r_datafinal = message.getDatafinal();
                if (r_datafinal == null) {
                    r_datafinal = "2019-07-20";
                }
                if (" ".equals(periodo)) {
                    periodo = "z";
                }
                r_datafinal = r_datafinal + "T23:59:59.0";
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
                    List<HashMap<String, Object>> map = new ArrayList<HashMap<String, Object>>();

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
                                HashMap<String, Object> row = new HashMap<String, Object>(rsmd.getColumnCount());
                                for(int i=1; i<=rsmd.getColumnCount(); ++i) {
                                    row.put(rsmd.getColumnName(i),rs.getObject(i));
                                }
                                map.add(row);
                            }
                            if (stmt != null) {
                                stmt.close();
                            }
                        }
                    }

                    Gson gson = new Gson();
                    session.getBasicRemote().sendText(gson.toJson(map));
                    System.out.println(map);

                    if (stmt2 != null) {
                        stmt2.close();
                    }
                } else {
                    Statement stmt = con.createStatement();
                    String query =  "SELECT * FROM public."
                                    + medidor + " WHERE datahora BETWEEN '"
                                    + datainicial + "00' AND '"
                                    + datafinal + "00';";
                    ResultSet rs = stmt.executeQuery(query);
                    System.out.println(query);
                    List<HashMap<String, Object>> map = convertResultSetToList(rs);

                    Gson gson = new Gson();
                    session.getBasicRemote().sendText(gson.toJson(map));
                    System.out.println(map);
    
                    if (stmt != null) {
                        stmt.close();
                    }
                }


            } else if ("medidas".equals(message.getMedidores())) {


            } else {
                session.getBasicRemote().sendText(message.getContent());
            }
            } catch (Exception e) {
                e.printStackTrace();
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
