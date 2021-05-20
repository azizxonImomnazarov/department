DROP FUNCTION IF EXISTS add_department;
create function add_department(d_name VARCHAR)
   returns void 
   language plpgsql
  as
$$
declare 
-- variable declaration
begin
INSERT INTO snp_department(department_name) values (d_name);
end;
$$