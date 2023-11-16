drop database if exists course_management_system;

CREATE DATABASE course_management_system;
USE course_management_system;

create table accounts (
    username varchar(255) primary key,
    password varchar(255) not null,
    usertype varchar(10) not null
);

-- Demo login data       ("id", "pass", "type")
 -- admin
insert into accounts values("admin", "admin", "Admin");

insert into accounts values
	("KMH", "123", "Teacher"),
	("AR", "123", "Teacher"),
	("RAJ", "123", "Teacher"),
	("KIA", "123", "Teacher");

insert into accounts values
	("B21DCAT013", "123", "Student"),
    ("3", "123", "Student"),
    ("4", "123", "Student"),
    ("5", "123", "Student");


create table student(
	id varchar(255) primary key ,
    name varchar(50) not null,
    address varchar(255) not null,
    phone varchar(10) ,
    email varchar(50) ,
    class varchar(20) ,
    foreign key student(id) references accounts(username)
);

INSERT INTO student VALUES
	("B21DCAT013", "Minh", "456 Elm Street", "9876543210", "minhdeptrai@example.com", "E21CQCN04-B"),
	("3", "Peter Jones", "789 Oak Street", "4567891230", "peter.jones@example.com", "12th grade"),
    ("4", "Mary Johnson", "999 Maple Street", "0987654321", "mary.johnson@example.com", "10th grade"),
    ("5", "David Williams", "654 Birch Street", "7894561230", "david.williams@example.com", "11th grade");



create table teacher (
	id varchar(255) primary key ,
    name varchar(50) not null,
    address varchar(255) not null,
    phone varchar(10) not null,
    email varchar(50) not null,
    department varchar(20) not null,
    foreign key teacher(id) references accounts(username)
);
 
insert into teacher values
	("KMH", "Monirul Hasan", "456 Elm Street", "9876543210", "kmhasan@9876543210.com", "AB"),
	("AR", "Ashiqur Rahman", "789 Oak Street", "4567891230", "ashiq.seu@gmail.com", "BC"),
	("RAJ", "Roksana Akhter Jolly", "999 Maple Street", "0987654321", "roksana.seu@gmail.com", "CD"),
	("KIA", "Kimia Aksir", "654 Birch Street", "7894561230", "kimia.aksir@gmail.com", "AC");
	

create table course (
	id varchar(255) primary key not null,
    name varchar(50) not null,
    credit int not null,
    max_student int not null,
    current_sutdent int not null,
    teacher_id varchar(255)
    
);

insert into course values
	("1", "Introduction to Software Engineering", 3, 30,2, "KMH"),
	("2", "Data Structures and Algorithms", 4, 2,2, "AR"),
	("3", "Operating Systems", 3, 30,2, "AR"),
	("4", "Computer Architecture", 3, 30,2, "RAJ"),
	("5", "Database Systems", 3, 30,2, "KIA");

create table registration (
	id int(10) primary key auto_increment,
	student_id varchar(255),
    course_id varchar(255),
    foreign key (student_id) references student(id),
    foreign key (course_id) references course(id)
);

create table registrationteacher(
	id int(10) primary key auto_increment,
    teacher_id varchar(255),
    course_id varchar(255),
    foreign key (teacher_id) references teacher(id),
    foreign key (course_id) references course(id)
);
