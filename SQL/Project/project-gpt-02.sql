WITH full_sales_data AS (
    SELECT
        c.customer_id,
        c.first_name || ' ' || c.last_name AS customer_name,
        i.invoice_id,
        i.invoice_date,
        t.track_id,
        t.name AS track_name,
        g.name AS genre_name,
        ar.name AS artist_name
    FROM customers c
    JOIN invoices i ON c.customer_id = i.customer_id
    JOIN invoice_items ii ON i.invoice_id = ii.invoice_id
    JOIN tracks t ON ii.track_id = t.track_id
    JOIN genres g ON t.genre_id = g.genre_id
    JOIN albums al ON t.album_id = al.album_id
    JOIN artists ar ON al.artist_id = ar.artist_id
),
-- 1. 고객별 장르별 구매 건수
customer_genre_purchase AS (
    SELECT
        customer_id,
        customer_name,
        genre_name,
        COUNT(*) AS genre_purchase_count
    FROM full_sales_data
    GROUP BY customer_id, customer_name, genre_name
),
-- 2. 고객별 전체 구매 건수
customer_total_purchase AS (
    SELECT
        customer_id,
        COUNT(*) AS total_purchase_count
    FROM full_sales_data
    GROUP BY customer_id
),
-- 3. 고객별 장르별 구매 비율 + 대표 장르 순위
customer_genre_ratio AS (
    SELECT
        cgp.customer_id,
        cgp.customer_name,
        cgp.genre_name,
        cgp.genre_purchase_count,
        ctp.total_purchase_count,
        ROUND(cgp.genre_purchase_count * 100.0 / ctp.total_purchase_count, 2) AS genre_ratio,
        RANK() OVER (PARTITION BY cgp.customer_id ORDER BY cgp.genre_purchase_count DESC) AS genre_rank
    FROM customer_genre_purchase cgp
    JOIN customer_total_purchase ctp ON cgp.customer_id = ctp.customer_id
),
-- 4. 고객별 대표 장르(rank 1, 2)
customer_top_genres AS (
    SELECT *
    FROM customer_genre_ratio
    WHERE genre_rank <= 2
),
-- 5. 장르별 고객 그룹화
genre_group_customers AS (
    SELECT
        customer_id,
        customer_name,
        genre_name,
        genre_rank
    FROM customer_top_genres
),
-- 6. 각 장르 그룹에서 많이 구매된 트랙/아티스트
genre_track_ranking AS (
    SELECT
        fs.genre_name,
        fs.track_id,
        fs.track_name,
        fs.artist_name,
        COUNT(*) AS purchase_count,
        RANK() OVER (PARTITION BY fs.genre_name ORDER BY COUNT(*) DESC, fs.track_id) AS genre_track_rank
    FROM full_sales_data fs
    GROUP BY fs.genre_name, fs.track_id, fs.track_name, fs.artist_name
),
-- 7. 고객별 추천 트랙 (본인이 구매한 트랙 제외)
recommendations AS (
    SELECT
        ggc.customer_id,
        ggc.customer_name,
        ggc.genre_name,
        ggc.genre_rank,
        gtr.track_name || ' - ' || gtr.artist_name AS recommended,
        gtr.purchase_count
    FROM genre_group_customers ggc
    JOIN genre_track_ranking gtr ON ggc.genre_name = gtr.genre_name
    WHERE gtr.genre_track_rank <= CASE
        WHEN ggc.genre_rank = 1 THEN 2  -- 대표 장르면 2개 추천
        WHEN ggc.genre_rank = 2 THEN 1  -- 다음 장르면 1개 추천
    END
    AND NOT EXISTS (
        SELECT 1
        FROM full_sales_data fs
        WHERE fs.customer_id = ggc.customer_id AND fs.track_id = gtr.track_id
    )
)
-- 최종 추천 결과
SELECT
    customer_id,
    customer_name,
    genre_name,
    genre_rank,
    recommended,
    purchase_count
FROM recommendations
ORDER BY customer_id, genre_rank, purchase_count DESC;
