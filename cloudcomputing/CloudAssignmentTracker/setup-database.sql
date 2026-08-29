CREATE TABLE IF NOT EXISTS assignments (
  id BIGSERIAL PRIMARY KEY,
  description VARCHAR(255),
  due_date DATE,
  status VARCHAR(255),
  title VARCHAR(255)
);

TRUNCATE TABLE assignments RESTART IDENTITY;

INSERT INTO assignments (title, description, due_date, status) VALUES
  ('COSC343 Assignment 2', 'Warehouse Robots', '2026-09-15', 'IN_PROGRESS'),
  ('INFO304 Assignment 2', 'Logistic Regression', '2026-09-11', 'PENDING'),
  ('COSC349 Assignment 2', 'Cloud Commputing', '2026-10-05', 'PENDING'),
  ('COSC345 Software Engineering Project', 'Software engineering project', '2026-10-15', 'PENDING'),
  ('COSC345 Individual Report', 'Evidence Review Individual Report', '2026-09-18', 'PENDING');