package com.firdavsiimajidzoda.myvideplayer.Models;

/**
 * Created by firdavsiimajidzoda on 4/12/17.
 */

/**
 * Class holds values for item from channel rss
 */
public class FeedItem {
    // Fields
    String title = null;
    String link = null;
    String description = null;
    String pubDate = null;
    String duration = null;
    String thumbnail = null;

    // Setter and getters
    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
