create table users(
  id int auto_increment,
  username varchar(32) not null,
  password varchar(128) not null,
  email varchar(32) not null,
  lastpasswordresetdate DATETIME not null,
  PRIMARY KEY (id)
);


create table user_roles(
  id int auto_increment,
  username varchar(32) not null,
  role varchar(32) not null,
  PRIMARY KEY (id)
);

/**
获得结果后用一下语句查看异常记录

select * from userinfo where custname="" and accstatus<>1;


select umid,accstatus from userinfo where needclear=-1;
select umid,accstatus from userinfo where needclear=0;

select umid,accstatus,amount,needclear,custname,custcertname,custcerid,jbrname,jbridnum,custphone from userinfo limit 10;


select umid,accstatus from userinfo where custname="";
 */
