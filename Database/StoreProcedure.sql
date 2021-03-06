SET SERVEROUTPUT ON;
--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE STORE PROCEDURE TO GET INFOMATION FROM USER
CREATE OR REPLACE PROCEDURE get_info_user(v_id_user USERS.USER_ID%TYPE)
AS
  --ROWTYPE is a record in table
  v_user USERS%ROWTYPE;
BEGIN
  SELECT * INTO v_user
  FROM USERS
  WHERE USER_ID = v_id_user;
  
  --PRINT USER INFOMATION
  DBMS_OUTPUT.put_line('USER_ID: ' || v_user.USER_ID);
  DBMS_OUTPUT.put_line('FIRST_NAME: ' || v_user.FIRST_NAME);
  DBMS_OUTPUT.put_line('LAST_NAME: ' || v_user.LAST_NAME);
  DBMS_OUTPUT.put_line('BIRTHDAY: ' || v_user.BIRTHDAY);
  DBMS_OUTPUT.put_line('EMAIL: ' || v_user.EMAIL);
  DBMS_OUTPUT.put_line('PHONE: ' || v_user.PHONE);
  DBMS_OUTPUT.put_line('ACTIVE_FLAG: ' || v_user.ACTIVE_FLAG);
  
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND EXCEPTION!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;

--TEST DATA
EXEC get_info_user(2);

--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE FUNCTION TO GET PRODUCT PRICE FROM PRODUCT_INFO_ID 
CREATE OR REPLACE FUNCTION get_product_price(v_product_id PRODUCT_INFO.PRODUCT_INFO_ID%TYPE)
RETURN PRODUCT_INFO.PRICE%TYPE
AS
  v_product_price PRODUCT_INFO.PRICE%TYPE;
BEGIN
  SELECT PRICE INTO v_product_price
  FROM PRODUCT_INFO
  WHERE PRODUCT_INFO_ID = v_product_id AND ACTIVE_FLAG = 1;
  
  RETURN v_product_price;
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND EXCEPTION get_product_price!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;
--EXECUTE
DECLARE
  v_product_price PRODUCT_INFO.PRICE%TYPE;
BEGIN
  v_product_price := get_product_price(1);
  DBMS_OUTPUT.PUT_LINE('v_product_price ' || v_product_price);
END;

--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE FUNCTION TO GET INVOICE PRICE
--IF CHECK PRICE = TOTAL(INVOICE_DETAIL)
CREATE OR REPLACE FUNCTION check_invoice_price(v_invoice_id INVOICE.INVOICE_ID%TYPE)
RETURN NUMBER
AS
  v_invoice_price INVOICE.PRICE%TYPE;
  v_total_invoice_de INVOICE.PRICE%TYPE;
  v_product_price PRODUCT_INFO.PRICE%TYPE;

BEGIN
  SELECT PRICE INTO v_invoice_price
  FROM INVOICE
  WHERE INVOICE.INVOICE_ID = v_invoice_id AND ACTIVE_FLAG = 1;
   
  v_total_invoice_de := 0;
  FOR item IN (SELECT PRICE, QUANITY
              FROM INVOICE_DETAIL IND, PRODUCT_INFO PI 
              WHERE INVOICE_ID = v_invoice_id AND IND.ACTIVE_FLAG = 1 AND IND.PRODUCT_ID = PI.PRODUCT_INFO_ID)
  LOOP
    v_total_invoice_de := v_total_invoice_de + item.PRICE * item.QUANITY;
  END LOOP;
  
  IF v_invoice_price = v_total_invoice_de THEN
    RETURN 1;
  ELSE
    RETURN 0;
  END IF;

  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND EXCEPTION!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;

--TEST
DECLARE
  v_res NUMBER;
BEGIN
  v_res := check_invoice_price(4);
  IF v_res = 1 THEN
    DBMS_OUTPUT.PUT_LINE('TRUE');
  ELSE
    DBMS_OUTPUT.PUT_LINE('FALSE');
  END IF;
END;

--TEST DATA
INSERT INTO INVOICE(INVOICE_ID, TYPE, USER_ID, PRICE) VALUES(4,1, 1, 0);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(4, 1, 3);
SELECT * FROM INVOICE;


--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE PROCEDURE TO INSERT IF CATEGORY IS NULL ELSE UPDATE 
CREATE OR REPLACE PROCEDURE create_category(v_cate_id NUMBER, v_name VARCHAR2,
                        v_description VARCHAR2, v_code VARCHAR2)
AS
  v_id NUMBER;
BEGIN
  SELECT CATEGORY_ID INTO v_id
  FROM CATEGORY
  WHERE CATEGORY_ID = v_cate_id;
  
  IF SQL%ROWCOUNT = 1 THEN
    DBMS_OUTPUT.PUT_LINE('UPDATE CATEGORY!');
    UPDATE CATEGORY
    SET NAME = v_name, DESCRIPTION = v_description, CODE = v_code
    WHERE CATEGORY_ID = v_cate_id;
  END IF;
  
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('INSERT CATEGORY!');
      INSERT INTO CATEGORY(CATEGORY_ID, NAME, DESCRIPTION, CODE) 
      VALUES(v_cate_id, v_name, v_description, v_code);
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;

EXEC create_category(49, 'N??c chanh', 'N??c chanh nguy�n ch?t', 'B2A12');

SELECT * FROM CATEGORY;


--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: GET ALL MENU FOR USER
CREATE OR REPLACE FUNCTION validator_email(v_user_id USERS.USER_ID%TYPE)
RETURN NUMBER
AS
  
BEGIN
  DBMS_OUTPUT.PUT_LINE('INSERT CATEGORY!');
  RETURN 1;
END;

DECLARE
  v_user_id USERS.USER_ID%TYPE;
  v_res NUMBER;
BEGIN
  v_user_id := 1;
  v_res := validator_email(1);
  DBMS_OUTPUT.PUT_LINE(v_res);
END;

--MODIFY DATE: 25/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: GET AMOUNT FROM LIST ORDER
CREATE OR REPLACE TYPE info_goods_receipt AS OBJECT (v_product_id NUMBER(5,0), v_quantity NUMBER(5,0));
CREATE OR REPLACE TYPE list_info_goods_receipt IS TABLE OF info_goods_receipt;

CREATE OR REPLACE FUNCTION get_amount(v_l_info_gr list_info_goods_receipt)
RETURN NUMBER
AS 
  v_amount NUMBER;
  v_price PRODUCT_INFO.PRICE%TYPE;
BEGIN
  v_amount := 0;
  FOR ind IN v_l_info_gr.FIRST..v_l_info_gr.LAST 
  LOOP
    --GET PRICE FROM PRODUCT ID
    SELECT PRICE INTO v_price
    FROM PRODUCT_INFO
    WHERE PRODUCT_INFO_ID = v_l_info_gr(ind).v_product_id;
    v_amount := v_amount + v_l_info_gr(ind).v_quantity * v_price;
  END LOOP;
  
  RETURN v_amount;
  
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;


--MODIFY DATE: 25/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: ADD A GOOD RECEIPT INVOICE TO INVOICE TABLE
CREATE OR REPLACE PROCEDURE add_goods_receipt_invoice(v_l_info_gr list_info_goods_receipt, 
                                    v_invoice_id NUMBER, v_type NUMBER, v_user_id NUMBER, v_code VARCHAR2)
AS 
  v_mount NUMBER;
BEGIN
  --INSERT INTO INVOICE
  v_mount := get_amount(v_l_info_gr);
  INSERT INTO INVOICE(INVOICE_ID, TYPE, USER_ID, PRICE, CODE) VALUES(v_invoice_id, v_type, v_user_id, v_mount, v_code);

  FOR ind IN v_l_info_gr.FIRST..v_l_info_gr.LAST 
  LOOP  
    --INSERT INTO INVOICE DETAIL
    INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) 
    VALUES (v_invoice_id, v_l_info_gr(ind).v_product_id, v_l_info_gr(ind).v_quantity);
    
  END LOOP;
END;

--TEST add_goods_receipt_invoice
DECLARE 
  v_l_info_gr list_info_goods_receipt := list_info_goods_receipt(
    info_goods_receipt( 21, 5),
    info_goods_receipt( 22, 3 )
  );
  v_type NUMBER; 
  v_user_id NUMBER; 
  v_code VARCHAR2(30);
BEGIN
  v_type := 1;
  v_user_id := 1;
  v_code := 'HD002';
  add_goods_receipt_invoice(v_l_info_gr, v_type, v_user_id, v_code);
END;

--MODIFY DATE: 25/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: SLEEP SYSTEM
CREATE OR REPLACE PROCEDURE sleep (in_time number)
AS
  v_now date;
BEGIN
  SELECT SYSDATE
  INTO v_now
  FROM DUAL;
  LOOP
    EXIT WHEN v_now + (in_time * (1/86400)) <= SYSDATE;
  END LOOP;
END;

EXEC sleep(10);

select * from invoice;
select * from invoice_detail;

alter table INVOICE drop constraint SYS_C009758;
alter table INVOICE add constraint SYS_C009758 CHECK(TYPE IN(0, 1)) enable novalidate;


