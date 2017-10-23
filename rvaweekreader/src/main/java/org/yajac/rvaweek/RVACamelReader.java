package org.yajac.rvaweek;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import org.jsoup.nodes.Element;
import org.yajac.rvaweek.aws.ScheduledEvent;
import org.yajac.rvaweek.cache.RVACacheWriter;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.web.WebReader;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.Set;

public class RVACamelReader extends WebReader {

	private static String URL = "http://www.thecamel.org";
	private static String LOCATION_NAME = "The Camel";
	private static String SELECT_CLASS = ".list-view-item";
	private static String CATEGORY = "Music";
	private static String ID_NAME = "Camel";
	private static String IMAGE = "https://cdn.ticketfly.com/wp-content/themes/thecamel/images/Camel-Logo-v2.jpg";


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
			if ("PRIVATE EVENT".equalsIgnoreCase(event.getName())) {
				return null;
			}
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}
		return event;
	}

}
