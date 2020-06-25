-- nhân viên th? 2 thêm m?t ??n nh?p hàng và c?p nh?p vào h? th?ng

--Employee 2 add a goods recreipt to invoice - use add_goods_receipt_invoice()
--B3
DECLARE 
  v_l_info_gr list_info_goods_receipt := list_info_goods_receipt(
    info_goods_receipt( 24, 5),
    info_goods_receipt( 26, 3 )
  );
  v_type NUMBER; 
  v_user_id NUMBER; 
  v_code VARCHAR2(30);
  v_invoice_seq NUMBER(5, 0);
BEGIN
  v_type := 1;
  v_user_id := 1;
  v_code := 'HD016';
   --GET INVOICE_SEQ NEXT VALUE
  SELECT INVOICE_SEQ.NEXTVAL INTO v_invoice_seq FROM DUAL;
  
  add_goods_receipt_invoice(v_l_info_gr, v_invoice_seq, v_type, v_user_id, v_code);
END;

--B4
COMMIT;




