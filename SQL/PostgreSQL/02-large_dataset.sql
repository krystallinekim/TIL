-- generate_series: 자동으로 데이터를 만들어 줌 --> 다양한 타입으로 생성 가능함

-- 숫자 생성
SELECT generate_series(1,10);

-- 날짜 생성
SELECT generate_series (
	'2024-01-01'::DATE,
	'2024-12-31'::DATE,
	'1 day'::INTERVAL
);

-- 시간 생성
SELECT generate_series(
	'2024-01-01 00:00:00'::TIMESTAMP,
	'2024-12-31 23:59:59'::TIMESTAMP,
	'1 hour'::INTERVAL
)
