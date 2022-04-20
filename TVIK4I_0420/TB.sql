create or replace trigger TB after insert on Vasarlo for each row
BEGIN
  insert into Naplo5 values('Beszúrás', :new.VID||'_'||:new.NEV||'_'||:new.CIM, sysdate);
END;