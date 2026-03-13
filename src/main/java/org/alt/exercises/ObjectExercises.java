package org.alt.exercises;

import org.alt.model.Product;
import org.alt.model.Transaction;
import org.alt.model.User;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ObjectExercises {

    // Utilitaire interne : extrait la valeur d'un champ par son nom via réflexion
    static Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalArgumentException("Champ introuvable : " + fieldName, e);
        }
    }

    // 1. Récupérer toutes les valeurs des champs d'un objet via réflexion
    // Retourne { nomChamp -> valeur } pour chaque champ déclaré, quel que soit le type
    public static Map<String, Object> getValues(Object obj) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                result.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Impossible d'accéder au champ : " + field.getName(), e);
            }
        }
        return result;
    }

    // 2. Transformer les valeurs des champs d'un objet
    public static <V, R> Map<String, Object> transformValues(Object obj, Class<V> type, Function<V, R> transformer) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : getValues(obj).entrySet()) {
            if (type.isInstance(entry.getValue())) {
                result.put(entry.getKey(), transformer.apply(type.cast(entry.getValue())));
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    // 3. Fusionner deux objets en sommant les valeurs numériques des champs communs
    public static Map<String, Integer> mergeObjects(Object obj1, Object obj2) {
        Map<String, Object> values1 = getValues(obj1);
        Map<String, Object> values2 = getValues(obj2);
        Map<String, Integer> result = new LinkedHashMap<>();
        Set<String> allKeys = new LinkedHashSet<>();
        allKeys.addAll(values1.keySet());
        allKeys.addAll(values2.keySet());
        for (String key : allKeys) {
            Object raw1 = values1.get(key);
            Object raw2 = values2.get(key);
            if (raw1 instanceof Number || raw2 instanceof Number) {
                int v1 = raw1 instanceof Number n ? n.intValue() : 0;
                int v2 = raw2 instanceof Number n ? n.intValue() : 0;
                result.put(key, v1 + v2);
            }
        }
        return result;
    }

    // 4. Filtrer les champs d'un objet selon leur type et une condition sur les valeurs
    // Le paramètre type garantit un filtrage type-safe sur des objets aux champs hétérogènes
    public static <V> Map<String, Object> filterObject(Object obj, Class<V> type, Predicate<V> predicate) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : getValues(obj).entrySet()) {
            if (type.isInstance(entry.getValue())) {
                V value = type.cast(entry.getValue());
                if (predicate.test(value)) {
                    result.put(entry.getKey(), value);
                }
            } else {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    // 5. Convertir une Map plate en Map imbriquée (séparateur ".")
    @SuppressWarnings("unchecked")
    public static Map<String, Object> flatToNested(Map<String, Object> flatMap) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : flatMap.entrySet()) {
            String[] keys = entry.getKey().split("\\.");
            Map<String, Object> current = result;
            for (int i = 0; i < keys.length - 1; i++) {
                current = (Map<String, Object>) current.computeIfAbsent(keys[i], k -> new LinkedHashMap<>());
            }
            current.put(keys[keys.length - 1], entry.getValue());
        }
        return result;
    }

    // 6. Trouver les champs d'un objet ayant une valeur spécifique
    public static List<String> findKeysByValue(Object obj, Object targetValue) {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : getValues(obj).entrySet()) {
            if (Objects.equals(entry.getValue(), targetValue)) {
                keys.add(entry.getKey());
            }
        }
        return keys;
    }

    // 7. Créer une Map à partir de deux tableaux parallèles
    public static <K, V> Map<K, V> createObjectFromArrays(K[] keys, V[] values) {
        Map<K, V> result = new LinkedHashMap<>();
        for (int i = 0; i < Math.min(keys.length, values.length); i++) {
            result.put(keys[i], values[i]);
        }
        return result;
    }

    // 8. Compter les occurrences de chaque valeur dans les champs d'un objet
    public static Map<Object, Integer> countValues(Object obj) {
        Map<Object, Integer> counts = new LinkedHashMap<>();
        for (Object value : getValues(obj).values()) {
            counts.merge(value, 1, Integer::sum);
        }
        return counts;
    }

    // 9. Extraire un sous-ensemble de champs d'un objet via réflexion
    public static Map<String, Object> extractProperties(Object obj, String... fieldNames) {
        Map<String, Object> result = new LinkedHashMap<>();
        for (String fieldName : fieldNames) {
            result.put(fieldName, getFieldValue(obj, fieldName));
        }
        return result;
    }

    // 10. Trier les champs d'un objet par valeur numérique croissante
    public static Map<String, Integer> sortObjectByValue(Object obj) {
        return getValues(obj).entrySet().stream()
                .filter(e -> e.getValue() instanceof Number)
                .sorted(Comparator.comparingInt(e -> ((Number) e.getValue()).intValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ((Number) e.getValue()).intValue(),
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    // 11. Trouver la valeur numérique maximale parmi les champs d'un objet
    public static int findMaxValue(Object obj) {
        return getValues(obj).values().stream()
                .filter(v -> v instanceof Number)
                .mapToInt(v -> ((Number) v).intValue())
                .max()
                .orElseThrow(() -> new NoSuchElementException("L'objet ne contient aucun champ numérique"));
    }

    // 12. Créer une Map à partir d'une liste de paires [clé, valeur]
    public static Map<Object, Object> createObjectFromPairs(List<Object[]> pairs) {
        Map<Object, Object> result = new LinkedHashMap<>();
        for (Object[] pair : pairs) {
            if (pair.length >= 2) {
                result.put(pair[0], pair[1]);
            }
        }
        return result;
    }

    // 13. Rechercher récursivement une valeur dans une Map imbriquée — retourne le chemin de clés
    @SuppressWarnings("unchecked")
    public static List<String> findValueInObject(Map<String, Object> map, Object target) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), target)) {
                List<String> path = new ArrayList<>();
                path.add(entry.getKey());
                return path;
            }
            if (entry.getValue() instanceof Map) {
                List<String> subPath = findValueInObject((Map<String, Object>) entry.getValue(), target);
                if (subPath != null) {
                    List<String> path = new ArrayList<>();
                    path.add(entry.getKey());
                    path.addAll(subPath);
                    return path;
                }
            }
        }
        return null;
    }

    // 14. Grouper une liste d'objets par la valeur d'un champ (via réflexion)
    public static <T> Map<Object, List<T>> groupByProperty(List<T> list, String fieldName) {
        Map<Object, List<T>> result = new LinkedHashMap<>();
        for (T item : list) {
            Object key = getFieldValue(item, fieldName);
            result.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }
        return result;
    }

    // 15. Valider qu'un objet respecte un schéma { nomChamp -> "string"|"number"|"boolean" }
    public static boolean validateObject(Object obj, Map<String, String> schema) {
        Map<String, Object> values = getValues(obj);
        for (Map.Entry<String, String> rule : schema.entrySet()) {
            Object value = values.get(rule.getKey());
            if (value == null) return false;
            boolean typeMatch = switch (rule.getValue()) {
                case "string" -> value instanceof String;
                case "number" -> value instanceof Number;
                case "boolean" -> value instanceof Boolean;
                default -> false;
            };
            if (!typeMatch) return false;
        }
        return true;
    }

    // 16. Comparer deux objets et décrire les différences champ par champ (via réflexion)
    public static Map<String, Map<String, Object>> compareDifferences(Object oldObj, Object newObj) {
        Map<String, Object> oldValues = getValues(oldObj);
        Map<String, Object> newValues = getValues(newObj);
        Map<String, Map<String, Object>> diff = new LinkedHashMap<>();

        Set<String> allKeys = new LinkedHashSet<>();
        allKeys.addAll(oldValues.keySet());
        allKeys.addAll(newValues.keySet());

        for (String key : allKeys) {
            Map<String, Object> entry = new LinkedHashMap<>();
            if (!oldValues.containsKey(key)) {
                entry.put("type", "added");
                entry.put("new", newValues.get(key));
                diff.put(key, entry);
            } else if (!newValues.containsKey(key)) {
                entry.put("type", "removed");
                entry.put("old", oldValues.get(key));
                diff.put(key, entry);
            } else if (!Objects.equals(oldValues.get(key), newValues.get(key))) {
                entry.put("type", "modified");
                entry.put("old", oldValues.get(key));
                entry.put("new", newValues.get(key));
                diff.put(key, entry);
            }
        }
        return diff;
    }

    // 17. Sérialiser un objet en query string URL-encodée (via réflexion)
    public static String objectToUrlParams(Object obj) {
        return getValues(obj).entrySet().stream()
                .map(e -> e.getKey() + "=" +
                        URLEncoder.encode(String.valueOf(e.getValue()), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }

    // 18. Générer un résumé statistique des valeurs numériques des champs d'un objet
    public static Map<String, Map<String, Double>> getObjectStats(Object obj) {
        List<Double> values = getValues(obj).values().stream()
                .filter(v -> v instanceof Number)
                .map(v -> ((Number) v).doubleValue())
                .sorted()
                .toList();
        int n = values.size();

        double total = values.stream().mapToDouble(Double::doubleValue).sum();
        double min = values.getFirst();
        double max = values.getLast();
        double average = total / n;
        double median = (n % 2 == 0)
                ? (values.get(n / 2 - 1) + values.get(n / 2)) / 2.0
                : values.get(n / 2);
        double variance = values.stream().mapToDouble(v -> Math.pow(v - average, 2)).average().orElse(0);
        double stdDev = Math.round(Math.sqrt(variance) * 100.0) / 100.0;

        Map<String, Double> basic = new LinkedHashMap<>();
        basic.put("min", min);
        basic.put("max", max);
        basic.put("average", average);
        basic.put("total", total);

        Map<String, Double> advanced = new LinkedHashMap<>();
        advanced.put("median", median);
        advanced.put("variance", variance);
        advanced.put("standardDeviation", stdDev);

        Map<String, Map<String, Double>> stats = new LinkedHashMap<>();
        stats.put("basic", basic);
        stats.put("advanced", advanced);
        return stats;
    }

    public static void run() {
        System.out.println("\n===== ObjectExercises =====\n");

        User user = new User(1, "Alice", 25, true);
        Product product = new Product(1, "Laptop", "Electronics", 999);
        Transaction t1 = new Transaction(1, "debit", 100, "Food");
        Transaction t2 = new Transaction(2, "credit", 50, "Food");

        System.out.println("1. getValues:\n\t" + getValues(user));
        System.out.println("2. transformValues:\n\t" + transformValues(user, String.class, String::toUpperCase));
        System.out.println("3. mergeObjects:\n\t" + mergeObjects(t1, t2));
        System.out.println("4. filterObject:\n\t" + filterObject(t1, Integer.class, v -> v > 0));
        System.out.println("5. flatToNested:\n\t" + flatToNested(Map.of("app.name", "MyApp", "app.version", "1.0.0", "database.host", "localhost")));
        System.out.println("6. findKeysByValue:\n\t" + findKeysByValue(user, 1));
        System.out.println("7. createFromArrays:\n\t" + createObjectFromArrays(new String[]{"Alice", "Bob"}, new Integer[]{100, 85}));
        System.out.println("8. countValues:\n\t" + countValues(new User(1, "Alice", 1, true)));
        System.out.println("9. extractProperties:\n\t" + extractProperties(user, "name", "age"));
        System.out.println("10. sortObjectByValue:\n\t" + sortObjectByValue(t1));
        System.out.println("11. findMaxValue:\n\t" + findMaxValue(t1));
        System.out.println("12. createFromPairs:\n\t" + createObjectFromPairs(Arrays.asList(new Object[]{"pommes", 2.5}, new Object[]{"bananes", 1.8})));
        System.out.println("13. findValueInObject:\n\t" + findValueInObject(Map.of("app", Map.of("theme", "dark")), "dark"));
        System.out.println("14. groupByProperty:\n\t" + groupByProperty(Arrays.asList(new User(1, "Alice", 25, true), new User(2, "Bob", 30, false), new User(3, "Charlie", 35, true)), "active"));
        System.out.println("15. validateObject:\n\t" + validateObject(user, Map.of("name", "string", "age", "number")));
        System.out.println("16. compareDifferences:\n\t" + compareDifferences(new User(1, "Jean", 30, true), new User(1, "Jean", 31, true)));
        System.out.println("17. objectToUrlParams:\n\t" + objectToUrlParams(user));
        System.out.println("18. getObjectStats:\n\t" + getObjectStats(new Transaction(100, null, 900, null)));
    }
}