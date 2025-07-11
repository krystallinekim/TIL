-- 장르별 상위 3개 아티스트 및 트랙 수

-- 각 장르별로 트랙 수가 가장 많은 상위 3명의 아티스트(artist_id, name, track_count)를 구하세요.
-- 동점일 경우 아티스트 이름 오름차순 정렬.
WITH
artist_track_genre AS (
SELECT
		r.artist_id,
		r.name AS artist,
		t.track_id,
		g.genre_id,
		g.name AS genre
	FROM artists r
	LEFT JOIN albums l ON r.artist_id = l.artist_id
	LEFT JOIN tracks t ON t.album_id = l.album_id
	RIGHT JOIN genres g ON g.genre_id = t.genre_id
),
ranking AS (
	SELECT
		genre,
		artist,
		COUNT(track_id) AS track_count,
		RANK() OVER(PARTITION BY genre ORDER BY COUNT(track_id) DESC) AS track_rank
	FROM artist_track_genre
	GROUP BY genre_id, genre, artist_id, artist
)
SELECT
	genre,
	artist,
	track_count
FROM ranking
WHERE track_rank <=3
ORDER BY genre, track_count DESC, artist;