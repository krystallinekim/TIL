-- 가장 많이 팔린 트랙 TOP 5

-- 판매량(구매된 수량)이 가장 많은 트랙 5개(track_id, name, 총 판매수량)를 출력하세요.
-- 동일 판매수량일 경우 트랙 이름 오름차순 정렬하세요.

SELECT
	t.track_id,
	t.name,
	SUM(i.quantity) AS track_quantity
FROM invoice_items i
JOIN tracks t ON t.track_id = i.track_id
GROUP BY t.track_id, t.name
ORDER BY track_quantity DESC, t.name LIMIT 5;