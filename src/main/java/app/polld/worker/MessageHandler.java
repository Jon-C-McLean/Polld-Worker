package app.polld.worker;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import app.polld.worker.models.Message;
import app.polld.worker.models.WorkerJobType;
import app.polld.worker.processors.BillDocumentProcessor;

public class MessageHandler {
	
	private AmazonSQS sqs;
	private String queueUrl;
	private String apiUrl;
	private ObjectMapper mapper;
	
	public MessageHandler(AmazonSQS sqs, String queueUrl, String apiUrl) {
		this.sqs = sqs;
		this.queueUrl = queueUrl;
		this.apiUrl = apiUrl;
		this.mapper = new ObjectMapper();
	}
	
	public boolean hasMessagesInQueue() {
		
		List<String> attributes = new ArrayList<String>();
		
		attributes.add("ApproximateNumberOfMessages");
//		attributes.add("ApproximateNumberOfMessagesDelayed");
		
		Map<String, String> attr = sqs.getQueueAttributes(queueUrl, attributes).getAttributes();
		
		int messages = Integer.parseInt(attr.get("ApproximateNumberOfMessages"));
		
		return messages > 0;
	}
	
	public List<Message> retrieveMessages() throws JsonParseException, JsonMappingException, IOException {
		List<com.amazonaws.services.sqs.model.Message> sqsMessages = sqs.receiveMessage(queueUrl).getMessages();
		
		List<Message> messages = new ArrayList<Message>();
		
		for(com.amazonaws.services.sqs.model.Message m : sqsMessages) {
			Message msg = new Message();
			msg = mapper.readValue(m.getBody(), Message.class);
			messages.add(msg);
		}
		
		return messages;
	}
	
	public boolean handleMessage(Message message) throws IOException, ParseException {
		WorkerJobType jobType = message.getJobType();
		
		switch(jobType) {
		case BILL_PARSE:
			new BillDocumentProcessor().process(message);
			break;
		case SENATOR_PARSE:
			// Parse Senator Page
			break;
		case OTHER:
			return false;
		}
		
		return true;
	}
	
}
