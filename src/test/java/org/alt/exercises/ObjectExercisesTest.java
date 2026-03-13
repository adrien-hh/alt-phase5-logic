package org.alt.exercises;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;
import org.alt.model.*;
import org.junit.jupiter.api.Test;

class ObjectExercisesTest {

  // 1. getValues
  @Test
  void getValues_shouldReturnAllFieldsOfUser() {
    User user = new User(1, "Alice", 25, true);
    Map<String, Object> values = ObjectExercises.getValues(user);
    assertEquals(1, values.get("id"));
    assertEquals("Alice", values.get("name"));
    assertEquals(25, values.get("age"));
    assertEquals(true, values.get("active"));
  }

  @Test
  void getValues_shouldReturnAllFieldsOfProduct() {
    Product product = new Product(1, "Laptop", "Electronics", 999);
    Map<String, Object> values = ObjectExercises.getValues(product);
    assertEquals("Laptop", values.get("name"));
    assertEquals("Electronics", values.get("category"));
    assertEquals(999, values.get("price"));
  }

  // 2. transformValues
  @Test
  void transformValues_shouldUppercaseOnlyStringFields() {
    User user = new User(1, "Alice", 25, true);
    Map<String, Object> result =
        ObjectExercises.transformValues(user, String.class, String::toUpperCase);
    assertEquals("ALICE", result.get("name"));
    assertEquals(1, result.get("id")); // Integer, conservé intact
    assertEquals(25, result.get("age")); // Integer, conservé intact
    assertEquals(true, result.get("active")); // Boolean, conservé intact
  }

  @Test
  void transformValues_shouldDoubleOnlyIntegerFields() {
    Transaction t = new Transaction(1, "debit", 100, "Food");
    Map<String, Object> result = ObjectExercises.transformValues(t, Integer.class, v -> v * 2);
    assertEquals(2, result.get("id"));
    assertEquals(200, result.get("amount"));
    assertEquals("debit", result.get("type")); // String, conservé intact
    assertEquals("Food", result.get("category")); // String, conservé intact
  }

  // 3. mergeObjects
  @Test
  void mergeObjects_shouldSumNumericFieldsOfTwoTransactions() {
    Transaction t1 = new Transaction(1, "debit", 100, "Food");
    Transaction t2 = new Transaction(2, "debit", 50, "Food");
    Map<String, Integer> result = ObjectExercises.mergeObjects(t1, t2);
    assertEquals(150, result.get("amount"));
  }

  // 4. filterObject
  @Test
  void filterObject_shouldKeepNonMatchingTypesIntact() {
    Transaction t = new Transaction(0, "debit", 500, "Food");
    Map<String, Object> result = ObjectExercises.filterObject(t, Integer.class, v -> v > 100);
    assertTrue(result.containsKey("amount")); // Integer > 100, conservé
    assertFalse(result.containsKey("id")); // Integer = 0, filtré
    assertTrue(result.containsKey("type")); // String, conservé tel quel
    assertTrue(result.containsKey("category")); // String, conservé tel quel
  }

  @Test
  void filterObject_shouldReturnEmptyIfAllNumericFieldsFiltered() {
    Transaction t = new Transaction(0, "debit", 50, "Food");
    Map<String, Object> result = ObjectExercises.filterObject(t, Integer.class, v -> v > 100);
    assertFalse(result.containsKey("id"));
    assertFalse(result.containsKey("amount"));
    assertTrue(result.containsKey("type")); // String intact
  }

  // 5. flatToNested — reste sur Map car la structure "clés avec points" n'existe que dans ce
  // contexte
  @Test
  void flatToNested_shouldNestByDotSeparator() {
    Map<String, Object> flat = new LinkedHashMap<>();
    flat.put("app.name", "MyApp");
    flat.put("app.version", "1.0.0");
    flat.put("database.host", "localhost");

    Map<String, Object> result = ObjectExercises.flatToNested(flat);

    @SuppressWarnings("unchecked")
    Map<String, Object> app = (Map<String, Object>) result.get("app");
    assertEquals("MyApp", app.get("name"));

    @SuppressWarnings("unchecked")
    Map<String, Object> db = (Map<String, Object>) result.get("database");
    assertEquals("localhost", db.get("host"));
  }

  // 6. findKeysByValue
  @Test
  void findKeysByValue_shouldFindFieldsMatchingValue() {
    User user = new User(1, "Alice", 1, true);
    // id et age valent tous les deux 1
    List<String> keys = ObjectExercises.findKeysByValue(user, 1);
    assertTrue(keys.containsAll(List.of("id", "age")));
  }

  @Test
  void findKeysByValue_shouldReturnEmptyIfNotFound() {
    User user = new User(1, "Alice", 25, true);
    assertTrue(ObjectExercises.findKeysByValue(user, 99).isEmpty());
  }

  // 7. createObjectFromArrays — pas de POJO applicable, on garde les tableaux
  @Test
  void createObjectFromArrays_shouldMapKeysToValues() {
    String[] names = {"Alice", "Bob", "Charlie"};
    Integer[] pts = {100, 85, 90};
    Map<String, Integer> result = ObjectExercises.createObjectFromArrays(names, pts);
    assertEquals(100, result.get("Alice"));
    assertEquals(85, result.get("Bob"));
  }

  @Test
  void createObjectFromArrays_shouldStopAtShortestArray() {
    String[] names = {"Alice", "Bob"};
    Integer[] pts = {100};
    assertEquals(1, ObjectExercises.createObjectFromArrays(names, pts).size());
  }

  // 8. countValues
  @Test
  void countValues_shouldCountOccurrencesOfFieldValues() {
    // Un User avec id=1, age=1 → la valeur 1 apparaît deux fois
    User user = new User(1, "Alice", 1, true);
    Map<Object, Integer> result = ObjectExercises.countValues(user);
    assertEquals(2, result.get(1));
    assertEquals(1, result.get("Alice"));
  }

  // 9. extractProperties
  @Test
  void extractProperties_shouldReturnOnlyRequestedFields() {
    User user = new User(1, "Jean", 35, true);
    Map<String, Object> result = ObjectExercises.extractProperties(user, "name", "age");
    assertEquals(2, result.size());
    assertEquals("Jean", result.get("name"));
    assertEquals(35, result.get("age"));
    assertFalse(result.containsKey("active"));
  }

  @Test
  void extractProperties_shouldWorkOnEmployee() {
    Employee emp = new Employee(1, "John", "Doe", 50000);
    Map<String, Object> result = ObjectExercises.extractProperties(emp, "firstName", "salary");
    assertEquals("John", result.get("firstName"));
    assertEquals(50000, result.get("salary"));
  }

  // 10. sortObjectByValue
  @Test
  void sortObjectByValue_shouldSortOnlyNumericFieldsAscending() {
    Transaction t = new Transaction(1, "debit", 500, "Food");
    Map<String, Integer> result = ObjectExercises.sortObjectByValue(t);
    List<Integer> values = result.values().stream().toList();
    assertEquals(1, values.get(0)); // id=1 en premier
    assertEquals(500, values.get(1)); // amount=500 en second
    assertFalse(result.containsKey("type")); // String, ignoré
    assertFalse(result.containsKey("category")); // String, ignoré
  }

  @Test
  void sortObjectByValue_shouldReturnEmptyIfNoNumericFields() {
    Book book = new Book(0, null, null, false);
    // id=0 et available=false — available est boolean donc ignoré, id=0 est Number
    Map<String, Integer> result = ObjectExercises.sortObjectByValue(book);
    assertTrue(result.containsKey("id"));
    assertFalse(result.containsKey("available"));
  }

  // 11. findMaxValue
  @Test
  void findMaxValue_shouldReturnLargestNumericField() {
    Transaction t = new Transaction(1, "debit", 500, "Food");
    assertEquals(500, ObjectExercises.findMaxValue(t));
  }

  @Test
  void findMaxValue_shouldIgnoreNonNumericFields() {
    User user = new User(1, "Alice", 25, true);
    // Seuls id=1 et age=25 sont numériques
    assertEquals(25, ObjectExercises.findMaxValue(user));
  }

  @Test
  void findMaxValue_shouldThrowIfNoNumericField() {
    // On ne peut pas vraiment créer un objet sans champ numérique avec nos POJOs,
    // mais on documente le comportement attendu
    assertThrows(NoSuchElementException.class, () -> ObjectExercises.findMaxValue(new Object()));
  }

  // 12. createObjectFromPairs
  @Test
  void createObjectFromPairs_shouldBuildMapFromPairs() {
    List<Object[]> pairs =
        Arrays.asList(new Object[] {"pommes", 2.5}, new Object[] {"bananes", 1.8});
    Map<Object, Object> result = ObjectExercises.createObjectFromPairs(pairs);
    assertEquals(2.5, result.get("pommes"));
    assertEquals(1.8, result.get("bananes"));
  }

  // 13. findValueInObject — reste sur Map imbriquée par nature
  @Test
  void findValueInObject_shouldReturnPathToValue() {
    Map<String, Object> config = new LinkedHashMap<>();
    Map<String, Object> settings = new LinkedHashMap<>();
    settings.put("theme", "dark");
    Map<String, Object> app = new LinkedHashMap<>();
    app.put("settings", settings);
    config.put("app", app);

    List<String> path = ObjectExercises.findValueInObject(config, "dark");
    assertNotNull(path);
    assertEquals(List.of("app", "settings", "theme"), path);
  }

  @Test
  void findValueInObject_shouldReturnNullIfNotFound() {
    Map<String, Object> config = Map.of("key", "value");
    assertNull(ObjectExercises.findValueInObject(config, "missing"));
  }

  // 14. groupByProperty
  @Test
  void groupByProperty_shouldGroupUsersByActiveField() {
    List<User> users =
        Arrays.asList(
            new User(1, "Alice", 25, true),
            new User(2, "Bob", 30, false),
            new User(3, "Charlie", 35, true));
    Map<Object, List<User>> result = ObjectExercises.groupByProperty(users, "active");
    assertEquals(2, result.get(true).size());
    assertEquals(1, result.get(false).size());
  }

  @Test
  void groupByProperty_shouldGroupProductsByCategory() {
    List<Product> products =
        Arrays.asList(
            new Product(1, "Laptop", "Electronics", 999),
            new Product(2, "Smartphone", "Electronics", 699),
            new Product(3, "T-shirt", "Clothing", 29));
    Map<Object, List<Product>> result = ObjectExercises.groupByProperty(products, "category");
    assertEquals(2, result.get("Electronics").size());
    assertEquals(1, result.get("Clothing").size());
  }

  // 15. validateObject
  @Test
  void validateObject_shouldReturnTrueForValidUser() {
    User user = new User(1, "Marie", 25, true);
    Map<String, String> schema = Map.of("name", "string", "age", "number", "active", "boolean");
    assertTrue(ObjectExercises.validateObject(user, schema));
  }

  @Test
  void validateObject_shouldReturnFalseForMissingField() {
    User user = new User(1, "Marie", 25, true);
    Map<String, String> schema = Map.of("email", "string");
    assertFalse(ObjectExercises.validateObject(user, schema));
  }

  // 16. compareDifferences
  @Test
  void compareDifferences_shouldDetectModifiedField() {
    User oldUser = new User(1, "Jean", 30, true);
    User newUser = new User(1, "Jean", 31, true);
    Map<String, Map<String, Object>> diff = ObjectExercises.compareDifferences(oldUser, newUser);
    assertEquals("modified", diff.get("age").get("type"));
    assertEquals(30, diff.get("age").get("old"));
    assertEquals(31, diff.get("age").get("new"));
  }

  @Test
  void compareDifferences_shouldReturnEmptyIfIdentical() {
    User u1 = new User(1, "Jean", 30, true);
    User u2 = new User(1, "Jean", 30, true);
    assertTrue(ObjectExercises.compareDifferences(u1, u2).isEmpty());
  }

  @Test
  void compareDifferences_shouldDetectModifiedFieldOnEmployee() {
    Employee e1 = new Employee(1, "John", "Doe", 50000);
    Employee e2 = new Employee(1, "John", "Doe", 60000);
    Map<String, Map<String, Object>> diff = ObjectExercises.compareDifferences(e1, e2);
    assertEquals("modified", diff.get("salary").get("type"));
    assertEquals(50000, diff.get("salary").get("old"));
    assertEquals(60000, diff.get("salary").get("new"));
  }

  // 17. objectToUrlParams
  @Test
  void objectToUrlParams_shouldSerializeUserFields() {
    User user = new User(1, "Alice", 25, true);
    String result = ObjectExercises.objectToUrlParams(user);
    assertTrue(result.contains("name=Alice"));
    assertTrue(result.contains("age=25"));
    assertTrue(result.contains("active=true"));
  }

  @Test
  void objectToUrlParams_shouldEncodeSpacesInName() {
    User user = new User(1, "Jean Pierre", 30, false);
    String result = ObjectExercises.objectToUrlParams(user);
    assertTrue(result.contains("name=Jean+Pierre") || result.contains("name=Jean%20Pierre"));
  }

  // 18. getObjectStats
  @Test
  void getObjectStats_shouldComputeStatsOnTransactionNumericFields() {
    Transaction t = new Transaction(900, "debit", 1100, "Food");
    Map<String, Map<String, Double>> stats = ObjectExercises.getObjectStats(t);
    assertEquals(900.0, stats.get("basic").get("min"));
    assertEquals(1100.0, stats.get("basic").get("max"));
    assertEquals(1000.0, stats.get("basic").get("average"));
  }
}
