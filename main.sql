create family_member(
	id int not null auto_increment,
	family_member_id int,
	family_member varchar(255),
	primary key (id),
	foreign key(family_member_id) references main.id
); 
create experimental_tissue(
	id int not null auto_increment,
	experimental_tissue_id int,
	experimental_tissue varchar(255),
	primary key (id),
	foreign key(experimental_tissue_id) references main.id
); 
create main(
	id int not null auto_increment,
	gene varchar(255),
	pmid varchar(255),
	species varchar(255),
	experiment varchar(255),
	replicates varchar(255),
	control varchar(255),
	quality_fold_change varchar(255),
	primary key (id)
); 

