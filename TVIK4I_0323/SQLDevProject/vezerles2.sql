DECLARE
    a number(2);
    b number(2);
BEGIN
    a := 10;
    b := 81;
    IF a > b THEN
    dbms_output.put_line(a);
    ELSE
    dbms_output.put_line(b);
END IF;