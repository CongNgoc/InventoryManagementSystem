--Trong khi nh�n vi�n th? nh?t ?ang xem c� bao nhi�u ??n nh?p h�ng h�m nay
--L�c n�y nh�n vi�n th? nh?t l?p t?ng h�a ??n nh?p h�ng trong ng�y th? b? sai s�t d? li?u.
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

