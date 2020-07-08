package app.polld.worker;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import app.polld.worker.models.Message;

public class Worker {
	
	private static final String queueUrl = "https://sqs.ap-southeast-2.amazonaws.com/271175096939/Polld-Parse-Dispatch";
	private static final String apiUrl = System.getProperty("API_URL");
	
	private static DocumentProcessor processor = new DocumentProcessor();
	private static MessageHandler handler = new MessageHandler(getSQS(), queueUrl, apiUrl, processor);
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, ParseException {
		boolean continueLooping = true;
		
		Long nextCheck = 0L;
		boolean checkTime = false;
		
		int emptyCounter = 0;
		
		while(continueLooping) {
			if(!checkTime) {
				emptyCounter = 0;
				if(handler.hasMessagesInQueue()) {
					handleMessage();
				}else {
					checkTime = true;
					nextCheck = System.currentTimeMillis() + 60000; // 1 min from now
				}
			}else {
				if(nextCheck <= System.currentTimeMillis()) {
					if(!handler.hasMessagesInQueue()) {
						if(emptyCounter == 25) {
							continueLooping = false;
						}else {
							emptyCounter++;
							nextCheck = System.currentTimeMillis() + 60000;
						}
						
					}else {
						checkTime = false;
						handleMessage();
					}
				}
			}
		}
	}
	
	public static void handleMessage() throws JsonParseException, JsonMappingException, IOException, ParseException {
		List<Message> messages = handler.retrieveMessages();
		
		for(Message m : messages) {
			handler.handleMessage(m);
			// TODO: Deletion of messages
		}
	}
	
	public static AmazonSQS getSQS() {
		return AmazonSQSClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_2).build();
	}
	
}
