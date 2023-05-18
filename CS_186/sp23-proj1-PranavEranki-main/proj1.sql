-- Before running drop any existing views
DROP VIEW IF EXISTS q0;
DROP VIEW IF EXISTS q1i;
DROP VIEW IF EXISTS q1ii;
DROP VIEW IF EXISTS q1iii;
DROP VIEW IF EXISTS q1iv;
DROP VIEW IF EXISTS q2i;
DROP VIEW IF EXISTS q2ii;
DROP VIEW IF EXISTS q2iii;
DROP VIEW IF EXISTS q3i;
DROP VIEW IF EXISTS q3ii;
DROP VIEW IF EXISTS q3iii;
DROP VIEW IF EXISTS q4i;
DROP VIEW IF EXISTS q4ii;
DROP VIEW IF EXISTS q4iii;
DROP VIEW IF EXISTS q4iv;
DROP VIEW IF EXISTS q4v;

-- Question 0
CREATE VIEW q0(era)
AS
   SELECT MAX(era)
   FROM pitching;
;

-- Question 1i
CREATE VIEW q1i(namefirst, namelast, birthyear)
AS
  SELECT nameFirst, nameLast, birthYear
  FROM people
  WHERE weight > 300;
;

-- Question 1ii
CREATE VIEW q1ii(namefirst, namelast, birthyear)
AS
  SELECT nameFirst, nameLast, birthYear
  FROM people
  WHERE nameFirst like '% %'
  ORDER BY nameFirst, nameLast ASC;
;

-- Question 1iii
CREATE VIEW q1iii(birthyear, avgheight, count)
AS
  SELECT birthYear, AVG(height), COUNT(playerid)
  FROM people
  GROUP BY birthYear
  ORDER BY birthYear ASC;
;

-- Question 1iv'
CREATE VIEW q1iv(birthyear, avgheight, count)
AS
  SELECT birthYear, AVG(height), COUNT(playerid)
  FROM people
  GROUP BY birthYear
  HAVING AVG(height) > 70
  ORDER BY birthYear ASC;
  
;

-- Question 2i
CREATE VIEW q2i(namefirst, namelast, playerid, yearid)
AS
  SELECT DISTINCT nameFirst, nameLast, people.playerID, halloffame.yearid
  FROM people INNER JOIN halloffame on people.playerID = halloffame.playerID
  WHERE inducted LIKE 'Y'
  ORDER BY yearid DESC, people.playerID ASC;
;

-- Question 2ii
CREATE VIEW q2ii(namefirst, namelast, playerid, schoolid, yearid)
AS
  SELECT namefirst, namelast, q2i.playerID, collegeplaying.schoolID, yearid
  FROM q2i INNER JOIN collegeplaying on q2i.playerid = collegeplaying.playerid
  INNER JOIN schools on collegeplaying.schoolID = schools.schoolID
  WHERE schools.schoolState LIKE 'CA'
  ORDER BY yearid DESC, schools.schoolID ASC, q2i.playerID ASC;
;

-- Question 2iii
CREATE VIEW q2iii(playerid, namefirst, namelast, schoolid)
AS
  SELECT DISTINCT q2i.playerID, namefirst, namelast, collegeplaying.schoolID
  FROM q2i LEFT OUTER JOIN collegeplaying on q2i.playerid = collegeplaying.playerid
  LEFT OUTER JOIN schools on collegeplaying.schoolID = schools.schoolID
  ORDER BY q2i.playerid DESC, schools.schoolID ASC;
;

-- Question 3i
CREATE VIEW q3i(playerid, namefirst, namelast, yearid, slg)
AS
  SELECT people.playerID, nameFirst, nameLast, yearid, (CAST(batting.H + batting.H2B + 2*batting.H3B + 3*batting.HR as FLOAT) / CAST(AB as FLOAT)) as slg -- floating point computation
  FROM people INNER JOIN batting on people.playerID = batting.playerID
  WHERE AB > 50
  ORDER BY slg desc, yearid asc, people.playerID asc
  LIMIT 10;
;


-- Question 3ii
CREATE VIEW q3ii(playerid, namefirst, namelast, lslg)
AS  
  SELECT pid, nf, nl, snum/sdenom as lslg
  FROM
  (SELECT pid, nf, nl, Sum(num) as snum, Sum(denom) as sdenom
  FROM
    (SELECT people.playerID as pid, nameFirst as nf, nameLast as nl, yearID, 
    CAST(batting.H + batting.H2B + 2*batting.H3B + 3*batting.HR as FLOAT) as num, CAST(AB as FLOAT) as denom
    FROM people INNER JOIN batting on people.playerID = batting.playerID)
  GROUP BY pid
  HAVING sdenom > 50)
  ORDER BY lslg desc
  LIMIT 10;

;

DROP VIEW IF EXISTS q3helper;
CREATE VIEW q3helper(pid, nf, nl, snum, sdenom, lslg)
AS SELECT pid, nf, nl, Sum(num) as snum, Sum(denom) as sdenom, Sum(num)/Sum(denom) as lslg
  FROM
    (SELECT people.playerID as pid, nameFirst as nf, nameLast as nl, yearID, 
    CAST(batting.H + batting.H2B + 2*batting.H3B + 3*batting.HR as FLOAT) as num, CAST(AB as FLOAT) as denom
    FROM people INNER JOIN batting on people.playerID = batting.playerID)
  GROUP BY pid
  HAVING sdenom > 50;

-- Question 3iii
CREATE VIEW q3iii(namefirst, namelast, lslg)
AS
  WITH mayswi(val) as (Select lslg from q3helper WHERE pid LIKE 'mayswi01')
  SELECT nf, nl, lslg
  FROM q3helper, mayswi
  WHERE lslg > mayswi.val
  -- WHERE NOT EXISTS ( 
  --   SELECT pid as inid, lslg as inner from q3helper
  --   where inner < (Select lslg from q3helper WHERE pid LIKE 'mayswi01') AND inid = pid
  -- );
;

-- Question 4i


  -- GROUP BY s.yearID
CREATE VIEW q4i(yearid, min, max, avg)
AS
  SELECT s.yearID, min(s.salary), max(s.salary), avg(s.salary)
  FROM people p inner join salaries s on p.playerID = s.playerID
  GROUP BY s.yearID
  ORDER BY s.yearID ASC;
;

-- order salary ascending
-- organize salary bins.
-- bin, salary start, salary end

-- (FLOOR(CAST(COUNT(s.*) as FLOAT) / CAST(10 as FLOAT))) * , floor# + constant size, constant size

-- WITH bin_vals as 
--   select *
--   from binids b inner join (Select * from salaries s WHERE s.ID != 1) 
--   on s.ID between (FLOOR(CAST(COUNT(s.*) as FLOAT) / CAST(10 as FLOAT))) * b.binid AND (FLOOR(CAST(COUNT(s.*) as FLOAT) / CAST(10 as FLOAT))) * (b.binid + 1) - 1

-- (FLOOR(CAST(COUNT(s.*) as FLOAT) / CAST(10 as FLOAT))) * 
  -- select *
  -- from binids b inner join (Select * from salaries s WHERE s.ID != 1) 
  -- on s.ID between (FLOOR(CAST(COUNT(s.ID) as FLOAT) / CAST(10 as FLOAT))) * b.binid AND (FLOOR(CAST(COUNT(s.ID) as FLOAT) / CAST(10 as FLOAT))) * (b.binid + 1) - 1

-- Question 4ii
CREATE VIEW q4ii(binid, low, high, count)
AS
-- max - min = our range, and our diff is salary - min
-- WITH binned_salaries AS (
-- SELECT 
-- (salary - min(salary) OVER()) / ((max(salary) OVER() - min(salary) OVER()) / 9) AS bin_index, 
-- salary
-- FROM salaries 
-- WHERE yearID = 2016
-- )
-- SELECT
-- floor(bin_index) AS binid,
-- min(salary) + CAST(floor(bin_index) as FLOAT) * (CAST(max(salary) - min(salary) AS FLOAT) / CAST(9 as FLOAT)) AS low, -- min of this range
-- min(salary) + CAST(floor(bin_index) + 1 as FLOAT) * (CAST(max(salary) - min(salary) AS FLOAT) / CAST(9 as FLOAT)) AS high,
-- count(salary) AS count
-- FROM binned_salaries
-- GROUP BY floor(bin_index)
-- ORDER BY
-- binid;

-- -- COUNT(*) OVER (Partition by binid) as count
WITH salary_c as (
  SELECT salary, min(salary) as mi, max(salary) as mx, max(salary) - min(salary) / 10.0  as bin_size
  from salaries
  WHERE salaries.yearID = 2016
), bin_calcs as (
  SELECT cast((salaries.salary - mi) / (mx - mi) * 10.0 as int) as binid, mi + cast((salaries.salary - mi) / (mx - mi) * 10.0 as int) * bin_size as low, mi + cast((salaries.salary - mi) / (mx - mi) * 10.0 as int) * bin_size + bin_size as high
  FROM salaries, salary_c
)
SELECT bin_calcs.binid, bin_calcs.low, bin_calcs.high, 3
FROM bin_calcs
WHERE binid != 10
GROUP BY binid;

-- , counterr as (
--   SELECT bin_calcs.binid
--   FROM bin_calcs inner join salaries on salaries.salary between bin_calcs.low and bin_calcs.high
--   GROUP BY bin_calcs.binid
-- )
-- select * from counterr;
-- SELECT bin_calcs.binid, bin_calcs.low, bin_calcs.high, COUNT(salaries.salary BETWEEN low AND high) as count
-- FROM salaries, salary_c inner join bin_calcs on salaries.salary between bin_calcs.low and bin_calcs.high
-- WHERE binid != 10
-- GROUP BY binid;

-- cast((salary - min(salary)) / (max(salary) - min(salary)) * 10.0 as int) as binid
-- SELECT binid as binid, mi + bin_size * binid as low, mi + bin_size * binid + 
-- FROM salary_c
-- GROUP BY binid;
-- WITH binned_salaries AS (
--   SELECT binid,
--     (min(salary) + ((binid) * ((max(salary) - min(salary)) / 10))) AS low,
--     (min(salary) + ((binid + 1) * ((max(salary) - min(salary)) / 10))) AS high,
--     count(salary) AS count
--   FROM (
--     SELECT salary,
--     CAST((salary - min(salary)) / ((max(salary) - min(salary)) / 10) AS INT) AS binid
--     FROM salaries
--     WHERE yearID > 1
--     GROUP BY binid )
-- )
-- SELECT binned_salaries.binid, low, high, binned_salaries.count as count
-- FROM binids LEFT JOIN binned_salaries ON binids.binid = binned_salaries.binid
-- ORDER BY binids.binid;

-- Question 4iii
CREATE VIEW q4iii(yearid, mindiff, maxdiff, avgdiff)
AS
SELECT 
    current.yearid, 
    current.min - previous.min as mindiff,
    current.max - previous.max as maxdiff,
    current.avg - previous.avg as avgdiff
  FROM q4i current JOIN q4i previous ON current.yearid = previous.yearid + 1
  ORDER BY current.yearid
;

-- Question 4iv
CREATE VIEW q4iv(playerid, namefirst, namelast, salary, yearid)
AS

SELECT people.playerid, namefirst, namelast, salary, yearid
FROM people inner join salaries on people.playerid = salaries.playerID
WHERE (yearid, salary) IN (
    SELECT yearid, max(salary)
    FROM salaries
    WHERE yearid IN (2000, 2001)
    GROUP BY yearid
)
ORDER BY yearid, salary DESC;

-- Question 4v
-- select the team's highest paid salary and lowest paid salary max(team_allstars.salary) as mx, 
CREATE VIEW q4v(team, diffAvg) AS
  WITH team_allstars AS (
    SELECT allstarfull.teamid, salaries.salary
    FROM allstarfull inner join salaries on allstarfull.playerID = salaries.playerID
    WHERE allstarfull.yearid = 2016 AND salaries.yearid = 2016
  )
SELECT team_allstars.teamid, max(team_allstars.salary) - min(team_allstars.salary)
FROM team_allstars
GROUP BY team_allstars.teamid
;

