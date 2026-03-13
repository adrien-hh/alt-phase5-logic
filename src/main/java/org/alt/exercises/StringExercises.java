package org.alt.exercises;

import java.util.Arrays;
import java.util.stream.Collectors;

public class StringExercises {

    // 1. Longueur d'une chaîne sans espaces
    // Cas d'usage : Validation de la longueur d'un tweet ou SMS
    public static int lengthWithoutSpaces(String s) {
        return s.replace(" ", "").length();
    }

    // 2. Salutation personnalisée avec première lettre en majuscule
    // Cas d'usage : Système de messagerie automatique ou e-mailing
    public static String greet(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return "Bonjour";
        }

        String capitalized = Arrays.stream(firstName.split("-"))
                .map(part -> Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase())
                .collect(Collectors.joining("-"));

        return "Bonjour " + capitalized;
    }

    // 3. Vérifie si une chaîne se termine par "!"
    // Cas d'usage : Analyse de ton dans un service client
    public static boolean endsWithExclamation(String s) {
        return s != null && s.endsWith("!");
    }

    // 4. Inverser l'ordre des mots dans une chaîne
    // Cas d'usage : Aide à la traduction (ex: français → japonais)
    public static String reverseWords(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            result.append(words[i]);

            if (i > 0) {
                result.append(" ");
            }
        }
        return result.toString();
    }

    // 5. Compter les occurrences d'une lettre dans une chaîne
    // Cas d'usage : Analyse de fréquence pour professeurs de langue
    public static int countOccurrences(String s, char letter) {
        int count = 0;
        for (char c : s.toLowerCase().toCharArray()) {
            if (c == Character.toLowerCase(letter)) {
                count++;
            }
        }
        return count;
    }

    // 6. Convertir une chaîne en camelCase
    // Cas d'usage : Migration de noms de colonnes SQL vers propriétés JavaScript
    public static String toCamelCase(String s) {
        String[] parts = s.split("[_\\- ]+");
        StringBuilder result = new StringBuilder(parts[0].toLowerCase());

        for (int i = 1; i < parts.length; i++) {
            result.append(Character.toUpperCase(parts[i].charAt(0)))
                    .append(parts[i].substring(1).toLowerCase());
        }
        return result.toString();
    }

    // 7. Compter le nombre de voyelles dans une chaîne
    // Cas d'usage : Analyse phonétique de poésie
    public static int countVowels(String s) {
        int count = 0;
        String vowels = "aeiouyàâäéèêëîïôùûüÿœæ";

        for (char c : s.toLowerCase().toCharArray()) {
            if (vowels.indexOf(c) >= 0) count++;
        }
        return count;
    }

    // 8. Alterner majuscules et minuscules dans une chaîne
    // Cas d'usage : Génération de variantes visuelles de mots de passe
    public static String alternateCase(String s) {
        StringBuilder result = new StringBuilder();
        int letterIndex = 0;

        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append(letterIndex % 2 == 0
                        ? Character.toUpperCase(c)
                        : Character.toLowerCase(c));
                letterIndex++;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    // 9. Supprimer les caractères consécutifs en double
    // Cas d'usage : Nettoyage de messages utilisateurs (fautes de frappe)
    public static String removeDuplicates(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        StringBuilder result = new StringBuilder();
        result.append(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) != s.charAt(i - 1)) {
                result.append(s.charAt(i));
            }
        }
        return result.toString();
    }

    // 10. Extraire les initiales d'un nom complet
    // Cas d'usage : Génération d'identifiants employés en RH
    public static String extractInitials(String fullName) {
        StringBuilder initials = new StringBuilder();
        for (String word : fullName.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                initials.append(Character.toUpperCase(word.charAt(0))).append(".");
            }
        }
        return initials.toString();
    }

    // 11. Masquer les caractères sauf les N derniers
    // Cas d'usage : Affichage sécurisé de numéros de carte bancaire
    public static String maskString(String s, int visibleCount) {
        if (s == null || visibleCount >= s.length()) return s;
        String visible = s.substring(s.length() - visibleCount);
        String masked = "*".repeat(s.length() - visibleCount);
        return masked + visible;
    }

    // 12. Vérifier si une chaîne est un palindrome
    // Cas d'usage : Vérification de marques ou slogans mémorables
    public static boolean isPalindrome(String s) {
        String cleaned = s.toLowerCase().replaceAll("[^a-zàâäéèêëîïôùûüœæ]", "");
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    // 13. Trouver la plus longue séquence de caractères identiques consécutifs
    // Cas d'usage : Contrôle qualité de codes-barres
    public static String longestSequence(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        String longest = String.valueOf(s.charAt(0));
        StringBuilder current = new StringBuilder(String.valueOf(s.charAt(0)));

        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                current.append(s.charAt(i));
            } else {
                current = new StringBuilder(String.valueOf(s.charAt(i)));
            }
            if (current.length() > longest.length()) {
                longest = current.toString();
            }
        }
        return longest;
    }

    // 14. Tronquer un texte avec points de suspension
    // Cas d'usage : Descriptions de produits sur réseaux sociaux
    public static String truncate(String s, int maxLength) {
        if (s == null || s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength - 3) + "...";
    }

    // 15. Capitaliser la première lettre de chaque mot
    // Cas d'usage : Standardisation des titres de documents
    public static String capitalizeWords(String s) {
        return Arrays.stream(s.split(" "))
                .map(word -> word.isEmpty() ? word
                        : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
}
