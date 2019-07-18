package simulador;

import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/CEP")
public class CEP extends Thread{

    private Session session;
    private static int i = 0;
    
    @OnOpen
    public void onOpen(Session session) {
        try {
            this.session = session;
            this.session.getBasicRemote().sendText("INICIOU!");
            this.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnMessage
    public void zerar(String arg){
        if(arg.equals("zerar")){
            i = 0;
        }
    }

    @Override
    public void run(){
        try {
            while (true) {
                Thread.sleep(1000);
                this.session.getBasicRemote().sendText("Mensagem " + i);
                i++;
            }
        } catch (Exception e) {
            System.out.println("--- Exception: "+e);
        }
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
