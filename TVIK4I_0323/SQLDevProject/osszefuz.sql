DECLARE 
    firstName VARCHAR(100);
    lastName VARCHAR(100);

BEGIN
    firstName := 'Bence ';
    lastName := 'Csomor';
    DBMS_OUTPUT.PUT_LINE(concat(firstName, lastName));
END;