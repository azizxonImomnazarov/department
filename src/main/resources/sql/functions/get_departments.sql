DROP TABLE IF EXISTS get_department;

CREATE FUNCTION get_department() 
    RETURNS TABLE (
        dep_id int,
        dep_name VARCHAR,
        emp_id int,
        emp_name VARCHAR ,
        emp_sur_name VARCHAR ,
        task_id int,
        task_description TEXT ,
        task_is_end boolean,
        task_present_date DATE ,
        task_complete_date DATE 
) 
AS $$
BEGIN
    RETURN QUERY SELECT
        dep.department_id,
        dep.department_name,
        emp.employee_id,
        emp.employee_name,
        emp.employee_sur_name,
        task.task_id,
        task.task_description,
        task.task_is_end,
        task.task_present_date,
        task.task_complete_date
    FROM
        snp_task as task
        INNER JOIN  snp_employee as emp on emp.employee_id = task.employee_id
        INNER JOIN  snp_department as dep on emp.department_id = dep.department_id;
END; $$ 

LANGUAGE 'plpgsql';