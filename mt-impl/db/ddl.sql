CREATE TABLE IF NOT EXISTS USER_DEF_CAT (
	ID		INT(20) NOT NULL AUTO_INCREMENT,
	CAT_ID	INT(20) NOT NULL,
	USER_ID	int(20) NOT NULL,
	
	PRIMARY KEY(ID)
)ENGINE=InnoDB;