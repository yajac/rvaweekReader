package org.yajac.rvaweek.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.yajac.rvaweek.model.Event;
import org.yajac.rvaweek.model.EventLocation;
import org.yajac.rvaweek.model.Source;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class FacebookAPIReader {

    private static final String LIMIT = "50";

    public Set<Event> readEvents(final String id) {
        final String url = "https://graph.facebook.com/v2.10/" + id + "/events";
        final ClientResponse response = getClientResponse(url);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        return getEvents(parseJson(output));
    }

    private ClientResponse getClientResponse(final String url) {
        final Client client = Client.create();
        final String accessToken = FacebookAccessManager.getAccessToken();
        final WebResource webResource = client.resource(url).queryParam("access_token", accessToken).queryParam("limit", LIMIT);
        return webResource.accept("application/json")
                .get(ClientResponse.class);
    }

    private JsonNode parseJson(final String rawContent) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(rawContent);
        } catch (IOException e) {
            throw new RuntimeException("Invalid JSON");
        }
    }

    protected Set<Event> getEvents(final JsonNode eventData) {
        Set<Event> eventSet = new HashSet<Event>();
        JsonNode eventList = eventData.get("data");
        int size = eventList.size();
        for (int i = 0; i < eventList.size(); i++) {
            Event event = handleEvent(eventList.get(i));
            event = filterEvent(event);
            if (event != null) {
                eventSet.add(event);
            }
        }
        return eventSet;
    }

    protected Event handleEvent(final JsonNode jsonNode) {
        final String eventId = jsonNode.get("id").textValue();
        final String dateString = jsonNode.get("start_time").textValue();
        Event event = getBaseEvent(eventId);
        event.setName(getNodeString(jsonNode, "name"));
        event.setImage(getPicture(eventId));
        event.setDescription(getNodeString(jsonNode, "description"));
        event.setUrl("https://www.facebook.com/events/" + eventId);
        event.setDate(formatDate(dateString));
        event.setEventLocation(getEventLocation(jsonNode.get("place")));
        return event;
    }

    protected EventLocation getEventLocation(JsonNode jsonNode) {
        EventLocation location = new EventLocation();
        if (jsonNode != null) {
            location.setName(jsonNode.get("name").textValue());
            JsonNode details = jsonNode.get("location");
            if (details != null) {
                location.setCity(getNodeString(jsonNode, "city"));
                location.setCountry(getNodeString(jsonNode, "country"));
                location.setLatitude(getNodeDouble(jsonNode, "latitude"));
                location.setLongitude(getNodeDouble(jsonNode, "longitude"));
                location.setState(getNodeString(jsonNode, "state"));
                location.setStreet(getNodeString(jsonNode, "street"));
                location.setZip(getNodeString(jsonNode, "zip"));
            }
        }
        return location;
    }

    private String getNodeString(JsonNode jsonNode, String fieldName) {
        if (jsonNode.get(fieldName) != null && jsonNode.get(fieldName).getNodeType() == JsonNodeType.STRING) {
            return jsonNode.get(fieldName).textValue();
        }
        return null;
    }

    private Double getNodeDouble(JsonNode jsonNode, String fieldName) {
        if (jsonNode.get(fieldName) != null && jsonNode.get(fieldName).getNodeType() == JsonNodeType.NUMBER) {
            return jsonNode.get(fieldName).asDouble();
        }
        return null;
    }

    protected Event getBaseEvent(final String id) {
        Event event = new Event();
        Source source = getSource();
        event.setId(id);
        event.setLocation(source.getName());
        event.setLocationURL(source.getUrl());
        event.setCategory(source.getCategory());
        return event;
    }

    protected abstract Source getSource();


    protected Event filterEvent(Event event) {
        return event;
    }

    public String getPicture(final String eventId) {
        Client client = Client.create();
        final String accessToken = FacebookAccessManager.getAccessToken();
        WebResource webResource = client.resource("https://graph.facebook.com/v2.10").path(eventId).queryParam("access_token", accessToken).queryParam("fields", "cover");
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        return parseJson(output).get("cover").get("source").textValue();
    }

    public Date formatDate(final String date) {
        try {
            ISO8601DateFormat df = new ISO8601DateFormat();
            return df.parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse Date", e);
        }
    }


}
