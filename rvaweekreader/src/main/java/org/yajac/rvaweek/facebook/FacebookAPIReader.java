package org.yajac.rvaweek.facebook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.yajac.rvaweek.model.Event;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class FacebookAPIReader {


    public Set<Event> readEvents(final String url) {
        Client client = Client.create();
        final String accessToken = FacebookAccessManager.getAccessToken();
        WebResource webResource = client.resource(url).queryParam("fields", "events").queryParam("access_token", accessToken);
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        return getEvents(parseJson(output));
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
        JsonNode eventList = eventData.get("events").get("data");
        int size = eventList.size();
        for (int i = 0; i < eventList.size(); i++) {
            Event event = handleEvent(eventList.get(i));
            if (event != null) {
                eventSet.add(event);
            }
        }
        return eventSet;
    }

    public String getPicture(String eventId) {
        Client client = Client.create();
        final String accessToken = FacebookAccessManager.getAccessToken();
        WebResource webResource = client.resource("https://graph.facebook.com/v2.10").path(eventId).path("picture").queryParam("access_token", accessToken).queryParam("redirect", "false").queryParam("type", "large");
        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String output = response.getEntity(String.class);
        return parseJson(output).get("data").get("url").textValue();
    }

    public Date formatDate(final String date) {
        try {
            ISO8601DateFormat df = new ISO8601DateFormat();
            return df.parse(date);
        } catch (Exception e) {
            throw new RuntimeException("Unable to parse Date", e);
        }
    }

    protected abstract Event handleEvent(JsonNode json);

}
