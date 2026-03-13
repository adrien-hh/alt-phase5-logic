# Test Technique — Exercices de Logique Java

## Structure du projet

```
technical-test/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Main.java
│   │       ├── exercises/
│   │       │   ├── StringExercises.java       # 15 exercices — manipulation de chaînes
│   │       │   ├── ObjectExercises.java        # 18 exercices — manipulation d'objets
│   │       │   └── ArrayObjectExercises.java   # 5 exercices  — tableaux d'objets
│   │       └── model/
│   │           ├── User.java
│   │           ├── Product.java
│   │           ├── Book.java
│   │           ├── Employee.java
│   │           └── Transaction.java
│   └── test/
│       └── java/
│           └── exercises/
│               ├── StringExercisesTest.java
│               ├── ObjectExercisesTest.java
│               └── ArrayObjectExercisesTest.java
├── pom.xml
└── README.md
```

## Prérequis

- Java 17+
- Maven 3.8+ (ou Gradle 8+)

## Installer les dépendances

```
mvn install
# ou
./gradlew build
```

## Lancer les tests

```bash
mvn test
# ou
./gradlew test
```

## Lancer les exemples

```bash
mvn compile exec:java -Dexec.mainClass="Main"
# ou
./gradlew bootRun
```

---

## Exercices résolus

### StringExercises.java — 15 fonctions

| # | Fonction | Description |
|---|----------|-------------|
| 1 | `lengthWithoutSpaces(String)` | Longueur d'une chaîne sans espaces |
| 2 | `greet(String)` | Salutation avec mise en forme du prénom (gère les tirets) |
| 3 | `endsWithExclamation(String)` | Détecte si une chaîne finit par `!` |
| 4 | `reverseWords(String)` | Inverse l'ordre des mots |
| 5 | `countOccurrences(String, char)` | Compte les occurrences d'une lettre (insensible à la casse) |
| 6 | `toCamelCase(String)` | Convertit `snake_case` / `kebab-case` en `camelCase` |
| 7 | `countVowels(String)` | Compte les voyelles (accentuées incluses) |
| 8 | `alternateCase(String)` | Alterne majuscules et minuscules lettre par lettre |
| 9 | `removeDuplicates(String)` | Supprime les caractères consécutifs identiques |
| 10 | `extractInitials(String)` | Extrait les initiales d'un nom complet |
| 11 | `maskString(String, int)` | Masque tous les caractères sauf les N derniers |
| 12 | `isPalindrome(String)` | Vérifie si une chaîne est un palindrome (ponctuation ignorée) |
| 13 | `longestSequence(String)` | Trouve la plus longue séquence de caractères identiques consécutifs |
| 14 | `truncate(String, int)` | Tronque un texte avec `...` à une longueur maximale |
| 15 | `capitalizeWords(String)` | Capitalise la première lettre de chaque mot |

### ObjectExercises.java — 18 fonctions

| # | Fonction | Description |
|---|----------|-------------|
| 1 | `getValues(Object)` | Retourne tous les champs d'un objet sous forme `{ nomChamp → valeur }` |
| 2 | `transformValues(Object, Class<V>, Function)` | Transforme les champs du type donné, conserve les autres intacts |
| 3 | `mergeObjects(Object, Object)` | Fusionne deux objets en sommant leurs champs numériques communs |
| 4 | `filterObject(Object, Class<V>, Predicate)` | Filtre les champs du type donné selon un prédicat, conserve les autres intacts |
| 5 | `flatToNested(Map)` | Convertit une Map plate en Map imbriquée (séparateur `.`) |
| 6 | `findKeysByValue(Object, Object)` | Retourne les noms des champs ayant une valeur donnée |
| 7 | `createObjectFromArrays(K[], V[])` | Crée une Map à partir de deux tableaux parallèles |
| 8 | `countValues(Object)` | Compte les occurrences de chaque valeur parmi les champs |
| 9 | `extractProperties(Object, String...)` | Extrait un sous-ensemble de champs par nom |
| 10 | `sortObjectByValue(Object)` | Trie les champs numériques par valeur croissante |
| 11 | `findMaxValue(Object)` | Retourne la valeur numérique maximale parmi les champs |
| 12 | `createObjectFromPairs(List<Object[]>)` | Crée une Map depuis une liste de paires `[clé, valeur]` |
| 13 | `findValueInObject(Map, Object)` | Recherche récursive d'une valeur dans une Map imbriquée — retourne le chemin de clés |
| 14 | `groupByProperty(List, String)` | Groupe une liste d'objets par la valeur d'un champ |
| 15 | `validateObject(Object, Map)` | Valide qu'un objet respecte un schéma de types |
| 16 | `compareDifferences(Object, Object)` | Compare deux objets et décrit les champs modifiés, ajoutés, supprimés |
| 17 | `objectToUrlParams(Object)` | Sérialise les champs d'un objet en query string URL-encodée |
| 18 | `getObjectStats(Object)` | Calcule min, max, moyenne, médiane, variance et écart-type sur les champs numériques |

### ArrayObjectExercises.java — 5 fonctions

| # | Fonction | Description |
|---|----------|-------------|
| 1 | `filterByProperty(T[], String, Object)` | Filtre un tableau d'objets selon la valeur d'un champ |
| 2 | `groupBy(T[], String)` | Groupe un tableau d'objets par la valeur d'un champ |
| 3 | `findIntersection(T[], T[], String)` | Intersection de deux tableaux selon un champ commun |
| 4 | `transformArray(T[], Function)` | Transforme un tableau via une fonction de mapping |
| 5 | `aggregateData(T[], String, String)` | Somme les valeurs d'un champ numérique, groupées par un autre champ |

---

## Choix techniques

### Réflexion Java comme socle de généricité
`ObjectExercises` et `ArrayObjectExercises` reposent sur `java.lang.reflect.Field` pour accéder aux champs de n'importe quel objet sans couplage à un type métier. La méthode `getValues(Object)` est le point d'entrée unique : elle retourne `Map<String, Object>` et est réutilisée par la majorité des autres méthodes. Cela permet d'appeler `getValues(user)`, `getValues(product)` ou `getValues(transaction)` de façon identique, sans aucun `switch` ou cast conditionnel sur le type.

### Gestion des objets hétérogènes — pattern `Class<V>`
Un objet réel a des champs de types différents (`String`, `int`, `boolean`, etc.). Les méthodes qui opèrent sur un type de valeur spécifique — `transformValues`, `filterObject` — reçoivent un paramètre `Class<V>` qui sert à deux choses : filtrer les champs du bon type via `type.isInstance()`, et caster proprement via `type.cast()` sans `@SuppressWarnings`. Les champs d'un autre type sont systématiquement conservés intacts plutôt qu'ignorés, ce qui reflète le comportement attendu sur un objet réel. Les méthodes purement numériques (`sortObjectByValue`, `findMaxValue`, `getObjectStats`) filtrent silencieusement les champs non-numériques avec `instanceof Number`.

### Ordre d'insertion préservé
`LinkedHashMap` est utilisé systématiquement à la place de `HashMap` pour garantir un ordre de parcours prévisible dans les résultats — important pour la lisibilité des tests et la cohérence avec les exemples de l'énoncé.

### Récursivité dans `findValueInObject`
La recherche dans une Map imbriquée est traitée de façon récursive. Chaque appel descend d'un niveau et remonte le chemin de clés si la valeur est trouvée, permettant une profondeur arbitraire.

### Gestion des cas limites
Les fonctions vérifient les cas dégradés là où c'est pertinent : `maskString` et `longestSequence` gèrent les chaînes vides ou nulles, `findMaxValue` lève une `NoSuchElementException` explicite si l'objet ne contient aucun champ numérique, `getObjectStats` lève une `IllegalArgumentException` dans le même cas.

### Encodage URL
`objectToUrlParams` utilise `URLEncoder.encode` avec `StandardCharsets.UTF_8` — encodage explicite pour éviter le comportement déprécié de la surcharge `String`-based.