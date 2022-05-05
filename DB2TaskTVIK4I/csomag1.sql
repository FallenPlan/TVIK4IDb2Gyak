create or replace package csomag1 as
procedure UjPhone(pid number, ptype char, color char,
prelease date, price number, condition char);
procedure UjCustomer(identityN number, cname char, address char,
salary number, birthday date, pid number);
function GetName(getidentityN in integer) return varchar;
function GetAvgPrice(typein char) return char;
end;