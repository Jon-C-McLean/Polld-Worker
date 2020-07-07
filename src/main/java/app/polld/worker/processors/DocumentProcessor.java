package app.polld.worker.processors;

import java.io.IOException;
import java.text.ParseException;

import app.polld.worker.models.Message;

public abstract class DocumentProcessor<T> {
	
	public DocumentProcessor() {}
	
	public abstract T process(Message m) throws IOException, ParseException;
	
}
