--Trong khi nhân viên th? nh?t ?ang xem có bao nhiêu ??n nh?p hàng hôm nay
--Lúc này nhân viên th? nh?t l?p t?ng hóa ??n nh?p hàng trong ngày th? b? sai sót d? li?u.
--nho commit truoc khi chay
--B1
SET TRANSACTION
ISOLATION LEVEL
READ COMMITTED;
SET

--B2  
SELECT INVOICE_ID, USER_ID, PRICE
FROM INVOICE
WHERE TYPE = 1 AND ACTIVE_FLAG = 1
AND TRUNC(UPDATE_DATE) = '25-JUN-20';


SELECT INVOICE_ID, USER_ID, PRICE
FROM INVOICE
WHERE TYPE = 1 AND ACTIVE_FLAG = 1
AND TRUNC(UPDATE_DATE) = '25-JUN-20';

