DROP TABLE IF EXISTS get_department;

CREATE FUNCTION get_department() 
    RETURNS TABLE (
        dep_id int,
        dep_name VARCHAR 
) 
AS $$
BEGIN
    RETURN QUERY SELECT
        department_id,
        department_name
    FROM
        snp_department;
END; $$ 

LANGUAGE 'plpgsql';