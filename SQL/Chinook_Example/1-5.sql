-- 각 장르별 트랙 수 집계

-- 각 장르(genres.name)별로 트랙 수를 집계하고, 트랙 수 내림차순으로 정렬하세요.

SELECT
	g.name,
	COUNT(track_id) AS track_count
FROM genres g
LEFT JOIN tracks ON tracks.genre_id = g.genre_id
GROUP BY g.name
ORDER BY track_count DESC;