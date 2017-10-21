/**
 * 
 */
package org.yajac.rvaweek.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * @author ian.mcewan
 *
 */
public class Event {

	@JsonProperty("name")
	private String name;
	@JsonProperty("location")
	private String location;
	@JsonProperty("locationURL")
	private String locationURL;
	@JsonProperty("url")
	private String url;
	@JsonProperty("image")
	private String image;
	@JsonProperty("date")
	private Date date;
	@JsonProperty("time")
	private String time;
	@JsonProperty("secondaryName")
	private String secondaryName;
	@JsonProperty("price")
	private String price;
	@JsonProperty("category")
	private String category;

	@JsonProperty("description")
	private String description;
	@JsonProperty("shortDescription")
	private String shortDescription;
	@JsonProperty("hasTime")
	private Boolean hasTime;
	@JsonProperty("eventLocation")
	private EventLocation eventLocation;

	@JsonProperty("id")
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSecondaryName() {
		return secondaryName;
	}

	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLocationURL() {
		return locationURL;
	}

	public void setLocationURL(String locationURL) {
		this.locationURL = locationURL;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public Boolean getHasTime() {
		return hasTime;
	}

	public void setHasTime(Boolean hasTime) {
		this.hasTime = hasTime;
	}

	public EventLocation getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(EventLocation eventLocation) {
		this.eventLocation = eventLocation;
	}

}
