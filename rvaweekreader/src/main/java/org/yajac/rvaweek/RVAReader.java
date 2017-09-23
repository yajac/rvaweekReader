package org.yajac.rvaweek;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

public abstract  class RVAReader {

	private static final String USER_AGENT = "RVA Week";

	public Set<Event> readEventsPage(String url, String selectClass)
			throws ParserConfigurationException, SAXException, IOException, ParseException,
			DatatypeConfigurationException {
		Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
		Set<Event> eventSet = getEvents(doc, selectClass);
		return eventSet;
	}

	protected Set<Event> getEvents(Document doc, String selectClass) {
		Set<Event> eventSet = new HashSet<Event>();
		Elements events = doc.select(selectClass);
		for (Element element : events) {
			Event event = handleEvent(element);
			if (event != null) {
				eventSet.add(event);
			}
		}
		return eventSet;
	}

	protected abstract Event handleEvent(Element element);

}
