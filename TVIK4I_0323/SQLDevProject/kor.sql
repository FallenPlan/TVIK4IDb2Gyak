DECLARE
    r number(2);
    area varchar(10);
    pi varchar(5);
    
BEGIN
    r := 12;
    pi := 3.14;
    area := 2*r*pi;
    dbms_output.put_line(area);
END;