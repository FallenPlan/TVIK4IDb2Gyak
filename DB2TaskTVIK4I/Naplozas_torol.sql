create or replace trigger torles after delete on phone for each row
BEGIN
  insert into Naplozas values('T�rl�s', :old.ID||'_'||:old.TYPE||'_'||:old.COLOR||'_'||:old.RELEASE||'_'||:old.PRICE||'_'||:old.CONDITION, sysdate);
END;