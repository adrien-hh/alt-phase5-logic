package org.alt.exercises;

import org.alt.model.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArrayObjectExercises {

    // Utilitaire interne : extrait la valeur d'un champ par son nom via réflexion
    private static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Champ introuvable : " + fieldName, e);
        }
    }

    // 1. Filtrer un tableau d'objets selon la valeur d'un champ
    // Fonctionne sur n'importe quel type T
    @SuppressWarnings("unchecked")
    public static <T> T[] filterByProperty(T[] array, String fieldName, Object value) {
        return Arrays.stream(array)
                .filter(item -> Objects.equals(getFieldValue(item, fieldName), value))
                .toArray(size -> (T[]) new Object[size]);
    }

    // 2. Grouper un tableau d'objets par la valeur d'un champ
    public static <T> Map<Object, List<T>> groupBy(T[] array, String fieldName) {
        Map<Object, List<T>> result = new LinkedHashMap<>();
        for (T item : array) {
            Object key = getFieldValue(item, fieldName);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return result;
    }

    // 3. Trouver l'intersection entre deux tableaux d'objets selon un champ commun
    public static <T> List<T> findIntersection(T[] array1, T[] array2, String fieldName) {
        Set<Object> valuesInArray2 = Arrays.stream(array2)
                .map(item -> getFieldValue(item, fieldName))
                .collect(Collectors.toSet());

        return Arrays.stream(array1)
                .filter(item -> valuesInArray2.contains(getFieldValue(item, fieldName)))
                .collect(Collectors.toList());
    }

    // 4. Transformer un tableau d'objets via une fonction de mapping
    public static <T, R> List<R> transformArray(T[] array, Function<T, R> transformer) {
        return Arrays.stream(array)
                .map(transformer)
                .collect(Collectors.toList());
    }

    // 5. Agréger (sommer) les valeurs numériques d'un champ, groupées par un autre champ
    public static <T> Map<Object, Integer> aggregateData(T[] array, String groupField, String valueField) {
        Map<Object, Integer> result = new LinkedHashMap<>();
        for (T item : array) {
            Object key   = getFieldValue(item, groupField);
            Object raw   = getFieldValue(item, valueField);
            int value    = (raw instanceof Number) ? ((Number) raw).intValue() : 0;
            result.merge(key, value, Integer::sum);
        }
        return result;
    }

    public static void run() {
        System.out.println("\n===== ArrayObjectExercises =====\n");

        User[] users = {
                new User(1, "Alice", 25, true),
                new User(2, "Bob", 30, false),
                new User(3, "Charlie", 35, true)
        };

        Product[] products = {
                new Product(1, "Laptop", "Electronics", 999),
                new Product(2, "Smartphone", "Electronics", 699),
                new Product(3, "T-shirt", "Clothing", 29)
        };

        Book[] library1 = {
                new Book(1, "1984", "Orwell", true),
                new Book(2, "Dune", "Herbert", false)
        };
        Book[] library2 = {
                new Book(3, "1984", "Orwell", true),
                new Book(4, "Foundation", "Asimov", true)
        };

        Employee[] employees = {
                new Employee(1, "John", "Doe", 50000),
                new Employee(2, "Jane", "Smith", 60000)
        };

        Transaction[] transactions = {
                new Transaction(1, "debit", 100, "Food"),
                new Transaction(2, "debit", 50, "Food"),
                new Transaction(3, "credit", 75, "Income")
        };

        System.out.println("1. filterByProperty:\n\t" + Arrays.toString(filterByProperty(users, "active", true)));
        System.out.println("2. groupBy:\n\t" + groupBy(products, "category"));
        System.out.println("3. findIntersection:\n\t" + findIntersection(library1, library2, "title"));
        System.out.println("4. transformArray:\n\t" + transformArray(employees, e -> e.getFirstName() + " " + e.getLastName()));
        System.out.println("5. aggregateData:\n\t" + aggregateData(transactions, "category", "amount"));
    }
}