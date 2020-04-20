CREATE TABLE COMPANY (
 id INTEGER not null AUTO_INCREMENT,
 company_name VARCHAR(30) not null,
 employees_number INTEGER not null,

-- PRIMARY(id),
-- FOREIGN KEY(id) REFERENCES EMPLOYEE(company_id)
);