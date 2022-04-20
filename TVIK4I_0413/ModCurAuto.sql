create procedure aut_arnov(szinbe in char, ert in int) is
    cursor cur_a is select * from auto where szin=szinbe
    for update of ar;
    a cur_a%rowtype;
begin
    open cur_a;
    loop
        fetch cur_a into a;
        exit when cur_a%notfound;
        update auto set ar=a.ar*(1+ert/100) where current of cur_a;
    end loop;
    close cur_a;
end;