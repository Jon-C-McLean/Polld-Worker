package app.polld.worker.processors;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import app.polld.worker.models.BillDetail;
import app.polld.worker.models.BillProgress;
import app.polld.worker.models.Message;

public class BillDocumentProcessor extends DocumentProcessor<BillDetail> {

	@Override
	public BillDetail process(Message m) throws IOException, ParseException {
		Document doc = Jsoup.connect(m.getUrl()).get();
		
		BillDetail detail = new BillDetail();
		
		String permaLink = doc.selectFirst("a.permalink").absUrl("href");
		
		String type = doc.selectFirst("type").text();
		String status = doc.selectFirst("status").text();
		String summary = doc.selectFirst("summary").text();
		
		Element portfolio = doc.selectFirst("portfolio");
		String portfolioValue = "";
		
		if(portfolio != null) {
			portfolioValue = portfolio.text();
			detail.setPortfolio(portfolioValue);
		}else {
			detail.setPortfolio(null);
		}
		
		Element sponsor = doc.selectFirst("sponsor");
		String sponsorValue = "";
		if(sponsor != null) {
			sponsorValue = sponsor.text();
			detail.setSponsor(sponsorValue);
		}else {
			detail.setSponsor(null);
		}
		
		detail.setStatus(status);
		detail.setPermaLink(permaLink);
		detail.setType(type);
		detail.setSummary(summary);
		
		Elements progressItems = doc.select(".bills-progress-item");
		
		for(Element entry : progressItems) {
			Elements children = entry.children();
			if(children.size() >= 2) {
				BillProgress progress = new BillProgress();
				progress.setDesc(children.first().text());
				
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
				String date = children.get(1).text();
				progress.setDate(new java.sql.Date(format.parse(date).getTime()));
				
				progress.setBillId(m.getBillId());
				
				if(children.first().text().equals("Assent")) {
					detail.setAssented(true);
					detail.setAssentDate(new java.sql.Date(format.parse(date).getTime()));
				}
			}
		}
		
		return detail;
	}

	
	
}
