.read data.sql


CREATE TABLE bluedog AS
  SELECT color, pet from students WHERE color='blue' AND pet='dog';

CREATE TABLE bluedog_songs AS
  SELECT color, pet, song from students WHERE color='blue' AND pet='dog';


CREATE TABLE matchmaker AS
  SELECT s1.pet, s1.song, s1.color, s2.color from students as s1, students as s2
  WHERE s1.pet=s2.pet AND s1.song=s2.song AND s1.time != s2.time AND s1.time < s2.time;


CREATE TABLE sevens AS
  SELECT s.seven
  from students as s
  JOIN numbers as n on s.time = n.time
  WHERE s.number = 7 and n.'7'='True';


CREATE TABLE favpets AS
  SELECT s.pet, COUNT(s.pet)
  from students as s
  GROUP BY s.pet
  ORDER BY COUNT(s.pet) DESC
  LIMIT 10;


CREATE TABLE dog AS
  SELECT s.pet, COUNT(s.pet)
  from students as s
  WHERE s.pet="dog";


CREATE TABLE bluedog_agg AS
  SELECT s.song, COUNT(s.song)
  from bluedog_songs as s
  GROUP BY s.song
  ORDER BY COUNT(song);


CREATE TABLE instructor_obedience AS
  SELECT s.seven, s.instructor, COUNT(s.instructor)
  from students as s
  JOIN numbers as n on s.time = n.time
  WHERE s.seven="7"
  GROUP BY s.instructor;


CREATE TABLE smallest_int_having AS
  SELECT "REPLACE THIS LINE WITH YOUR SOLUTION";


CREATE TABLE smallest_int_count AS
  SELECT "REPLACE THIS LINE WITH YOUR SOLUTION";

