create table usuario(
	nome varchar(50),
	senha varchar(50),
	primary key(nome)
);
create sequence id_email;

create table email(
	id int,
	corpo varchar(255),
	nome_remetente varchar(50),
	nome_destinatario varchar(50),
	data varchar(50),
	hora varchar(50),
	primary key(id),
	foreign key(nome_remetente) references usuario,
	foreign key(nome_destinatario) references usuario
);
