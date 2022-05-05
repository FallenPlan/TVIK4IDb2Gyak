create or replace procedure UjCustomer(identityN number, cname char, address char,
salary number, birthday date, pid number) as perror exception;
begin
if salary <= 200000 then
raise perror;
else
insert into customer values(identityN, cname, address, salary, birthday, pid);
end if;
exception
when perror then
dbms_output.put_line('A fizetés túl alacsony!');
end;