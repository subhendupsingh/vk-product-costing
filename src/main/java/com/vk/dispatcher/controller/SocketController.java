package com.vk.dispatcher.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.vk.dispatcher.model.CartonItem;
import com.vk.dispatcher.service.CartonService;

@Controller
public class SocketController {
	
	@Autowired
	private CartonService cartonService;

/**
     * A simple DTO class to encapsulate messages along with their timestamps.
     */
    public static class MessageDTO {

        public Date date;
        public String content;

        public MessageDTO(String message) {
            this.date = Calendar.getInstance().getTime();
            this.content = message;
        }
    }

    /**
     * Listens the /app/guestbook endpoint and when a message is received, encapsulates it in a MessageDTO instance and relays the resulting object to
     * the clients listening at the /topic/entries endpoint.
     * 
     * @param message the message
     * @return the encapsulated message
     */
    @MessageMapping("/guestbook")
    @SendTo("/topic/entries")
    public MessageDTO guestbook(String message) {
        System.out.println("Received message: " + message);
        try{
        if(!message.isEmpty()){
        	String parts[]=message.split(",");
        	CartonItem item=new CartonItem();
        	item.setAsin(parts[0]);
        	item.setCarton(cartonService.find(Integer.parseInt(parts[1])));
        	cartonService.saveCartonItem(item);
        }
        return new MessageDTO(message);
        }catch(Exception e){
        	e.printStackTrace();
        	return new MessageDTO("failure");
        }
    }
}
