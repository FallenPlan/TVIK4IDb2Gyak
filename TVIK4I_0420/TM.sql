create or replace trigger TM after update on Vasarlo for each row
BEGIN
  insert into Naplo5 values('M�dos�t�s', :old.VID||'_'||:old.NEV||'_'||:old.CIM||'_'||:new.VID||'_'||:new.NEV||'_'||:new.CIM, sysdate);
END;