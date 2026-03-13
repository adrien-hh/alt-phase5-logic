package org.alt.exercises;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StringExercisesTest {

  // 1. lengthWithoutSpaces
  @Test
  void lengthWithoutSpaces_shouldIgnoreSpaces() {
    assertEquals(15, StringExercises.lengthWithoutSpaces("Bonjour le monde !"));
  }

  @Test
  void lengthWithoutSpaces_shouldReturnZeroForOnlySpaces() {
    assertEquals(0, StringExercises.lengthWithoutSpaces("   "));
  }

  @Test
  void lengthWithoutSpaces_shouldWorkWithNoSpaces() {
    assertEquals(5, StringExercises.lengthWithoutSpaces("hello"));
  }

  // 2. greet
  @Test
  void greet_shouldCapitalizeSimpleName() {
    assertEquals("Bonjour Jean", StringExercises.greet("jean"));
  }

  @Test
  void greet_shouldHandleHyphenatedName() {
    assertEquals("Bonjour Jean-Pierre", StringExercises.greet("jean-pierre"));
  }

  @Test
  void greet_shouldHandleAlreadyCapitalized() {
    assertEquals("Bonjour Marie", StringExercises.greet("MARIE"));
  }

  // 3. endsWithExclamation
  @Test
  void endsWithExclamation_shouldReturnTrueWhenEndsWithBang() {
    assertTrue(StringExercises.endsWithExclamation("Je suis très satisfait !"));
  }

  @Test
  void endsWithExclamation_shouldReturnFalseOtherwise() {
    assertFalse(StringExercises.endsWithExclamation("Bonjour"));
  }

  @Test
  void endsWithExclamation_shouldReturnFalseForEmptyString() {
    assertFalse(StringExercises.endsWithExclamation(""));
  }

  // 4. reverseWords
  @Test
  void reverseWords_shouldReverseWordOrder() {
    assertEquals("pomme une mange Je", StringExercises.reverseWords("Je mange une pomme"));
  }

  @Test
  void reverseWords_shouldHandleSingleWord() {
    assertEquals("bonjour", StringExercises.reverseWords("bonjour"));
  }

  // 5. countOccurrences
  @Test
  void countOccurrences_shouldCountCorrectly() {
    assertEquals(3, StringExercises.countOccurrences("Bonjour le monde", 'o'));
  }

  @Test
  void countOccurrences_shouldBeCaseInsensitive() {
    assertEquals(2, StringExercises.countOccurrences("Bonjour bonjour", 'b'));
  }

  @Test
  void countOccurrences_shouldReturnZeroIfNotFound() {
    assertEquals(0, StringExercises.countOccurrences("bonjour", 'z'));
  }

  // 6. toCamelCase
  @Test
  void toCamelCase_shouldConvertSnakeCase() {
    assertEquals("userFirstName", StringExercises.toCamelCase("user_first_name"));
  }

  @Test
  void toCamelCase_shouldConvertKebabCase() {
    assertEquals("userFirstName", StringExercises.toCamelCase("user-first-name"));
  }

  @Test
  void toCamelCase_shouldHandleSingleWord() {
    assertEquals("user", StringExercises.toCamelCase("user"));
  }

  // 7. countVowels
  @Test
  void countVowels_shouldCountBasicVowels() {
    assertEquals(5, StringExercises.countVowels("bonjour monde"));
  }

  @Test
  void countVowels_shouldReturnZeroForNoVowels() {
    assertEquals(0, StringExercises.countVowels("rhthm"));
  }

  // 8. alternateCase
  @Test
  void alternateCase_shouldAlternateCasing() {
    assertEquals("BoNjOuR", StringExercises.alternateCase("bonjour"));
  }

  @Test
  void alternateCase_shouldSkipNonLetters() {
    assertEquals("BoN jOuR", StringExercises.alternateCase("bon jour"));
  }

  // 9. removeDuplicates
  @Test
  void removeDuplicates_shouldRemoveConsecutiveDuplicates() {
    assertEquals(
        "Bonjour ! J'ai besoin d'aide.",
        StringExercises.removeDuplicates("Bonjouuuur !!! J'ai besoiiiin d'aide...."));
  }

  @Test
  void removeDuplicates_shouldLeaveNonConsecutiveAlone() {
    assertEquals("abab", StringExercises.removeDuplicates("abab"));
  }

  // 10. extractInitials
  @Test
  void extractInitials_shouldExtractThreeInitials() {
    assertEquals("J.M.D.", StringExercises.extractInitials("Jean Martin Dupont"));
  }

  @Test
  void extractInitials_shouldExtractTwoInitials() {
    assertEquals("J.D.", StringExercises.extractInitials("jean dupont"));
  }

  // 11. maskString
  @Test
  void maskString_shouldMaskAllButLastFour() {
    assertEquals("************3456", StringExercises.maskString("1234567890123456", 4));
  }

  @Test
  void maskString_shouldReturnFullStringIfVisibleCountExceedsLength() {
    assertEquals("123", StringExercises.maskString("123", 5));
  }

  // 12. isPalindrome
  @Test
  void isPalindrome_shouldReturnTrueForPalindrome() {
    assertTrue(StringExercises.isPalindrome("kayak"));
  }

  @Test
  void isPalindrome_shouldIgnorePunctuation() {
    assertTrue(StringExercises.isPalindrome("Eh ! ca va la vache ?"));
  }

  @Test
  void isPalindrome_shouldReturnFalseForNonPalindrome() {
    assertFalse(StringExercises.isPalindrome("bonjour"));
  }

  // 13. longestSequence
  @Test
  void longestSequence_shouldFindLongestRun() {
    assertEquals("bbbbb", StringExercises.longestSequence("aaabbbbbcccc"));
  }

  @Test
  void longestSequence_shouldHandleSingleChar() {
    assertEquals("a", StringExercises.longestSequence("a"));
  }

  // 14. truncate
  @Test
  void truncate_shouldTruncateWithEllipsis() {
    assertEquals(
        "Ceci est une très...",
        StringExercises.truncate("Ceci est une très longue description d'un produit", 20));
  }

  @Test
  void truncate_shouldNotTruncateIfShortEnough() {
    assertEquals("Bonjour", StringExercises.truncate("Bonjour", 20));
  }

  // 15. capitalizeWords
  @Test
  void capitalizeWords_shouldCapitalizeEachWord() {
    assertEquals(
        "Bienvenue Sur Notre Site Web",
        StringExercises.capitalizeWords("bienvenue sur notre site web"));
  }

  @Test
  void capitalizeWords_shouldHandleSingleWord() {
    assertEquals("Bonjour", StringExercises.capitalizeWords("bonjour"));
  }
}
