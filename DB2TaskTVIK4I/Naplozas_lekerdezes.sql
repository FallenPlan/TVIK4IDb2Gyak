create or replace procedure kiir as adat varchar2(20);
BEGIN
  select count(sorszam) into adat from Vasarlas;
  dbms_output.put_line(adat);
END;