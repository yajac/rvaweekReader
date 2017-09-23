package org.yajac.rvaweek;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

public class RVAReader {

	private static final String USER_AGENT = "RVA Week";

	public Set<Event> readWordpressEvents(String url, String selectClass)
			throws ParserConfigurationException, SAXException, IOException, ParseException,
			DatatypeConfigurationException {
		Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
		Set<Event> eventSet = new HashSet<Event>();
		readEvents(doc, eventSet, selectClass);
		return eventSet;
	}

	protected void readEvents(Document doc, Set<Event> eventSet, String selectClass) {
		Elements events = doc.select(selectClass);
		for (Element element : events) {
			Event event = handleEvent(element);
			if (event != null) {
				eventSet.add(event);
			}
		}
	}

	protected Event handleEvent(Element element) {
		return new Event();
	}

}
