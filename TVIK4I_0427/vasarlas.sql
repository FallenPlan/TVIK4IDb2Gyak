create table Vasarlas(
SORSZAM number(38, 0) not null,
IDOPONT timestamp(6) default CURRENT_TIMESTAMP,
TKOD char(3) not null,
DARAB number(38, 0),
VID char(3) not null
);