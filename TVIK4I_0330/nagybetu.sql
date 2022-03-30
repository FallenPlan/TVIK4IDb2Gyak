DECLARE name VARCHAR(100);

BEGIN
    name := 'Csomor Bence';
    dbms_output.put_line(UPPER(name));
    dbms_output.put_line(LOWER(name));
END;