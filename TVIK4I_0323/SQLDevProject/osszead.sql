DECLARE
    x NUMBER(5);
    y NUMBER(5);
    s NUMBER(5);
BEGIN
    x := 2;
    y := 3;
    s := x+y;
    dbms_output.put_line(s);
END;