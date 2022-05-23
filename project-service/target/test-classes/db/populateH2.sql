DROP TABLE IF EXISTS project cascade ;
DROP TABLE IF EXISTS project_tasks cascade ;
DROP SEQUENCE IF EXISTS project_id_sequence;

CREATE SEQUENCE project_id_sequence;

CREATE TABLE project
(
    id          BIGINT DEFAULT project_id_sequence.nextval PRIMARY KEY,
    name        VARCHAR(10)                 NOT NULL,
    status      VARCHAR
);

CREATE TABLE project_tasks
(
    tasks       BIGINT,
    project_id  BIGINT,
    FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE

);



INSERT INTO project (name, status) VALUES
('AIRA-F', NULL),
('AIRA-D', 'ACTIVE');

INSERT INTO project_tasks (tasks, project_id)
VALUES (1, 1),
       (2, 2);


