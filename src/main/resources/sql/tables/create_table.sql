DROP TABLE IF EXISTS snp_employee;
DROP TABLE IF EXISTS snp_department;
DROP TABLE IF EXISTS snp_task;


CREATE TABLE snp_department (  
    department_id SERIAL NOT NULL primary key,
    department_name VARCHAR(100) NOT NULL 
);
CREATE TABLE snp_employee (  
    employee_id SERIAL NOT NULL primary key,
    employee_name VARCHAR(100) NOT NULL ,
    employee_sur_name VARCHAR (100) NOT NULL ,
    department_id INTEGER NOT NULL ,
    FOREIGN KEY (department_id) REFERENCES snp_department(department_id)
);


CREATE TABLE snp_task (  
    task_id SERIAL NOT NULL primary key,
    task_description text NOT NULL ,
    task_is_end boolean DEFAULT false,
    task_present_date DATE NOT NULL ,
    task_completed_date DATE ,
    employee_id INTEGER NOT NULL ,
    FOREIGN KEY (employee_id) REFERENCES snp_employee(employee_id)
);