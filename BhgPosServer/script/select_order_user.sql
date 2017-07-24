SELECT
	CONCAT(
		'*4\r\n',
		'$',
		LENGTH(redis_cmd),
		'\r\n',
		redis_cmd,
		'\r\n',
		'$',
		LENGTH(redis_key),
		'\r\n',
		redis_key,
		'\r\n',
		'$',
		LENGTH(hkey),
		'\r\n',
		hkey,
		'\r\n',
		'$',
		LENGTH(hval),
		'\r\n',
		hval,
		'\r'
	) AS redis
FROM
	(
		SELECT
			'HSET' AS redis_cmd,
			'order_user' AS redis_key,
			order_user_id AS hkey,
			CONCAT(order_user_id,'#',IFNULL(name,''),'#',IFNULL(age,''),'#',IFNULL(gender,''),'#',IFNULL(province,''),'#',IFNULL(revenue,''),'#',IFNULL(education,'')) AS hval
		FROM
			order_user
	) AS ou;