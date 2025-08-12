# 자율 분석 프로젝트 - 고객 음악 추천

현재 파악한 ERD를 바탕으로 추출할 수 있는 데이터나 정보를 확인할 수 있는 대시보드를 제작해 보자

## 전체 통계 요약

### 기본현황

- 전체 고객 수 : 59명

- 총 구매 건수 : 2240건

- 총 판매 금액 : 2328.60$

### 장르 인기도 TOP 3

순위 | 장르 | 구매건수(건) | 점유율(%)
--- | ---| ---| ---|
1| Rock| 835 | 37.28
2| Latin| 386 | 17.23
3| Metal | 264 | 11.79


## 시스템 개요

- 고객에게 음악을 추천함

- 추천 기준은 다음과 같다.

    - 고객이 구매한 곡 중 가장 많이 구매한 곡과 그 다음으로 구매한 곡의 장르를 각각 구함

    - 장르별로 다른 고객들이 가장 많이 구매한 곡을 선별하고, 가장 많이 구매한 장르에 2개, 다음 장르에 1개 추천한다.

    - 고객이 이미 구매한 곡은 제외한다.

```sql
WITH
-- 고객의 선호 장르를 분석
customer_genre_pref AS (
	SELECT
		c.customer_id,
		c.first_name || ' ' || c.last_name AS customer_name,
		g.name AS genre_name,
        -- 고객별로 장르 당 구매 횟수 및 순위를 부여
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
-- 곡의 통계데이터를 분석해 장르명, 판매량을 확인
genre_track_stats AS (
	SELECT
		g.name AS genre_name,
		t.track_id,
		ar.name || ' - ' || t.name AS artist_track_name,
        -- 판매량
		COUNT(ii.invoice_line_id) AS total_sales
	FROM invoice_items ii
	JOIN tracks t ON t.track_id = ii.track_id
	JOIN albums al ON al.album_id = t.album_id
	JOIN artists ar ON ar.artist_id = al.artist_id
	JOIN genres g ON g.genre_id = t.genre_id
	GROUP BY g.name, t.track_id, ar.name, t.name
),
-- 구매항목의 제외를 위해 고객별로 구매한 곡의 목록을 따로 만듦
customer_track_history AS (
	SELECT DISTINCT
		c.customer_id,
		t.track_id
	FROM customers c
	JOIN invoices i ON c.customer_id = i.customer_id
	JOIN invoice_items ii ON ii.invoice_id = i.invoice_id
	JOIN tracks t ON t.track_id = ii.track_id
),
-- 고객별로 추천 곡의 목록을 할당함 
recommendable_tracks AS (
	SELECT
		cgp.customer_id,
		cgp.customer_name,
		cgp.genre_name,
		cgp.genre_rank,
		gts.artist_track_name,
		gts.total_sales,
        -- 고객별, 장르별로 판매량 순위에 맞춰 확인함
		RANK() OVER (
			PARTITION BY cgp.customer_id, cgp.genre_rank
			ORDER BY gts.total_sales DESC, gts.track_id ASC
		) AS track_rank
	FROM customer_genre_pref cgp
	JOIN genre_track_stats gts ON cgp.genre_name = gts.genre_name
	LEFT JOIN customer_track_history cth 
		ON cgp.customer_id = cth.customer_id 
		AND gts.track_id = cth.track_id
    -- 이 때, 고객별로 선호장르 1, 2위만 고르고, 이미 구매한 곡은 제외함
	WHERE cgp.genre_rank <= 2
	  AND cth.track_id IS NULL
)
-- 최종 결과
SELECT
	customer_id, -- 고객ID
	customer_name, -- 고객명
	genre_name AS recommended_from_genre, -- 추천장르
	artist_track_name AS recommended_track, -- 곡명
FROM recommendable_tracks
-- 선호 1위 장르에서는 추천순위 2위까지 표시하고, 선호 2위 장르에서는 추천순위 1위만 표시함
WHERE (genre_rank = 1 AND track_rank <= 2)
   OR (genre_rank = 2 AND track_rank = 1)
ORDER BY customer_id, genre_rank, track_rank;
```


## 결과

고객ID | 고객명 |  추천장르 | 추천곡
---| ---| ---| ---|
1|Luís Gonçalves|Rock|	"Accept - Balls to the Wall"
1|Luís Gonçalves|Rock|	"AC/DC - Inject The Venom"
1|Luís Gonçalves|Latin|	"Caetano Veloso - Meditação"
2|Leonie Köhler|Rock|	"AC/DC - Inject The Venom"	
2|Leonie Köhler|Rock|	"AC/DC - Snowballed"
2|Leonie Köhler|Blues|	"Eric Clapton - Sunshine Of Your Love"


### 장르별 추천 분포

장르| 비율 
---|---  
Rock               | ██████████████████████████████████ 57.6%  
Latin              | █████████ 14.7%  
Alternative & Punk | ████ 9.6%  
Metal              | ████ 9.0%  
Jazz               | ▏1.1%  
Blues              | ▏0.6%  
Classical          | ▏0.6%  


## 주요 인사이트

- 가장 인기 있는 장르는 락 
    - 장르 전체 점유율 37.28%

    - 추천 장르의 57.6%


- 추천은 소수의 인기 트랙에 편중됨
    
    - 상위 10%의 트랙이 추천의 22.85%를 차지
