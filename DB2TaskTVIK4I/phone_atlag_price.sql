create or replace function GetAvgPrice(typein char) return
char as
x integer;
y integer;
ni char(100):= 'Nem létezik ilyen típus';
begin
select count(*) into y from phone where type = typein;
if y >= 1 then
select avg(price) into x from phone where type = typein;
ni:='Az átlagár: '||x;
end if;
return ni;
end;