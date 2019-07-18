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


@ServerEndpoint(value="/endpoint",
                decoders = MessageDecoder.class, 
                encoders = MessageEncoder.class)
public class WebSocketEndpoint {
    private Session session;
    
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
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        System.out.println(message.toString() + " " + message.getContent()
                           + " from " + message.getFrom());
        try {
            session.getBasicRemote().sendText("text");
        } catch (Exception e) {
            //TODO: handle exception
        }
        broadcast(message);
    }

    @OnError
    public void onError(Session session, java.lang.Throwable throwable) {
        System.out.println("--- OnError: "+throwable);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("--- OnClose: "+reason);
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        // synchronized (endpoint) {
        //     try {
        //         endpoint.session.getBasicRemote().sendObject(message);
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // }
    }

}
