create or replace function getName(getidentityN in integer) return
varchar as
x customer.cname%type;
y integer;
begin
select count(*) into y from customer where getidentityN=identityN;
if(y<1) then
x:='Nem l�tezik a k�d';
else
select cname into x from customer where getidentityN = identityN;
end if;
return x;
end;