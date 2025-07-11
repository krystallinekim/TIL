-- 모든 앨범과 해당 아티스트 이름 출력

-- 각 앨범의 title과 해당 아티스트의 name을 출력하고, 앨범 제목 기준 오름차순 정렬하세요.

SELECT
	l.title,
	r.name
FROM albums l
LEFT JOIN artists r ON l.artist_id = r.artist_id
ORDER BY l.title