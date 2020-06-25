--th� nh�n vi�n th? 2 ?ang th�m m?t h�a ??n xu?t h�ng c?ng cho m?t h�ng A
--D? li?u ? c?t Quantity t?i th?i ?i?m nh�n vi�n th? nh?t ho�n t?t ch?a ???c c?p nh?p 
--cho nh�n vi�n th? 2 s? d?ng khi?n x�y ra tr??ng h?p Lost update.

--B4
SET TRANSACTION
ISOLATION LEVEL
READ COMMITTED;

--B5
--LAY RA TAT CA PRODUCT_IN_STOCK
SELECT PRODUCT_STOCK_ID,PRODUCT_ID, QUANITY 
FROM PRODUCT_IN_STOCK 
WHERE ACTIVE_FLAG = 1;

--B6
--THEM HOA DON XUAT HANG VOI PRODUCT_ID = 21, QUANTITY = 5
DECLARE 
  v_l_info_gr list_info_goods_receipt := list_info_goods_receipt(
    info_goods_receipt( 21, 5)
  );
  v_type NUMBER; 
  v_user_id NUMBER; 
  v_code VARCHAR2(30);
  v_invoice_id NUMBER;
BEGIN
  v_type := 0;
  v_user_id := 1;
  v_code := 'HD016';
  v_invoice_id := 39;

  add_goods_receipt_invoice(v_l_info_gr, v_invoice_id, v_type, v_user_id, v_code);
END;

--B8
COMMIT;


