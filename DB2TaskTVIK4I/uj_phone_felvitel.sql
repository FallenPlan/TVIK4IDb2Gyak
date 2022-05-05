create or replace procedure UjPhone(id number, type char, color char,
release date, price number, condition char) as
begin
insert into phone values(id, type, color, release, price, condition);
end;
