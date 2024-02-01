#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "asserts.h"
// Necessary due to static functions in state.c
#include "state.c"

/* Look at asserts.c for some helpful assert functions */

int greater_than_forty_two(int x) { return x > 42; }

bool is_vowel(char c) {
  char* vowels = "aeiouAEIOU";
  for (int i = 0; i < strlen(vowels); i++) {
    if (c == vowels[i]) {
      return true;
    }
  }
  return false;
}

/*
  Example 1: Returns true if all test cases pass. False otherwise.
    The function greater_than_forty_two(int x) will return true if x > 42. False otherwise.
    Note: This test is NOT comprehensive
*/
bool test_greater_than_forty_two() {
  int testcase_1 = 42;
  bool output_1 = greater_than_forty_two(testcase_1);
  if (!assert_false("output_1", output_1)) {
    return false;
  }

  int testcase_2 = -42;
  bool output_2 = greater_than_forty_two(testcase_2);
  if (!assert_false("output_2", output_2)) {
    return false;
  }

  int testcase_3 = 4242;
  bool output_3 = greater_than_forty_two(testcase_3);
  if (!assert_true("output_3", output_3)) {
    return false;
  }

  return true;
}

/*
  Example 2: Returns true if all test cases pass. False otherwise.
    The function is_vowel(char c) will return true if c is a vowel (i.e. c is a,e,i,o,u)
    and returns false otherwise
    Note: This test is NOT comprehensive
*/
bool test_is_vowel() {
  char testcase_1 = 'a';
  bool output_1 = is_vowel(testcase_1);
  if (!assert_true("output_1", output_1)) {
    return false;
  }

  char testcase_2 = 'e';
  bool output_2 = is_vowel(testcase_2);
  if (!assert_true("output_2", output_2)) {
    return false;
  }

  char testcase_3 = 'i';
  bool output_3 = is_vowel(testcase_3);
  if (!assert_true("output_3", output_3)) {
    return false;
  }

  char testcase_4 = 'o';
  bool output_4 = is_vowel(testcase_4);
  if (!assert_true("output_4", output_4)) {
    return false;
  }

  char testcase_5 = 'u';
  bool output_5 = is_vowel(testcase_5);
  if (!assert_true("output_5", output_5)) {
    return false;
  }

  char testcase_6 = 'k';
  bool output_6 = is_vowel(testcase_6);
  if (!assert_false("output_6", output_6)) {
    return false;
  }

  return true;
}

/* Task 4.1 */

bool test_is_tail() {
  char test_case_1 = '^';
  char test_case_2 = '<';
  char test_case_3 = '>';
  char test_case_4 = 'v';
  char test_case_5 = ' ';
  char test_case_6 = '*';
  char test_case_7 = '#';
  char test_case_8 = 'W';
  char test_case_9 = 'A';
  char test_case_10 = 'S';
  char test_case_11 = 'D';
  char test_case_12 = 'w';
  char test_case_13 = 'a';
  char test_case_14 = 's';
  char test_case_15 = 'd';

  if (!assert_false("op_1", is_tail(test_case_1))) {
    return false;
  }
  if (!assert_false("op_2", is_tail(test_case_2))) {
    return false;
  }
  if (!assert_false("op_3", is_tail(test_case_3))) {
    return false;
  }
  if (!assert_false("op_4", is_tail(test_case_4))) {
    return false;
  }
  if (!assert_false("op_5", is_tail(test_case_5))) {
    return false;
  }
  if (!assert_false("op_6", is_tail(test_case_6))) {
    return false;
  }
  if (!assert_false("op_7", is_tail(test_case_7))) {
    return false;
  }
  if (!assert_false("op_8", is_tail(test_case_8))) {
    return false;
  }
  if (!assert_false("op_9", is_tail(test_case_9))) {
    return false;
  }
  if (!assert_false("op_10", is_tail(test_case_10))) {
    return false;
  }
  if (!assert_false("op_11", is_tail(test_case_11))) {
    return false;
  }
  if (!assert_true("op_12", is_tail(test_case_12))) {
    return false;
  }
  if (!assert_true("op_13", is_tail(test_case_13))) {
    return false;
  }
  if (!assert_true("op_14", is_tail(test_case_14))) {
    return false;
  }
  if (!assert_true("op_15", is_tail(test_case_15))) {
    return false;
  }
  return true;
}

bool test_is_head() {
    char test_case_1 = '^';
  char test_case_2 = '<';
  char test_case_3 = '>';
  char test_case_4 = 'v';
  char test_case_5 = ' ';
  char test_case_6 = '*';
  char test_case_7 = '#';
  char test_case_8 = 'W';
  char test_case_9 = 'A';
  char test_case_10 = 'S';
  char test_case_11 = 'D';
  char test_case_12 = 'w';
  char test_case_13 = 'a';
  char test_case_14 = 's';
  char test_case_15 = 'd';

  if (!assert_false("op_1", is_head(test_case_1))) {
    return false;
  }
  if (!assert_false("op_2", is_head(test_case_2))) {
    return false;
  }
  if (!assert_false("op_3", is_head(test_case_3))) {
    return false;
  }
  if (!assert_false("op_4", is_head(test_case_4))) {
    return false;
  }
  if (!assert_false("op_5", is_head(test_case_5))) {
    return false;
  }
  if (!assert_false("op_6", is_head(test_case_6))) {
    return false;
  }
  if (!assert_false("op_7", is_head(test_case_7))) {
    return false;
  }
  if (!assert_true("op_8", is_head(test_case_8))) {
    return false;
  }
  if (!assert_true("op_9", is_head(test_case_9))) {
    return false;
  }
  if (!assert_true("op_10", is_head(test_case_10))) {
    return false;
  }
  if (!assert_true("op_11", is_head(test_case_11))) {
    return false;
  }
  if (!assert_false("op_12", is_head(test_case_12))) {
    return false;
  }
  if (!assert_false("op_13", is_head(test_case_13))) {
    return false;
  }
  if (!assert_false("op_14", is_head(test_case_14))) {
    return false;
  }
  if (!assert_false("op_15", is_head(test_case_15))) {
    return false;
  }
  return true;
}

bool test_is_snake() {
  char test_case_1 = '^';
  char test_case_2 = '<';
  char test_case_3 = '>';
  char test_case_4 = 'v';
  char test_case_5 = ' ';
  char test_case_6 = '*';
  char test_case_7 = '#';
  char test_case_8 = 'W';
  char test_case_9 = 'A';
  char test_case_10 = 'S';
  char test_case_11 = 'D';
  char test_case_12 = 'w';
  char test_case_13 = 'a';
  char test_case_14 = 's';
  char test_case_15 = 'd';
  

  bool op_1 = is_snake(test_case_1);
  bool op_2 = is_snake(test_case_2);
  bool op_3 = is_snake(test_case_3);
  bool op_4 = is_snake(test_case_4);
  bool op_5 = is_snake(test_case_5);
  bool op_6 = is_snake(test_case_6);
  bool op_7 = is_snake(test_case_7);
  bool op_8 = is_snake(test_case_8);
  bool op_9 = is_snake(test_case_9);
  bool op_10 = is_snake(test_case_10);
  bool op_11 = is_snake(test_case_11);
  bool op_12 = is_snake(test_case_12);
  bool op_13 = is_snake(test_case_13);
  bool op_14 = is_snake(test_case_14);
  bool op_15 = is_snake(test_case_15);
  if (!assert_true("op_1", op_1)) {
    return false;
  }
  if (!assert_true("op_2", op_2)) {
    return false;
  }
  if (!assert_true("op_3", op_3)) {
    return false;
  }
  if (!assert_true("op_4", op_4)) {
    return false;
  }
  if (!assert_false("op_5", op_5)) {
    return false;
  }
  if (!assert_false("op_6", op_6)) {
    return false;
  }
  if (!assert_false("op_7", op_7)) {
    return false;
  }
  if (!assert_true("op_8", op_8)) {
    return false;
  }
  if (!assert_true("op_9", op_9)) {
    return false;
  }
  if (!assert_true("op_10", op_10)) {
    return false;
  }
  if (!assert_true("op_11", op_11)) {
    return false;
  }
  if (!assert_true("op_12", op_12)) {
    return false;
  }
  if (!assert_true("op_13", op_13)) {
    return false;
  }
  if (!assert_true("op_14", op_14)) {
    return false;
  }if (!assert_true("op_15", op_15)) {
    return false;
  }
  
  return true;
}

bool test_body_to_tail() {
  // TODO: Implement this function
  char test_case_1 = '^';
  char test_case_2 = '<';
  char test_case_3 = '>';
  char test_case_4 = 'v';
  char expected_1 = 'w';
  char expected_2 = 'a';
  char expected_3 = 'd';
  char expected_4 = 's';
  char op_1 = body_to_tail(test_case_1);
  char op_2 = body_to_tail(test_case_2);
  char op_3 = body_to_tail(test_case_3);
  char op_4 = body_to_tail(test_case_4);
  

  if (!assert_equals_char("Tail char: ", expected_1, op_1)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_2, op_2)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_3, op_3)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_4, op_4)) {
    return false;
  }
  return true;
  
}

bool test_head_to_body() {
  char test_case_1 = 'W';
  char test_case_2 = 'A';
  char test_case_3 = 'S';
  char test_case_4 = 'D';
  char expected_1 = '^';
  char expected_2 = '<';
  char expected_3 = 'v';
  char expected_4 = '>';
  char op_1 = head_to_body(test_case_1);
  char op_2 = head_to_body(test_case_2);
  char op_3 = head_to_body(test_case_3);
  char op_4 = head_to_body(test_case_4);
  
  
  if (!assert_equals_char("Tail char: ", expected_1, op_1)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_2, op_2)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_3, op_3)) {
    return false;
  }
  if (!assert_equals_char("Tail char: ", expected_4, op_4)) {
    return false;
  }
  return true;
}

bool test_get_next_x() {
  // col
  
  if (!assert_equals_int("Next collum: ", 6, get_next_col(5, '>'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 6, get_next_col(5, 'D'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 6, get_next_col(5, 'd'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 4, get_next_col(5, '<'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 4, get_next_col(5, 'a'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 4, get_next_col(5, 'A'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, '^'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, 'w'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, 'W'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, 'v'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, 's'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, 'S'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, ' '))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, '*'))) {
    return false;
  }
  if (!assert_equals_int("Next collum: ", 5, get_next_col(5, '#'))) {
    return false;
  }
  
  return true;
}

bool test_get_next_y() {
  // row
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, '>'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, 'D'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, 'd'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, '<'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, 'a'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, 'A'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 4, get_next_row(5, '^'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 4, get_next_row(5, 'w'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 4, get_next_row(5, 'W'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 6, get_next_row(5, 'v'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 6, get_next_row(5, 's'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 6, get_next_row(5, 'S'))) {
    return false;
  }
  if (!assert_equals_int("Next  ", 5, get_next_row(5, ' '))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_row(5, '*'))) {
    return false;
  }
  if (!assert_equals_int("Next row: ", 5, get_next_col(5, '#'))) {
    return false;
  }
  
  return true;
}

bool test_customs() {
  if (!test_greater_than_forty_two()) {
    printf("%s\n", "test_greater_than_forty_two failed.");
    return false;
  }
  if (!test_is_vowel()) {
    printf("%s\n", "test_is_vowel failed.");
    return false;
  }
  if (!test_is_tail()) {
    printf("%s\n", "test_is_tail failed");
    return false;
  }
  if (!test_is_head()) {
    printf("%s\n", "test_is_head failed");
    return false;
  }
  if (!test_is_snake()) {
    printf("%s\n", "test_is_snake failed");
    return false;
  }
  if (!test_body_to_tail()) {
    printf("%s\n", "test_body_to_tail failed");
    return false;
  }
  if (!test_head_to_body()) {
    printf("%s\n", "test_head_to_body failed");
    return false;
  }
  if (!test_get_next_x()) {
    printf("%s\n", "test_get_next_x failed");
    return false;
  }
  if (!test_get_next_y()) {
    printf("%s\n", "test_get_next_y failed");
    return false;
  }
  return true;
}

int main(int argc, char* argv[]) {
  init_colors();

  if (!test_and_print("custom", test_customs)) {
    return 0;
  }

  return 0;
}
