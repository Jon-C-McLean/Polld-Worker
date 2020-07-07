package app.polld.worker;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Worker {
	
	private static final String queueUrl = "https://sqs.ap-southeast-2.amazonaws.com/271175096939/Polld-Parse-Dispatch";
	
	private static DocumentProcessor processor = new DocumentProcessor();
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, ParseException {
		List<Message> messages = getSQS().receiveMessage(queueUrl).getMessages();
		
		for(Message m : messages) {
			System.out.println("Body: " + m.getBody());
			
			ObjectMapper mapper = new ObjectMapper();
			
			app.polld.worker.models.Message msg = mapper.readValue(m.getBody(), app.polld.worker.models.Message.class);
			
			System.out.println(mapper.writeValueAsString(processor.processParliamentBill(msg)));
		}
	}
	
	public static AmazonSQS getSQS() {
		return AmazonSQSClientBuilder.standard().withRegion(Regions.AP_SOUTHEAST_2).build();
	}
	
}
