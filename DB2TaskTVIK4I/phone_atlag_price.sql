create or replace function GetAvgPrice(typein char) return
char as
x integer;
y integer;
ni char(100):= 'Nem l�tezik ilyen t�pus';
begin
select count(*) into y from phone where type = typein;
if y >= 1 then
select avg(price) into x from phone where type = typein;
ni:='Az �tlag�r: '||x;
end if;
return ni;
end;