package org.yajac.rvaweek;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.StringUtils;

public class RVAReader {

	private static final String USER_AGENT = "RVA Week";

	public void insertEvents(Set<Event> events) {
		for (Event event : events) {
			try {
				createItem(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void createItem(Event event) {
		AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient();
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();

		addAttributeValue(item, "name", event.getName());
		addAttributeValue(item, "secondaryName", event.getSecondaryName());
		addAttributeValue(item, "location", event.getLocation());
		addAttributeValue(item, "image", event.getImage());
		addAttributeValue(item, "url", event.getUrl());
		addAttributeValue(item, "time", event.getTime());
		addAttributeValue(item, "category", event.getCategory());
		addAttributeValue(item, "locationURL", event.getLocationURL());
		addAttributeValue(item, "price", event.getPrice());
		addAttributeValue(item, "date", getDateString(event.getDate()));
		addAttributeValue(item, "eventId", event.getName() + getDateString(event.getDate()));
		dynamoDBClient.putItem("events", item);
	}

	private void addAttributeValue(Map<String, AttributeValue> item, String key, String value) {
		if (StringUtils.hasValue(value)) {
			AttributeValue attributeValue = new AttributeValue(value);
			item.put(key, attributeValue);
		}
	}

	private String getDateString(Date date) {
		SimpleDateFormat parser = new SimpleDateFormat("MM/dd/YYYY");
		return parser.format(date);
	}

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
