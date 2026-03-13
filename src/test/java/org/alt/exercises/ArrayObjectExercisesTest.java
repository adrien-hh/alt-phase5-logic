package org.alt.exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import java.util.function.Function;
import org.alt.model.*;
import org.junit.jupiter.api.Test;

class ArrayObjectExercisesTest {

  // 1. filterByProperty
  @Test
  void filterByProperty_shouldReturnOnlyActiveUsers() {
    User[] users = {
      new User(1, "Alice", 25, true),
      new User(2, "Bob", 30, false),
      new User(3, "Charlie", 35, true)
    };
    Object[] result = ArrayObjectExercises.filterByProperty(users, "active", true);
    assertEquals(2, result.length);
    assertTrue(Arrays.stream(result).allMatch(u -> ((User) u).isActive()));
  }

  @Test
  void filterByProperty_shouldWorkOnProducts() {
    Product[] products = {
      new Product(1, "Laptop", "Electronics", 999),
      new Product(2, "T-shirt", "Clothing", 29),
      new Product(3, "Phone", "Electronics", 699)
    };
    Object[] result = ArrayObjectExercises.filterByProperty(products, "category", "Electronics");
    assertEquals(2, result.length);
  }

  @Test
  void filterByProperty_shouldReturnEmptyArrayIfNoMatch() {
    User[] users = {new User(1, "Alice", 25, false)};
    Object[] result = ArrayObjectExercises.filterByProperty(users, "active", true);
    assertEquals(0, result.length);
  }

  // 2. groupBy
  @Test
  void groupBy_shouldGroupProductsByCategory() {
    Product[] products = {
      new Product(1, "Laptop", "Electronics", 999),
      new Product(2, "Smartphone", "Electronics", 699),
      new Product(3, "T-shirt", "Clothing", 29)
    };
    Map<Object, List<Product>> result = ArrayObjectExercises.groupBy(products, "category");
    assertEquals(2, result.get("Electronics").size());
    assertEquals(1, result.get("Clothing").size());
  }

  @Test
  void groupBy_shouldGroupUsersByActiveStatus() {
    User[] users = {
      new User(1, "Alice", 25, true),
      new User(2, "Bob", 30, false),
      new User(3, "Charlie", 35, true)
    };
    Map<Object, List<User>> result = ArrayObjectExercises.groupBy(users, "active");
    assertEquals(2, result.get(true).size());
    assertEquals(1, result.get(false).size());
  }

  // 3. findIntersection
  @Test
  void findIntersection_shouldReturnCommonBooksByTitle() {
    Book[] library1 = {new Book(1, "1984", "Orwell", true), new Book(2, "Dune", "Herbert", false)};
    Book[] library2 = {
      new Book(3, "1984", "Orwell", true), new Book(4, "Foundation", "Asimov", true)
    };
    List<Book> result = ArrayObjectExercises.findIntersection(library1, library2, "title");
    assertEquals(1, result.size());
    assertEquals("1984", result.get(0).getTitle());
  }

  @Test
  void findIntersection_shouldWorkOnEmployeesBySalary() {
    Employee[] team1 = {
      new Employee(1, "John", "Doe", 50000), new Employee(2, "Jane", "Smith", 60000)
    };
    Employee[] team2 = {
      new Employee(3, "Paul", "Martin", 50000), new Employee(4, "Anna", "Brown", 70000)
    };
    List<Employee> result = ArrayObjectExercises.findIntersection(team1, team2, "salary");
    assertEquals(1, result.size());
    assertEquals(50000, result.get(0).getSalary());
  }

  @Test
  void findIntersection_shouldReturnEmptyListIfNoCommon() {
    Book[] library1 = {new Book(1, "Dune", "Herbert", false)};
    Book[] library2 = {new Book(2, "Foundation", "Asimov", true)};
    List<Book> result = ArrayObjectExercises.findIntersection(library1, library2, "title");
    assertTrue(result.isEmpty());
  }

  // 4. transformArray
  @Test
  void transformArray_shouldBuildFullNameAndAnnualSalary() {
    Employee[] employees = {
      new Employee(1, "John", "Doe", 50000), new Employee(2, "Jane", "Smith", 60000)
    };
    Function<Employee, Map<String, Object>> transformer =
        emp -> {
          Map<String, Object> r = new LinkedHashMap<>();
          r.put("fullName", emp.getFirstName() + " " + emp.getLastName());
          r.put("annualSalary", emp.getSalary() * 12);
          return r;
        };
    List<Map<String, Object>> result = ArrayObjectExercises.transformArray(employees, transformer);
    assertEquals(2, result.size());
    assertEquals("John Doe", result.get(0).get("fullName"));
    assertEquals(600000, result.get(0).get("annualSalary"));
  }

  @Test
  void transformArray_shouldExtractNamesFromUsers() {
    User[] users = {new User(1, "Alice", 25, true), new User(2, "Bob", 30, false)};
    List<String> names = ArrayObjectExercises.transformArray(users, User::getName);
    assertEquals(List.of("Alice", "Bob"), names);
  }

  // 5. aggregateData
  @Test
  void aggregateData_shouldSumAmountsByCategory() {
    Transaction[] transactions = {
      new Transaction(1, "debit", 100, "Food"),
      new Transaction(2, "debit", 50, "Food"),
      new Transaction(3, "credit", 75, "Income")
    };
    Map<Object, Integer> result =
        ArrayObjectExercises.aggregateData(transactions, "category", "amount");
    assertEquals(150, result.get("Food"));
    assertEquals(75, result.get("Income"));
  }

  @Test
  void aggregateData_shouldSumPricesByCategory() {
    Product[] products = {
      new Product(1, "Laptop", "Electronics", 999),
      new Product(2, "Phone", "Electronics", 699),
      new Product(3, "T-shirt", "Clothing", 29)
    };
    Map<Object, Integer> result = ArrayObjectExercises.aggregateData(products, "category", "price");
    assertEquals(1698, result.get("Electronics"));
    assertEquals(29, result.get("Clothing"));
  }
}
