create or replace trigger modositas_kontrollalas
before update on phone for each row
declare
begin
if :new.release not between to_date('1970.01.01','yyyy.MM.dd') and
sysdate then
dbms_output.put_line('Nem helyes dátum');
:new.release:=:old.release;
end if;
end;