create or replace package body csomag1 as
procedure UjPhone(pid number, ptype char, color char,
prelease date, price number, condition char) as
begin
insert into phone values(pid, ptype, color, prelease, price, condition);
end;
procedure UjCustomer(identityN number, cname char, address char,
salary number, birthday date, pid number) as perror exception;
begin
if salary <= 200000 then raise perror;
else
insert into customer values(identityN, cname, address, salary, birthday, pid);
end if;
exception
when perror then
dbms_output.put_line('A fizetés túl alacsony!');
end;
function GetName(getidentityN in integer) return varchar as
x customer.cname%type;
y integer;
begin
select count(*) into y from customer where getidentityN=identityN;
if(y<1) then
x:='Nem létezik a kód';
else
select cname into x from customer where getidentityN = identityN;
end if;
return x;
end;
function GetAvgPrice(typein char) return
char as
x integer;
y integer;
ni char(100):= 'Nem létezik ilyen típus';
begin
select count(*) into y from phone where ptype = typein;
if y >= 1 then
select avg(price) into x from phone where ptype = typein;
ni:='Az átlagár: '||x;
end if;
return ni;
end;
end;