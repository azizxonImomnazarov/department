DROP FUNCTION IF EXISTS add_task;
create function add_task(t_description VARCHAR,t_is_end boolean,t_present_date DATE ,t_employee_id)
   returns void 
   language plpgsql
  as
$$
declare 
-- variable declaration
begin
INSERT INTO snp_task(task_description,task_is_end,task_present_date,employee_id) values (t_description,t_is_end,t_present_date,t_employee_id);
end;
$$