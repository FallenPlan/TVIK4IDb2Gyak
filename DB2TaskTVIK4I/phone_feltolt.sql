create or replace trigger feltolt after update on phone for each row
BEGIN
  insert into Naplo5 values('Módosítás', :old.VID||'_'||:old.NEV||'_'||:old.CIM||'_'||:new.VID||'_'||:new.NEV||'_'||:new.CIM, sysdate);
END;