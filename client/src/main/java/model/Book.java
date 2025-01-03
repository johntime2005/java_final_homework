package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import utils.BooleanDeserializer;

public class Book {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("isborrowed")
    @JsonDeserialize(using = BooleanDeserializer.class)
    private Boolean isborrowed;

    // 构造方法
    public Book() {
    }

    public Book(int id, String title, String author, String publisher, Boolean isborrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isborrowed = isborrowed;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Boolean getIsborrowed() {
        return isborrowed;
    }

    public void setIsborrowed(Boolean isborrowed) {
        this.isborrowed = isborrowed;
    }

    // 为Excel导入导出提供的别名方法
    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public void setPress(String press) {
        this.publisher = press;
    }

    @Override
    public String toString() {
        return "Book{id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", isborrowed=" + isborrowed +
                "}";
    }
}
