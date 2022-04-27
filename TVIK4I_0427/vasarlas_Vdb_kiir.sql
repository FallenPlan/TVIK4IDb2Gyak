create or replace procedure kiir as x number(8);
BEGIN
  select count(sorszam) into x from Vasarlas;
  dbms_output.put_line(x);
END;