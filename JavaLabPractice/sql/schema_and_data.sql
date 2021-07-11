create table course
(
    id           serial primary key,
    name         varchar(40),
    start_date   varchar(10),
    end_date     varchar(10),
    teacher_id   integer
);

create table lesson
(
    id serial primary key,
    name varchar(40),
    time varchar(20),
    course integer,
    foreign key (course) references course(id)
);

create table teacher
(
    id         serial primary key,
    first_name varchar(20),
    last_name  varchar(20),
    experience integer,
    foreign key (id) references course (id)
);

create table student
(
    id serial primary key,
    first_name varchar(20),
    last_name  varchar(20),
    group_number integer
);

create table teacher_course
(
    teacher_id integer,
    course_id  integer,
    foreign key (teacher_id) references teacher(id),
    foreign key (course_id) references course(id)
);

create table course_student
(
    student_id integer,
    course_id  integer,
    foreign key (student_id) references student(id),
    foreign key (course_id) references course(id)
);

insert into course(name, start_date, end_date, teacher_id)
values ('Data bases', '29.06.21', '12.07.21', 1);
insert into course(name, start_date, end_date, teacher_id)
values ('NodeJS', '30.06.21', '13.07.21', 1);

insert into student(first_name, last_name, group_number)
values ('Vasya', 'Pupkin', 1);
insert into student(first_name, last_name, group_number)
values ('Valeriy', 'Petrovich', 1);


insert into course_student(student_id, course_id)
values (1, 1);
insert into course_student(student_id, course_id)
values (2, 1);

insert into teacher(first_name, last_name, experience)
values ('Natalya', 'Konopleva', 21);
insert into teacher (first_name, last_name, experience)
values ('Natalya', 'Minina', 5);