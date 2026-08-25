CREATE TABLE IF NOT EXISTS assignments (
  id BIGSERIAL PRIMARY KEY,
  description VARCHAR(255),
  due_date DATE,
  status VARCHAR(255),
  title VARCHAR(255)
);

--Test data
INSERT INTO assignments (title, description, due_date, status) VALUES ('COSC349 Assignment 1', 'Virtualisation and portable deployment', '2026-09-08', 'IN_PROGRESS');
INSERT INTO assignments (title, description, due_date, status) VALUES ('COSC349 Assignment 2', 'Distributed systems project', '2026-10-15', 'PENDING');
INSERT INTO assignments (title, description, due_date, status) VALUES ('COSC249 Essay', 'Literature review draft', '2026-09-20', 'PENDING');
INSERT INTO assignments (title, description, due_date, status) VALUES ('Test Assignment', 'Testing the POST endpoint', '2026-09-01', 'PENDING');
INSERT INTO assignments (title, description, due_date, status) VALUES ('COSC345 Software Engineering Project', 'Software engineering project', '2026-10-15', 'PENDING');