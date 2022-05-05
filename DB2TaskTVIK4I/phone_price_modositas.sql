create or replace procedure incPrice as
allRows integer;
begin
update phone set price = price+20000;
if sql%notfound then
dbms_output.put_line('Nem frissült egy ár sem');
elsif sql%found then
allRows:=sql%rowcount;
dbms_output.put_line( allRows || ' darab phone frissítve
lett ');
end if;
end;