DROP TABLE IF EXISTS get_department;

CREATE FUNCTION get_task() 
    RETURNS TABLE (
        task_id int,
        task_description TEXT ,
        task_is_end boolean,
        task_present_date DATE ,
        task_completed_date DATE 
) 
AS $$
BEGIN
    RETURN QUERY SELECT
        task_id,
        task_description,
        task_is_end,
        task_present_date,
        task_completed_date

    FROM
        snp_task;
END; $$ 

LANGUAGE 'plpgsql';