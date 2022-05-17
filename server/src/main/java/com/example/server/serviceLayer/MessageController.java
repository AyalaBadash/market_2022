package com.example.server.serviceLayer;

import com.example.server.businessLayer.MarketException;
import com.example.server.businessLayer.Publisher;
import com.example.server.serviceLayer.FacadeObjects.OutputMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    //maps from whom I received the message
    @MessageMapping("/notification/{to}")
    public void sendMessage(@DestinationVariable String to, OutputMessage message) throws MarketException {

       boolean isExists= Publisher.getInstance().isExists(to);
       if(isExists){

           simpMessagingTemplate.convertAndSend("/topic/messages/"+ to, message);
       }
    }
}
