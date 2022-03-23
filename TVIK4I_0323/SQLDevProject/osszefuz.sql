DECLARE 
    firstName VARCHAR(100);
    lastName VARCHAR(100);

BEGIN
    firstName := 'Bence';
    lastName := 'Csomor';
    dbms_output.put_line(firstName + lastName);
END;