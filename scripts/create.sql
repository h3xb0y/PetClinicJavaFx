CREATE DATABASE PetClinic; 
USE PetClinic;




CREATE TABLE user(
  user_login VARCHAR(50) NOT NULL,
  user_password VARCHAR(50) NOT NULL,
  user_role VARCHAR(50) NOT NULL,
  PRIMARY KEY (user_login) 
);

CREATE TABLE owner(
  owner_id INT AUTO_INCREMENT NOT NULL,
  owner_name VARCHAR(50) NOT NULL,
  owner_lastname VARCHAR(50) NOT NULL,
  owner_num VARCHAR(50) NOT NULL,
  owner_gender VARCHAR(50) NOT NULL,
  owner_status BOOLEAN NOT NULL,
  user_login   VARCHAR(50) NOT NULL,
  PRIMARY KEY (owner_id),
  FOREIGN KEY (user_login) REFERENCES user(user_login)
);

CREATE TABLE specialization(
  spec_id INT AUTO_INCREMENT NOT NULL,
  spec_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (spec_id) 
);

CREATE TABLE kind(
  kind_id INT AUTO_INCREMENT NOT NULL,
  kind_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (kind_id) 
);


CREATE TABLE animal(
  animal_id INT AUTO_INCREMENT NOT NULL,
  animal_name VARCHAR(50) NOT NULL,
  animal_age INT NOT NULL,
  animal_weight INT NOT NULL,
  animal_status BOOLEAN NOT NULL,
  kind_id INT NOT NULL,
  owner_id INT NOT NULL,
  PRIMARY KEY (animal_id),
  FOREIGN KEY (owner_id) REFERENCES owner(owner_id),
  FOREIGN KEY (kind_id) REFERENCES kind(kind_id)
);

CREATE TABLE cabinet(
  cabinet_id INT AUTO_INCREMENT NOT NULL,
  cabinet_num INT NOT NULL,
  cabinet_type VARCHAR(50) NOT NULL,
  cabinet_level INT NOT NULL,
  PRIMARY KEY (cabinet_id)
);

CREATE TABLE doctor(
  doctor_id INT AUTO_INCREMENT NOT NULL,
  doctor_name VARCHAR(50) NOT NULL,
  doctor_lastname VARCHAR(50) NOT NULL,
  doctor_num VARCHAR(50) NOT NULL,
  doctor_gender VARCHAR(50) NOT NULL,
  user_login   VARCHAR(50) NOT NULL,
  cabinet_id INT NOT NULL,
  spec_id INT NOT NULL,
  FOREIGN KEY (spec_id) REFERENCES specialization(spec_id),
  FOREIGN KEY (cabinet_id) REFERENCES cabinet(cabinet_id),
  FOREIGN KEY (user_login) REFERENCES user(user_login),
  PRIMARY KEY (doctor_id) 
);

CREATE TABLE diagnose(
  diagnose_id INT AUTO_INCREMENT NOT NULL,
  diagnose_name VARCHAR(50) NOT NULL,
  diagnose_desc VARCHAR(50) NOT NULL,
  animal_id INT NOT NULL,
  PRIMARY KEY (diagnose_id),
  FOREIGN KEY (animal_id) REFERENCES animal(animal_id)
);

CREATE TABLE ticket(
  ticket_id INT AUTO_INCREMENT NOT NULL,
  ticket_date DATE NOT NULL,
  ticket_time TIME NOT NULL,
  ticket_status BOOLEAN NOT NULL,
  diagnose_id INT,
  owner_id INT NOT NULL,
  animal_id INT NOT NULL,
  doctor_id INT NOT NULL,
  PRIMARY KEY (ticket_id),
  FOREIGN KEY (animal_id) REFERENCES animal(animal_id),
  FOREIGN KEY (owner_id) REFERENCES owner(owner_id),
  FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id)
);