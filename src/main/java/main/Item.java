package main;


import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Table
@BatchSize(size = 10)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    private String guid;
    private String title;
    private String description;
    private String pubDate;

    public Item(String guid, String title, String description, String pubDate) {
        this.guid = guid;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
    }

    public Item() {
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Override
    public String toString() {
        return "Item{" +
                "guid='" + guid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
