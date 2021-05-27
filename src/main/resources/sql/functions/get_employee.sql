DROP TABLE IF EXISTS get_employee;

CREATE FUNCTION get_employee() 
    RETURNS TABLE (
        dep_id int,
        dep_name VARCHAR 
) 
AS $$
BEGIN
    RETURN QUERY SELECT
        emp.employee_id,
        emp.employee_name,
        emp.employee_sur_name,
        task.task_id,
        task.task_description,
        task.task_is_end,
        task.task_present_date,
        task.task_completed_date
    FROM
        snp_task as task
        INNER JOIN  snp_employee as emp on emp.employee_id = task.employee_id;
END; $$ 

LANGUAGE 'plpgsql';