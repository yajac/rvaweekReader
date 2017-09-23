package org.yajac.rvaweek;

import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class RVACamelReader extends RVAReader {

	private static String URL = "http://www.thecamel.org";
	private static String LOCATION_NAME = "The Camel";
	private static String SELECT_CLASS = ".list-view-item";
	private static String CATEGORY = "Music";
	private static String ID_NAME = "Camel";
	private static String IMAGE = "https://cdn.ticketfly.com/wp-content/themes/thecamel/images/Camel-Logo-v2.jpg";


	public static void main(String[] args) {
		RVACamelReader rVAReader = new RVACamelReader();
		try {
			Set<Event> events = rVAReader.readEventsPage(URL, SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public int handle(ScheduledEvent request, Context context) {
		LambdaLogger logger = context.getLogger();
		try {
			Set<Event> events = readEventsPage(URL, SELECT_CLASS);
			RVACacheWriter.insertEvents(events);
			logger.log("Events: " + events.size());
			return events.size();
		} catch (Exception e) {
			logger.log(e.getMessage());
		}
		return 0;
	}

	protected Event handleEvent(Element element) {
		Event event = null;
		try {
			event = new Event();
			event.setUrl(URL + element.getElementsByTag("a").attr("href"));
			event.setImage(element.getElementsByTag("img").attr("src"));
			event.setPrice(element.getElementsByClass("price-range").text());
			event.setTime(element.getElementsByClass("dtstart").text());
			event.setName(element.getElementsByClass("headliners").text());
			event.setSecondaryName(element.getElementsByClass("supports").text());
			String date = element.getElementsByClass("value-title").attr("title");
			event.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(date).toGregorianCalendar().getTime());
			event.setLocation(LOCATION_NAME);
			event.setLocationURL(URL);
			event.setCategory(CATEGORY);
			event.setId(ID_NAME + date);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return event;
	}

}
