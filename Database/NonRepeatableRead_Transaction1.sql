--Trong khi th? nh?t ?ang thêm m?t hóa ??n nh?p hàng cho m?t hàng A 
--B1
--LAY RA TAT CA PRODUCT_IN_STOCK
SELECT PRODUCT_STOCK_ID,PRODUCT_ID, QUANITY 
FROM PRODUCT_IN_STOCK 
WHERE ACTIVE_FLAG = 1;


--B2
--THEM HOA DON NHAP HANG VOI PRODUCT_ID = 21, QUANTITY = 10
DECLARE 
  v_l_info_gr list_info_goods_receipt := list_info_goods_receipt(
    info_goods_receipt( 21, 15)
  );
  v_type NUMBER; 
  v_user_id NUMBER; 
  v_code VARCHAR2(30);
  v_invoice_id NUMBER;
BEGIN
  v_type := 1;
  v_user_id := 1;
  v_code := 'HD015';
  v_invoice_id := 38;
  
  add_goods_receipt_invoice(v_l_info_gr, v_invoice_id, v_type, v_user_id, v_code);
END;

--B3
--LAY RA TAT CA PRODUCT_IN_STOCK
SELECT PRODUCT_STOCK_ID,PRODUCT_ID, QUANITY 
FROM PRODUCT_IN_STOCK 
WHERE ACTIVE_FLAG = 1;

--B7
COMMIT;

--B9
SELECT PRODUCT_STOCK_ID,PRODUCT_ID, QUANITY 
FROM PRODUCT_IN_STOCK 
WHERE ACTIVE_FLAG = 1;


