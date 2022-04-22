CREATE TABLE employees
(
    employee_id    NUMBER(6) PRIMARY KEY,
    first_name     VARCHAR2(20) NOT NULL,
    last_name      VARCHAR2(25) NOT NULL,
    email          VARCHAR2(25) UNIQUE NOT NULL,
    phone_number   VARCHAR2(20) UNIQUE NOT NULL,
    salary         NUMBER(8, 2),
    department_id  NUMBER(4),
    CONSTRAINT emp_salary_min CHECK (salary > 0),
    CONSTRAINT dep_fk FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE SEQUENCE employees_seq START WITH 1;

ALTER TABLE employees
    MODIFY employee_id DEFAULT employees_seq.nextval;


