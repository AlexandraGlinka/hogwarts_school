SELECT s.name, s.age, f.name
FROM student AS s
LEFT JOIN faculty AS f on s.faculty_id = f.id
ORDER BY 3, 1;

SELECT s.*
FROM student AS s
JOIN avatar AS a ON s.id = a.student_id;