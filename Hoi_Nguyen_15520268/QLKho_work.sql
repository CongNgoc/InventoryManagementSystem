--create tables

CREATE TABLE USERS(
 USER_ID NUMBER(5) NOT NULL PRIMARY KEY,
 FIRST_NAME VARCHAR2(25) NOT NULL,
 LAST_NAME VARCHAR2(25) NOT NULL,
 BIRTHDAY DATE NOT NULL,
 USER_NAME VARCHAR2(30) NOT NULL,
 PASSWORD VARCHAR2(30) NOT NULL,
 EMAIL VARCHAR2(50) UNIQUE, 
 PHONE VARCHAR2(12) UNIQUE,
 ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
 CREATE_DATE DATE NOT NULL,
 UPDATE_DATE DATE NOT NULL
)

CREATE TABLE USER_ROLE(
  USER_ID NUMBER(5) NOT NULL,
  ROLE_ID NUMBER(5) NOT NULL ,
  ACTIVE_FLAG NUMBER(1) DEFAULT 1 CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)

CREATE TABLE ROLE(
  ROLE_ID NUMBER(5) NOT NULL PRIMARY KEY,
  ROLE_NAME VARCHAR(30) NOT NULL,
  DESCRIPTION VARCHAR(200),
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)
  
CREATE TABLE MENU(
  MENU_ID NUMBER(5) NOT NULL PRIMARY KEY,
  MENU_NAME VARCHAR2(40) NOT NULL,
  PARENT_ID NUMBER(5),
  URL VARCHAR2(100) NOT NULL,
  MENU_INDEX NUMBER(10),
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)
  
CREATE TABLE AUTH(
  ROLE_ID NUMBER(5) NOT NULL,
  MENU_ID NUMBER(5) NOT NULL,
  PERMISSION NUMBER(1)CHECK(PERMISSION IN(0,1)) NOT NULL,
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)


CREATE TABLE PRODUCT_INFO(
  PRODUCT_INFO_ID NUMBER(5) NOT NULL PRIMARY KEY,
  CATEGORY_ID NUMBER(5) NOT NULL,
  CODE VARCHAR2(30) NOT NULL,
  NAME VARCHAR2(50) NOT NULL,
  DESCRIPTION VARCHAR2(200),
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)

CREATE TABLE CATEGORY(
  CATEGORY_ID NUMBER(5) NOT NULL PRIMARY KEY,
  NAME VARCHAR2(50) NOT NULL,
  DESCRIPTION VARCHAR2(200),
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)

CREATE TABLE INVOICE_DETAIL(
  PRODUCT_ID NUMBER(5) NOT NULL,
  INVOICE_ID NUMBER(5) NOT NULL,
  QUANITY NUMBER(5) NOT NULL,
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)

CREATE TABLE INVOICE(
  INVOICE_ID NUMBER(5) NOT NULL PRIMARY KEY,
  TYPE NUMBER(1) CHECK(TYPE IN(1,2)) NOT NULL,
  USER_ID NUMBER(5) NOT NULL,
  PRICE NUMBER (15) NOT NULL,
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE NOT NULL,
  UPDATE_DATE DATE NOT NULL
)

CREATE TABLE PRODUCT_IN_STOCK(
  PRODUCT_STOCK_ID NUMBER(5) NOT NULL PRIMARY KEY,
  PRODUCT_ID  NUMBER(5) NOT NULL,
  QUANITY NUMBER(11) NOT NULL,
  ACTIVE_FLAG NUMBER(1) DEFAULT 1  CHECK(ACTIVE_FLAG IN(0,1)) NOT NULL,
  CREATE_DATE DATE DEFAULT SYSDATE NOT NULL,
  UPDATE_DATE DATE DEFAULT SYSDATE NOT NULL
)

--create sequence
CREATE SEQUENCE USERS_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE ROLE_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE MENU_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE PRODUCT_INFO_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE CATEGORY_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE INVOICE_SEQ
   INCREMENT BY 1 
   START WITH 1;
   
CREATE SEQUENCE PRODUCT_IN_STOCK_SEQ
   INCREMENT BY 1 
   START WITH 1;

-- create foreign key

PROMPT Creating Foreign Keys on 'USER_ROLE'
ALTER TABLE USER_ROLE 
ADD CONSTRAINT
 USER_ROLE_USERS_FK FOREIGN KEY 
  (USER_ID) REFERENCES USERS(USER_ID)
ADD CONSTRAINT
 USER_ROLE_ROLE_FK FOREIGN KEY 
  (ROLE_ID) REFERENCES ROLE(ROLE_ID)
/

PROMPT Creating Foreign Keys on 'AUTH'
ALTER TABLE AUTH
ADD CONSTRAINT
 AUTH_ROLE_FK FOREIGN KEY 
  (MENU_ID) REFERENCES MENU(MENU_ID)
ADD CONSTRAINT
 AUTH_MENU_FK FOREIGN KEY 
  (ROLE_ID) REFERENCES ROLE(ROLE_ID)
/

PROMPT Creating Foreign Keys on 'INVOICE_DETAIL'
ALTER TABLE INVOICE_DETAIL
ADD CONSTRAINT
 IN_DETAIL_PRODUCT_FK FOREIGN KEY 
  (PRODUCT_ID) REFERENCES PRODUCT_INFO(PRODUCT_INFO_ID)
ADD CONSTRAINT
 IN_DETAIL_INVOICE_FK FOREIGN KEY 
  (INVOICE_ID) REFERENCES INVOICE(INVOICE_ID)
/

PROMPT Creating Foreign Keys on 'PRODUCT_INFO'
ALTER TABLE PRODUCT_INFO ADD CONSTRAINT
 PRODUCT_CATE_FK FOREIGN KEY 
  (CATEGORY_ID) REFERENCES CATEGORY(CATEGORY_ID)
/


PROMPT Creating Foreign Keys on 'PRODUCT_IN_STOCK'
ALTER TABLE PRODUCT_IN_STOCK ADD CONSTRAINT
 PRODUCT_STOCK_FK FOREIGN KEY 
  (PRODUCT_ID) REFERENCES PRODUCT_INFO(PRODUCT_INFO_ID)
/

PROMPT Creating Foreign Keys on 'INVOICE'
ALTER TABLE INVOICE ADD CONSTRAINT
 INVOICE_USER_FK FOREIGN KEY 
  (USER_ID) REFERENCES USERS(USER_ID)
/

PROMPT Creating Foreign Keys on 'MENU'
ALTER TABLE MENU ADD CONSTRAINT
 MENU_PARENT_FK FOREIGN KEY 
  (PARENT_ID) REFERENCES MENU(MENU_ID)
/
--
--09.05.2020 UPDATE by Hoi Nguyen
-- add primary key for auth, invoice_detail, user_role


alter table auth
add auth_id NUMBER(5) not null primary key;

alter table invoice_detail
add in_de_id NUMBER(5) not null primary key;

alter table user_role
add user_role_id NUMBER(5) not null primary key;


--10.05.2020 UPDATE by Hoi Nguyen
-- create sequence for auth, invoice_detail, user_role

  CREATE SEQUENCE AUTH_SEQ
   START WITH 1
  INCREMENT BY 1 ;
   
  CREATE SEQUENCE IN_DE_SEQ
   START WITH 1
   INCREMENT BY 1 ;
   
   CREATE SEQUENCE USER_ROLE_SEQ
   START WITH 1
   INCREMENT BY 1 ;

--
--02.06.2020 MODIFIED BY Cong Nguyen
-- Add Order_index column To MENU TABLE
 
alter table MENU
add order_index NUMBER(5);


-- MODIFY CREATE_DATE DEFAULT VALUE IS SYSDATE
ALTER TABLE CATEGORY
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE AUTH
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);
  
ALTER TABLE HISTORY
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE INVOICE
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE INVOICE_DETAIL
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE PRODUCT_INFO
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE ROLE
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE USER_ROLE
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);

ALTER TABLE USERS
MODIFY (CREATE_DATE DATE DEFAULT SYSDATE,
UPDATE_DATE DATE DEFAULT SYSDATE);


--SET SEQUECE TO ID COLMUN
ALTER TABLE USERS
MODIFY USER_ID NUMBER(5) DEFAULT USERS_SEQ.NEXTVAL;

ALTER TABLE AUTH
MODIFY AUTH_ID NUMBER(5) DEFAULT AUTH_SEQ.NEXTVAL;

ALTER TABLE MENU
MODIFY MENU_ID NUMBER(5) DEFAULT MENU_SEQ.NEXTVAL;

ALTER TABLE CATEGORY
MODIFY CATEGORY_ID NUMBER(5) DEFAULT CATEGORY_SEQ.NEXTVAL

CREATE SEQUENCE HISTORY_SEQ
START WITH 1
INCREMENT BY 1;

ALTER TABLE HISTORY
MODIFY HISTORY_ID NUMBER(5) DEFAULT HISTORY_SEQ.NEXTVAL;

ALTER TABLE INVOICE
MODIFY INVOICE_ID NUMBER(5) DEFAULT INVOICE_SEQ.NEXTVAL;

ALTER TABLE INVOICE_DETAIL
MODIFY IN_DE_ID NUMBER(5) DEFAULT IN_DE_SEQ.NEXTVAL;

ALTER TABLE PRODUCT_IN_STOCK
MODIFY PRODUCT_STOCK_ID NUMBER(5) DEFAULT PRODUCT_IN_STOCK_SEQ.NEXTVAL;

ALTER TABLE PRODUCT_INFO
MODIFY PRODUCT_INFO_ID NUMBER(5) DEFAULT PRODUCT_INFO_SEQ.NEXTVAL;

ALTER TABLE ROLE
MODIFY ROLE_ID NUMBER(5) DEFAULT ROLE_SEQ.NEXTVAL;

ALTER TABLE USER_ROLE
MODIFY USER_ROLE_ID NUMBER(5) DEFAULT USER_ROLE_SEQ.NEXTVAL;

--INSERT VALUE TO TEST MODIFY DEFAUT VALUE
--MENU TABLE
SELECT * FROM MENU;
insert into menu(MENU_ID, url,menu_name) values (0,'/','Root');
insert into menu(parent_id,url,menu_name,order_index) values (0,'/product','S?n ph?m',1);
insert into menu(parent_id,url,menu_name,order_index) values (0,'/stock','Kho',2);
insert into menu(MENU_ID, parent_id,url,menu_name,order_index) values (3, 0,'/management','Quản lý',3);

insert into menu(MENU_ID, parent_id,url,menu_name,order_index) values (4, 1,'/product-info/list','Danh sách sản phẩm',2);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/list','Danh sách category',1);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/edit','Sửa',-1);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/view','Xem',-1);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/add','Thêm mới',-1);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/save','Lưu',-1);
insert into menu(parent_id,url,menu_name,order_index) values (1,'/category/delete','Xoá',-1);

insert into menu(parent_id,url,menu_name,order_index) values (2,'/goods-recept/list','Danh sách nhập kho',1);
insert into menu(parent_id,url,menu_name,order_index) values (2,'/goods-issue/list','Danh sách xuất kho',2);
insert into menu(parent_id,url,menu_name,order_index) values (2,'/product-in-stock/list','Sản phẩm trong kho',3);
insert into menu(parent_id,url,menu_name,order_index) values (2,'/history','Lịch sử kho',4);

insert into menu(parent_id,url,menu_name,order_index) values (3,'/user/list','Danh sách user',1);
insert into menu(parent_id,url,menu_name,order_index) values (3,'/menu/list','Danh sách menu',1);
insert into menu(parent_id,url,menu_name,order_index) values (3,'/role/list','Danh sách quyền',1);

--ROLE TABLE
INSERT INTO role(ROLE_NAME,DESCRIPTION) VALUES ('admin','Admin of system');
INSERT INTO role(ROLE_NAME,DESCRIPTION) VALUES  ('staff','Staff of system');

--AUTH TABLE
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,1,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,2,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,3,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,4,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,5,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,6,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,7,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,8,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,9,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,10,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,11,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,12,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,13,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,14,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,15,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,16,1);
INSERT INTO auth(ROLE_ID,MENU_ID,PERMISSION) VALUES (1,17,1);

--add column code to category table
ALTER TABLE Category
ADD code VARCHAR2(40);
