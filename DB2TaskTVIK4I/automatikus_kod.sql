create sequence seq1
start with 8;
create trigger autokod before insert on phone for each row
begin
:new.id := seq1.nextval;
end;