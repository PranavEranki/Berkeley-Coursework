#include "state.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "snake_utils.h"

#define LEN(arr) ((int) (sizeof(arr) / sizeof((arr)[0])))

/* Helper function definitions */
static void set_board_at(game_state_t* state, unsigned int row, unsigned int col, char ch);
static bool is_tail(char c);
static bool is_head(char c);
static bool is_snake(char c);
static char body_to_tail(char c);
static char head_to_body(char c);
static unsigned int get_next_row(unsigned int cur_row, char c);
static unsigned int get_next_col(unsigned int cur_col, char c);
static void find_head(game_state_t* state, unsigned int snum);
static char next_square(game_state_t* state, unsigned int snum);
static void update_tail(game_state_t* state, unsigned int snum);
static void update_head(game_state_t* state, unsigned int snum);

/* Task 1 */
game_state_t* create_default_state() {
  // TODO: Implement this function.
// ####################
// #                  #
// # d>D    *         #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// #                  #
// ####################
  char board_ptr[18][21] = {
    {"####################\0"},
    {"#                  #\0"},
    {"# d>D    *         #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"#                  #\0"},
    {"####################\0"}
  };

  game_state_t* state = malloc(sizeof(game_state_t));
 
  state->num_rows = 18;
  state->board = malloc(sizeof(char*)* state->num_rows);
  char **p = state->board;
  for (int i = 0; i < state->num_rows; i++) {
    *p = malloc(sizeof(char) * 21);
    strcpy(*p , board_ptr[i]);
    p++;
  }

  state->num_snakes = 1;

  state->snakes = malloc(sizeof(snake_t) * state->num_snakes);
  
  state->snakes->tail_row = 2;
  state->snakes->tail_col = 2;
  state->snakes->head_row = 2;
  state->snakes->head_col = 4;
  state->snakes-> live = true;

  return state;
}

/* Task 2 */
void free_state(game_state_t* state) {
  // TODO: Implement this function.
  char **p = state->board;
  for (int i = 0; i < state->num_rows; i++) {
    free(*p);
    p++;
  }
  free(state->board);
  free(state->snakes);
  
  free(state);
  return;
}

/* Task 3 */
void print_board(game_state_t* state, FILE* fp) {
  // TODO: Implement this function.
  char **p = state->board;
  for(int i = 0; i < state->num_rows; i++){
    fprintf(fp, *p);
    fprintf(fp,"\n");
    p++;
  }
  return;
}

/*
  Saves the current state into filename. Does not modify the state object.
  (already implemented for you).
*/
void save_board(game_state_t* state, char* filename) {
  FILE* f = fopen(filename, "w");
  print_board(state, f);
  fclose(f);
}

/* Task 4.1 */

/*
  Helper function to get a character from the board
  (already implemented for you).
*/
char get_board_at(game_state_t* state, unsigned int row, unsigned int col) {
  return state->board[row][col];
}

/*
  Helper function to set a character on the board
  (already implemented for you).
*/
static void set_board_at(game_state_t* state, unsigned int row, unsigned int col, char ch) {
  state->board[row][col] = ch;
}

/*
  Returns true if c is part of the snake's tail.
  The snake consists of these characters: "wasd"
  Returns false otherwise.
*/
static bool is_tail(char c) {
  // TODO: Implement this function.
  if (c == 'w' || c == 'a' || c == 's' || c == 'd') return true;
  return false;
}

/*
  Returns true if c is part of the snake's head.
  The snake consists of these characters: "WASDx"
  Returns false otherwise.
*/
static bool is_head(char c) {
  // TODO: Implement this function.
  if (c == 'W' || c == 'A' || c == 'S' || c == 'D') return true;
  return false;
}

/*
  Returns true if c is part of the snake.
  The snake consists of these characters: "wasd^<v>WASDx"
*/
static bool is_snake(char c) {
  // TODO: Implement this function.
  if (c == '^' || c == '<' || c == 'v' || c == '>' 
                        || is_head(c) || is_tail(c)) return true;
  return false;
}

/*
  Converts a character in the snake's body ("^<v>")
  to the matching character representing the snake's
  tail ("wasd").
*/
static char body_to_tail(char c) {
  // TODO: Implement this function.
  switch(c) {
    case '^':
      return 'w';
    case '<':
      return 'a';
    case 'v':
      return 's';
    case '>':
      return 'd';
    break;
  }
  return c;
}

/*
  Converts a character in the snake's head ("WASD")
  to the matching character representing the snake's
  body ("^<v>").
*/
static char head_to_body(char c) {
  // TODO: Implement this function.
  switch(c) {
    case 'W':
      return '^';
    case 'A':
      return '<';
    case 'S':
      return 'v';
    case 'D':
      return '>';
    break;
  }
  return c;
  
}

/*
  Returns cur_row + 1 if c is 'v' or 's' or 'S'.
  Returns cur_row - 1 if c is '^' or 'w' or 'W'.
  Returns cur_row otherwise.
*/
static unsigned int get_next_row(unsigned int cur_row, char c) {
  // TODO: Implement this function.
  if (c == 'v' || c == 's' || c == 'S') {
    cur_row++;
  } else if (c == '^' || c == 'w' || c == 'W') {
    cur_row--;
  }
  return cur_row;
}

/*
  Returns cur_col + 1 if c is '>' or 'd' or 'D'.
  Returns cur_col - 1 if c is '<' or 'a' or 'A'.
  Returns cur_col otherwise.
*/
static unsigned int get_next_col(unsigned int cur_col, char c) {
  // TODO: Implement this function.
  if (c == '>' || c == 'd' || c == 'D') {
    cur_col++;
  } else if (c == '<' || c == 'a' || c == 'A') {
    cur_col--;
  }
  return cur_col;
}

/*
  Task 4.2

  Helper function for update_state. Return the character in the cell the snake is moving into.

  This function should not modify anything.
*/
static char next_square(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  unsigned int cur_col = state->snakes[snum].head_col;
  unsigned int cur_row = state->snakes[snum].head_row;
  char cur_c = get_board_at(state, cur_row, cur_col);
  return get_board_at(state, get_next_row(cur_row, cur_c), 
                                get_next_col(cur_col, cur_c));
}

/*
  Task 4.3

  Helper function for update_state. Update the head...

  ...on the board: add a character where the snake is moving

  ...in the snake struct: update the row and col of the head

  Note that this function ignores food, walls, and snake bodies when moving the head.
*/
static void update_head(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  unsigned int cur_row = state->snakes[snum].head_row;
  unsigned int cur_col = state->snakes[snum].head_col;
  char cur_head = get_board_at(state, cur_row, cur_col);
  
  unsigned int new_head_col = get_next_col(cur_col, cur_head);
  unsigned int new_head_row = get_next_row(cur_row, cur_head);
  
  state->snakes[snum].head_col = new_head_col;
  state->snakes[snum].head_row = new_head_row;
  set_board_at(state ,new_head_row, new_head_col, cur_head);
  set_board_at(state, cur_row, cur_col, head_to_body(cur_head));
  return;
}

/*
  Task 4.4

  Helper function for update_state. Update the tail...

  ...on the board: blank out the current tail, and change the new
  tail from a body character (^<v>) into a tail character (wasd)

  ...in the snake struct: update the row and col of the tail
*/
static void update_tail(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  unsigned int cur_row = state->snakes[snum].tail_row;
  unsigned int cur_col = state->snakes[snum].tail_col;
  char cur_tail = get_board_at(state, cur_row, cur_col);
  
  unsigned int new_tail_col = get_next_col(cur_col, cur_tail);
  unsigned int new_tail_row = get_next_row(cur_row, cur_tail);
  char new_tail = get_board_at(state, new_tail_row, new_tail_col);
  new_tail = body_to_tail(new_tail);
  
  state->snakes[snum].tail_col = new_tail_col;
  state->snakes[snum].tail_row = new_tail_row;
  set_board_at(state ,new_tail_row, new_tail_col, new_tail);
  set_board_at(state, cur_row, cur_col, ' ');
  return;
}

/* Task 4.5 */
void update_state(game_state_t* state, int (*add_food)(game_state_t* state)) {
  // TODO: Implement this function.
  for (unsigned int s = 0; s < state->num_snakes; s++) {
    char nxt_sqr = next_square(state, s);
    if (nxt_sqr == '#' || is_snake(nxt_sqr)) {
      state->snakes[s].live = false;
      set_board_at(state, state->snakes[s].head_row, state->snakes[s].head_col, 'x');
    } else if(nxt_sqr == '*') {
      update_head(state, s);
      add_food(state);
    } else {
      update_head(state, s);
      update_tail(state, s);
    }
  }
  return;
}

/* Task 5 */
game_state_t* load_board(char* filename) {
  // TODO: Implement this function.
  game_state_t* state = malloc(sizeof(game_state_t));
  FILE *fp = fopen(filename, "r");
  if (fp == NULL) {
    return NULL;
  }
  unsigned int num_row = 0;
  while (!feof(fp)) {
    if (fgetc(fp) == '\n') {
      num_row += 1;
    }
  }
  fclose(fp);
  state->num_rows = num_row;
  state->board =(char**)malloc(sizeof(char*) * num_row);
  FILE *first_p = fopen(filename, "r");
  FILE *second_p = fopen(filename, "r");
  int num_cols = 0;
  int row = 0;
  while(!feof(first_p)) {
    char c = fgetc(first_p);
    if (c != '\n') {
      num_cols++;
    } else {
      state->board[row] = (char*)malloc(sizeof(char) * (num_cols + 1));
      for (int i = 0; i <= num_cols; i++) {
        char s = fgetc(second_p);
        if (s != '\n') {
          state->board[row][i] = s;
        }else {
          state->board[row][i] = '\0';
        }
      } 
      row++;
      num_cols = 0;
    }
  }
  fclose(first_p);
  fclose(second_p);
  return state;
}

/*
  Task 6.1

  Helper function for initialize_snakes.
  Given a snake struct with the tail row and col filled in,
  trace through the board to find the head row and col, and
  fill in the head row and col in the struct.
*/
static void find_head(game_state_t* state, unsigned int snum) {
  // TODO: Implement this function.
  unsigned int col = state->snakes[snum].tail_col;
  unsigned int row = state->snakes[snum].tail_row;
  char c = get_board_at(state, row, col);
  while (!is_head(c)) {
    col = get_next_col(col, c);
    row = get_next_row(row, c);
    c = get_board_at(state, row, col);
  }
  state->snakes[snum].head_row = row;
  state->snakes[snum].head_col = col;


  return;
}

/* Task 6.2 */
game_state_t* initialize_snakes(game_state_t* state) {
  // TODO: Implement this function.
  unsigned int row = 0;
  unsigned int col = 0;
  int num_tail = 0;
  for (row = 0; row < state->num_rows; row++) {
    for (col = 0; col < strlen(state->board[row]); col++) {
      if (is_tail(get_board_at(state, row, col))) {
        num_tail++;
      }
    }
  }
  state->num_snakes = num_tail;
  state->snakes = malloc(sizeof(snake_t) * num_tail);
  unsigned int t_num = 0;
  for (row = 0; row < state->num_rows; row++) {
    for (col = 0; col < strlen(state->board[row]); col++) {
      if (is_tail(get_board_at(state, row, col))) {
        state->snakes[t_num].tail_row = row;
        state->snakes[t_num].tail_col = col;
        find_head(state, t_num);
        state->snakes[t_num].live = true;
        t_num++;
      }
    }
  }

  return state;
}
