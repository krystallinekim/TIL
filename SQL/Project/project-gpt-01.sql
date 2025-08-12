WITH
customer_genre_pref AS (
	SELECT
		c.customer_id,
		c.first_name || ' ' || c.last_name AS customer_name,
		g.name AS genre_name,
		COUNT(*) AS purchase_count,
		RANK() OVER (
			PARTITION BY c.customer_id
			ORDER BY COUNT(*) DESC
		) AS genre_rank
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
	JOIN invoice_items ii ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN genres g ON g.genre_id = t.genre_id
	GROUP BY c.customer_id, c.first_name, c.last_name, g.name
),
genre_track_stats AS (
	SELECT
		g.name AS genre_name,
		t.track_id,
		ar.name || ' - ' || t.name AS artist_track_name,
		COUNT(ii.invoice_line_id) AS total_sales
	FROM invoice_items ii
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN albums al ON al.album_id = t.album_id
	JOIN artists ar ON ar.artist_id = al.artist_id
	JOIN genres g ON g.genre_id = t.genre_id
	GROUP BY g.name, t.track_id, ar.name, t.name
),
customer_track_history AS (
	SELECT DISTINCT
		c.customer_id,
		t.track_id
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
	JOIN invoice_items ii ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
),
recommendable_tracks AS (
	SELECT
		cgp.customer_id,
		cgp.customer_name,
		cgp.genre_name,
		cgp.genre_rank,
		gts.artist_track_name,
		gts.total_sales,
		RANK() OVER (
			PARTITION BY cgp.customer_id, cgp.genre_rank
			ORDER BY gts.total_sales DESC, gts.track_id ASC
		) AS track_rank
	FROM customer_genre_pref cgp
	JOIN genre_track_stats gts ON cgp.genre_name = gts.genre_name
	LEFT JOIN customer_track_history cth 
		ON cgp.customer_id = cth.customer_id 
		AND gts.track_id = cth.track_id
	WHERE cgp.genre_rank <= 2
	  AND cth.track_id IS NULL
)
SELECT
	customer_id,
	customer_name,
	genre_name AS recommended_from_genre,
	artist_track_name AS recommended_track,
	genre_rank,
	track_rank,
	total_sales
FROM recommendable_tracks
WHERE (genre_rank = 1 AND track_rank <= 2)
   OR (genre_rank = 2 AND track_rank = 1)
ORDER BY customer_id, genre_rank, track_rank;