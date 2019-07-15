package eel418.servlet_de_leitura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AcessoBD {
    ResultSet rs;
    PreparedStatement stmt;
    Connection con;
    
    public String guardar(
            String medidor,
            String temperatura,
            String umidade,
            String datahora,
            String serial
    ){
        try{
            double d_temperatura = Double.parseDouble(temperatura);
            double d_umidade = Double.parseDouble(umidade);
            
            long l_datahora = 0L;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            l_datahora = df.parse(datahora).getTime();
            Timestamp t_datahora = new Timestamp(l_datahora);
            
            long l_serial = Long.parseLong(serial);

            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                    "tempumidade",                                  //User
                    "tempumidade");                                 //Password
            stmt = con.prepareStatement(
                    "INSERT INTO medidor001 "+
                    "(medidor,temperatura,umidade,datahora,serial) " +
                    " VALUES(?,?,?,?,?);");
            stmt.setString   (1, medidor);
            stmt.setDouble   (2, d_temperatura);
            stmt.setDouble   (3, d_umidade);
            stmt.setTimestamp(4, t_datahora);
            stmt.setLong     (5, l_serial);
            
            stmt.executeUpdate();
            stmt.close();
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
            return "NOK";
        }
        return "OK";
    }
    
    public String puxar(
            String medidor,
            String temperatura,
            String umidade,
            String datahora,
            String serial
    ){
        try{
            double d_temperatura = Double.parseDouble(temperatura);
            double d_umidade = Double.parseDouble(umidade);
            
            long l_datahora = 0L;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            l_datahora = df.parse(datahora).getTime();
            Timestamp t_datahora = new Timestamp(l_datahora);
            
            long l_serial = Long.parseLong(serial);

            con = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tempumidade", //Database URL
                    "tempumidade",                                  //User
                    "tempumidade");                                 //Password
            stmt = con.prepareStatement(
                    "SELECT medidor,temperatura,umidade,datahora,serial "+
                    "FROM medidor001;");
            stmt.setString   (1, medidor);
            stmt.setDouble   (2, d_temperatura);
            stmt.setDouble   (3, d_umidade);
            stmt.setTimestamp(4, t_datahora);
            stmt.setLong     (5, l_serial);
            
            stmt.executeUpdate();
            stmt.close();
            con.close();
            
        }catch(Exception e){
            e.printStackTrace();
            return "NOK";
        }
        return "OK";
    }
}
