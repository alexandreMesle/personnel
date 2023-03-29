drop table if exists EMPLOYE;
drop table if exists LIGUE;

create table if not exists EMPLOYE
(
	id_employe int NOT NULL AUTO_INCREMENT,
    nom_employe VARCHAR(200),
    prenom_employe VARCHAR(200),
    password_employe VARCHAR(200),
    mail_employe VARCHAR(200),
    root_employe boolean,
    estadmin_employe boolean,
    dateDepart_employe date,
    dateArrivé_employe date,
    id_ligue int,
    constraint pk_employe primary key(id_employe)
);

create table if not exists LIGUE
(
	id_ligue int NOT NULL AUTO_INCREMENT,
    nom_ligue VARCHAR(200),
    id_employe int,
    constraint pk_ligue primary key(id_ligue)
);