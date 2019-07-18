package simulador;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/graficos")
public class graficos extends Thread{

    public static Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        graficos.session = session;
        System.out.println("--- Conectou");
        try{
            graficos.session.getBasicRemote().sendText("=== Conectou!");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    public void zerar(String arg){
    }

    @OnError
    public void onError(Session session,java.lang.Throwable throwable) {
        System.out.println("--- OnError: "+throwable);
    }

    @OnClose
    public void onClose(Session session,CloseReason reason) {
        System.out.println("--- OnClose: "+reason);
    }
    
}
