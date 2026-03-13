package org.alt.model;

public class User {
  private int id;
  private String name;
  private int age;
  private boolean active;

  public User(int id, String name, int age, boolean active) {
    this.id = id;
    this.name = name;
    this.age = age;
    this.active = active;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String toString() {
    return "{id:" + id + ", name:'" + name + "', age:" + age + ", active:" + active + "}";
  }
}
