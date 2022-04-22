CREATE TABLE department
(
    department_id NUMBER(4) PRIMARY KEY,
    department_name VARCHAR(30) NOT NULL,
    department_location VARCHAR(10) NOT NULL);

CREATE SEQUENCE id_sequence START WITH 1;
ALTER TABLE department MODIFY department_id DEFAULT id_sequence.nextval;