DROP FUNCTION IF EXISTS add_employee;
create function add_employee(e_name VARCHAR,e_sur_name VARCHAR ,e_department_id INTEGER)
   returns void 
   language plpgsql
  as
$$
declare 
-- variable declaration
begin
INSERT INTO snp_employee(employee_name,employee_sur_name,department_id) values (e_name,e_sur_name,e_department_id);
end;
$$