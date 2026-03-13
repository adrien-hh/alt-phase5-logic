package org.alt.model;

public class Book {
  private int id;
  private String title;
  private String author;
  private boolean available;

  public Book(int id, String title, String author, boolean available) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.available = available;
  }

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

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

  public String toString() {
    return "{id:"
        + id
        + ", title:'"
        + title
        + "', author:'"
        + author
        + "', available:"
        + available
        + "}";
  }

  // Getter générique pour filterByProperty / findIntersection
  public Object getProperty(String property) {
    return switch (property) {
      case "id" -> id;
      case "title" -> title;
      case "author" -> author;
      case "available" -> available;
      default -> null;
    };
  }
}
