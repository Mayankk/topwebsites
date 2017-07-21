package org.ml.topsites.rest.api;

/**
 * Message class to wrap all the REST text response
 * @author mkurra
 *
 */
public class Message {
	private String message;
	
	public Message(String message){
		this.message = message;
		
	}
	
	public Message(){
		
	}
	
	public String getMessage(){
		return message;
	}
}
