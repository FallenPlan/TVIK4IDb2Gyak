create or replace trigger modositas after update on phone for each row
BEGIN
  insert into Naplozas values('Módosítás', :old.ID||'_'||:old.TYPE||'_'||:old.COLOR||'_'||:old.RELEASE||'_'||:old.PRICE||'_'||:old.CONDITION
  ||'_'||:new.ID||'_'||:new.TYPE||'_'||:new.COLOR||'_'||:new.RELEASE||'_'||:new.PRICE||'_'||:new.CONDITION, sysdate);
END;