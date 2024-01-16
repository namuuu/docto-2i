create table Doctor (id integer not null auto_increment, birthdate datetime not null, firstname varchar(255) not null, name varchar(255) not null, address varchar(255), login varchar(255) not null, password varchar(255) not null, health_professional_number integer, intern bit, primary key (id)) engine=InnoDB;
create table Manager (id integer not null auto_increment, birthdate datetime not null, firstname varchar(255) not null, name varchar(255) not null, address varchar(255), login varchar(255) not null, password varchar(255) not null, primary key (id)) engine=InnoDB;
create table Patient (id integer not null auto_increment, birthdate datetime not null, firstname varchar(255) not null, name varchar(255) not null, identifier integer, request_type bit, primary key (id)) engine=InnoDB;
create table Salle (numero integer not null, name varchar(255), primary key (numero)) engine=InnoDB;
alter table Doctor add constraint UK_nd2m8bnxompaejd3mcvj25cl unique (login);
alter table Manager add constraint UK_n4bf8j6n5flngxiocc64tr0vy unique (login);
