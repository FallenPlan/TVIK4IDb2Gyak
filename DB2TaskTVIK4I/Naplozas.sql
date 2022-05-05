create or replace trigger feltolt after insert on phone for each row
BEGIN
  insert into Naplozas values('Beszúrás', :new.ID||'_'||:new.TYPE||'_'||:new.COLOR||'_'||:new.RELEASE||'_'||:new.PRICE||'_'||:new.CONDITION, sysdate);
END;