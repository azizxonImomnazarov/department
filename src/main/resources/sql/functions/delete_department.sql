DROP FUNCTION IF EXISTS add_department;
create function add_department(d_name VARCHAR)
   returns void 
   language plpgsql
  as
$$
declare 
-- variable declaration
begin
DELETE  FROM TABLE snp_employee(employee_name,employee_sur_name,department_id) values (e_name,e_sur_name,e_department_id);
end;
$$