create or replace procedure PhoneModositas(pid number, ujPrice
number) as
begin
update phone set price = ujPrice where id = pid;
end;