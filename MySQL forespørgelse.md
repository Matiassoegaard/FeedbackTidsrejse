Create table kunder(
id int primary key auto_increment not null,
navn varchar(50) not null,
email varchar(50) not null);

create table tidsmaskine(
id int primary key auto_increment not null,
navn varchar(50) not null,
kapacitet varchar(50) not null,
status varchar(50) not null);

create table tidsperiode(
id int primary key auto_increment not null,
navn varchar(50) not null,
beskrivelse varchar(50) not null);

create table guide(
id int primary key auto_increment not null,
navn varchar(50) not null,
specialitet varchar(50) not null);

create table booking(
id int primary key auto_increment not null,
kundeID int,
tidsmaskineID int,
tidsperiodeID int,
guideID int,
foreign key (kundeID) references kunder(id),
foreign key (tidsmaskineID) references tidsmaskine(id),
foreign key (tidsperiodeID) references tidsperiode(id),
foreign key (guideID) references guide(id));

insert into guide (navn, specialitet)
values ('Professor Quark','Den der ved mest om små ting');
insert into guide (navn, specialitet)
values ('Doctor Who','Good with time');
insert into guide (navn, specialitet)
values ('Professor Paradox','Futuristisk navigation');

insert into tidsperiode (navn, beskrivelse)
values ('Dinosaurernes æra','Der er mange dinosaurer over alt');
insert into tidsperiode (navn, beskrivelse)
values ('Middelalderen','Se hvordan forfadrende levede');
insert into tidsperiode (navn, beskrivelse)
values ('Fremtiden','Se hvordan tiden kommer til at se ud');

insert into tidsmaskine (navn, kapacitet, status)
values ('Tartis',1,'Ledig');
insert into tidsmaskine (navn, kapacitet, status)
values ('Docs bil',2,'Ledig');
insert into tidsmaskine (navn, kapacitet, status)
values ('Ormehul',3,'Ledig');

insert into kunder(navn, email)
values ('Karl','karlsen@karl.karl');
insert into kunder(navn, email)
values ('Bob','Bobsen@bobobobo.com');
insert into kunder(navn, email)
values ('Torben','Torbsen@Kosoade.com');
