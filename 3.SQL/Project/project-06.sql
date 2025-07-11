-- 6. 각 그룹에서 인기 트랙/아티스트 추천
--     - 동일 그룹 고객들이 가장 많이 산 트랙/아티스트 TOP 3
--     - (TrackName, TimesPurchased)
--     - 트랙, 아티스트의 판매순 집계
WITH
customer_count AS (
	SELECT
		c.customer_id,
		c.first_name || ' ' || c.last_name AS customer_name,
		g.name AS genre_name,
		COUNT(*) OVER(PARTITION BY c.customer_id) AS totalpurchase
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
	JOIN invoice_items ii ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN genres g ON g.genre_id = t.genre_id
),
customer_genre_count AS (
	SELECT
		customer_id,
		customer_name,
		genre_name,
		totalpurchase,
		COUNT(*) AS purchasecount
	FROM customer_count
	GROUP BY customer_id, customer_name, genre_name, totalpurchase
),
customer_genre_ratio AS (
	SELECT 
		*, 
		ROUND(purchasecount * 100 / totalpurchase,2) AS ratio,
		RANK() OVER(PARTITION BY customer_id ORDER BY purchasecount DESC) AS ranking
	FROM customer_genre_count
),
customer_genre AS (
	SELECT 
		customer_id,
		customer_name,
		genre_name,
		ranking
	FROM customer_genre_ratio 
	WHERE ranking = 1
),
track_artist_stats AS (
	SELECT
		g.name AS genre_name,
		a.name || ' - ' || t.name AS trackname,
		t.track_id,
		COUNT(ii.invoice_id) AS totalpurchased
	FROM invoice_items ii
	JOIN invoices i ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN genres g ON g.genre_id = t.genre_id
	JOIN albums l ON l.album_id = t.album_id
	JOIN artists a ON a.artist_id = l.artist_id
	GROUP BY g.name, trackname, t.track_id
),
recommendable_tracks AS (
	SELECT
		cg.customer_id,
		cg.customer_name,
		cg.genre_name,
		cg.ranking,
		tas.trackname,
		tas.totalpurchased,
		RANK() OVER (
			PARTITION BY cg.customer_id, cg.ranking
			ORDER BY tas.totalpurchased DESC, tas.track_id ASC
		) AS genre_track_rank
	FROM customer_genre cg
	JOIN track_artist_stats tas ON cg.genre_name = tas.genre_name
)
SELECT
	customer_id,
	customer_name,
	genre_name AS recommended_from_genre,
	artist_track_name AS recommended_track,
	rank AS genre_rank,
	genre_track_rank AS track_rank_within_genre,
	genre_purchase_count AS genre_based_track_sales
FROM recommendable_tracks
WHERE (rank = 1 AND genre_track_rank <= 2)
   OR (rank = 2 AND genre_track_rank = 1)
ORDER BY customer_id, genre_rank, genre_track_rank;
