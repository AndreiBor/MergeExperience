-- create database kozlov_db;
-- create schema public;

create table public.modules(
   id serial primary key,
   name_of_module varchar(20) not null
)

create table public.themes(
   id serial primary key,
   name_of_theme varchar(50) not null,
   id_module int references public.modules(id)
)

create table public.links(
   id serial primary key,
   description varchar(40) not null,
   link varchar(1000) not null,
   id_theme int references public.themes(id)
)
insert into public.modules (name_of_module)
values ('MODULE 1'),('MODULE 2'),('MODULE 3'),('MODULE 4')

insert into public.themes (name_of_theme,id_module)
values ('Jakarta',1),
('Apache Maven',1),
('Apache Tomcat',1),
('HttpServlet',1),
('GIT',1),
('SQL',2),
('Driver JDBC',2),
('Pattern DAO',2),
('Cookie',2),
('JSP and JSTL',2),
('HIBERNATE',3),
('SPRING',4)

insert into public.links (description,link,id_theme)
values ('Download Jakarta','https://jakarta.apache.org/site/downloads/index.html',1),
('Download Maven','https://maven.apache.org/download.cgi',2),
('Manual Maven','https://maven.apache.org/what-is-maven.html',2),
('Download Apache Tomcat','https://tomcat.apache.org/download-90.cgi',3),
('How to use','https://www.baeldung.com/tomcat',3),
('Manual here','https://metanit.com/java/javaee/4.1.php',4),
('Download Git','https://git-scm.com/downloads',5),
('About SQL','https://www.w3schools.com/sql/sql_intro.asp',6),
('About JDBC','https://habr.com/ru/post/326614/',7),
('About Dao','https://ru.wikipedia.org/wiki/Data_Access_Object',8),
('Manual here','https://metanit.com/java/javaee/4.9.php',9),
('About JSP here','https://metanit.com/java/javaee/3.1.php',10),
('About JSTL here','https://metanit.com/java/javaee/3.11.php',10),
('About EL here','https://metanit.com/java/javaee/3.9.php',10),
('Download Hibernate','https://hibernate.org/orm/releases/',11),
('Manual here','https://hibernate.org/orm/',11),
('More useful info here','https://javarush.com/groups/posts/hibernate-java',11),
('Spring initializr','https://start.spring.io/',12),
('Manual here','https://spring.io/',12),
('More useful info here','https://javarush.com/groups/posts/spring-framework-java-1',12)

