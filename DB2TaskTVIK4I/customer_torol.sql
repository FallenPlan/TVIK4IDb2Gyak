create or replace procedure customerDel(iddel int) as
begin
    delete from customer where identityN=iddel;
end;