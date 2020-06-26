SET SERVEROUTPUT ON;
--MODIFY DATE: 22/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE TRIGGER TO UPDATE PRICE WHEN INSERT INVOICE DETAIL
CREATE OR REPLACE TRIGGER update_price_on_invoice
AFTER INSERT OR UPDATE OR DELETE ON INVOICE_DETAIL
FOR EACH ROW
DECLARE
  price_product PRODUCT_INFO.PRICE%TYPE;
BEGIN
  --GET CURRENT PRICE FROM PRODUCT 
  IF(INSERTING) THEN
    SELECT PRICE INTO price_product
    FROM PRODUCT_INFO PI
    WHERE PI.PRODUCT_INFO_ID = :NEW.PRODUCT_ID;
    
    UPDATE INVOICE 
    SET INVOICE.PRICE = PRICE + price_product*:NEW.QUANITY
    WHERE INVOICE_ID = :NEW.INVOICE_ID;
    
  END IF;
  IF(UPDATING) THEN
    SELECT PRICE INTO price_product
    FROM PRODUCT_INFO PI
    WHERE PI.PRODUCT_INFO_ID = :NEW.PRODUCT_ID;
  
    UPDATE INVOICE 
    SET INVOICE.PRICE = PRICE + price_product*:NEW.QUANITY
    WHERE INVOICE_ID = :NEW.INVOICE_ID;
    
    UPDATE INVOICE 
    SET INVOICE.PRICE = PRICE - price_product*:OLD.QUANITY
    WHERE INVOICE_ID = :OLD.INVOICE_ID;
  END IF;
  IF(DELETING) THEN
    SELECT PRICE INTO price_product
    FROM PRODUCT_INFO PI
    WHERE PI.PRODUCT_INFO_ID = :OLD.PRODUCT_ID;
    
    UPDATE INVOICE 
    SET INVOICE.PRICE = PRICE - price_product*:OLD.QUANITY
    WHERE INVOICE_ID = :OLD.INVOICE_ID;
  END IF;
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND EXCEPTION!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');

END;


--INSERT INTO INVOICE
INSERT INTO INVOICE(INVOICE_ID,"QLKHO_ADMIN"."INVOICE"."TYPE", USER_ID, PRICE) VALUES(1,2, 1, 0);
--INSERT INTO INVOICE_DETAIL
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 1, 3);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 2, 1);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 1, 1);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 2, 1);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 2, 1);
INSERT INTO INVOICE_DETAIL(INVOICE_ID, PRODUCT_ID, QUANITY) VALUES(1, 2, 1);

SELECT * FROM INVOICE;
SELECT * FROM INVOICE_DETAIL;
SELECT * FROM PRODUCT;

--MODIFY DATE: 22/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CREATE TRIGGER TO CHECK QUANITY WHEN INSERT INVOICE DETAIL(EXPORT: INVOICE.TYPE = 2)
CREATE OR REPLACE TRIGGER CHECK_QUANITY_IN_STOCK
BEFORE INSERT ON PRODUCT_IN_STOCK
FOR EACH ROW
BEGIN
  IF :NEW.QUANITY > 150 THEN
    raise_application_error(-20001,'The quantity of goods in stock is not > 150.');
  END IF;
  
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('NO_DATA_FOUND EXCEPTION!');
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;

--TEST DATA
INSERT INTO PRODUCT_IN_STOCK(PRODUCT_ID, QUANITY) VALUES (1, 153);
SELECT * FROM PRODUCT_IN_STOCK;



--MODIFY DATE: 23/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: CHECK EMAIL VALIDATION
CREATE OR REPLACE TRIGGER validator_email
BEFORE INSERT OR UPDATE ON USERS
FOR EACH ROW
BEGIN
  IF (REGEXP_LIKE (:NEW.EMAIL , '^[A-Za-z]+[A-Za-z0-9.]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,4}$')) THEN
    DBMS_OUTPUT.PUT('Email validate success!');
  ELSE
    raise_application_error(-20001,'Email incorrect!');
  END IF;
  
END;

INSERT INTO USERS(FIRST_NAME, LAST_NAME, BIRTHDAY,USER_NAME, PASSWORD, EMAIL) 
VALUES('Huyen','Nguyen',SYSDATE,'employee01', 'pass01', 'employee@gmail.com');

--MODIFY DATE: 25/06/2020
--MODIFIED BY: NGUYEN NGOC CONG
--DESCRIPTION: WHEN ADD INVOICE_DETAIL THEN UPDATE QUANTITY ON PRODUCT_IN_STOCK
CREATE OR REPLACE TRIGGER update_quantity
BEFORE INSERT ON INVOICE_DETAIL
FOR EACH ROW
DECLARE 
  v_type INVOICE.TYPE%TYPE;
  v_product_id PRODUCT_IN_STOCK.PRODUCT_ID%TYPE;
BEGIN
  SELECT TYPE INTO v_type
  FROM INVOICE
  WHERE INVOICE_ID = :NEW.INVOICE_ID AND ACTIVE_FLAG = 1;
  
  SELECT PRODUCT_ID INTO v_product_id
  FROM PRODUCT_IN_STOCK
  WHERE PRODUCT_ID = :NEW.PRODUCT_ID;
  
  IF v_type = 1 THEN
    --NHAP HANG
    UPDATE PRODUCT_IN_STOCK
    SET QUANITY = QUANITY + :NEW.QUANITY
    WHERE PRODUCT_ID = :NEW.PRODUCT_ID AND ACTIVE_FLAG = 1; 
  ELSE
    --XUAT HANG
    UPDATE PRODUCT_IN_STOCK
    SET QUANITY = QUANITY - :NEW.QUANITY 
    WHERE PRODUCT_ID = :NEW.PRODUCT_ID AND ACTIVE_FLAG = 1;
  END IF;
  
  EXCEPTION 
    WHEN NO_DATA_FOUND THEN
      DBMS_OUTPUT.PUT_LINE('INSERT NEW PRODUCT_IN_STOCK!');
      INSERT INTO PRODUCT_IN_STOCK(PRODUCT_ID, QUANITY) VALUES (:NEW.PRODUCT_ID, :NEW.QUANITY);
      
     WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('OTHERS EXCEPTION!');
END;
