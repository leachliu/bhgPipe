mysql -h192.168.1.156 -pwbcloud123 -P3306 -uwbcloud wxmovie_new --skip-column-names --raw --default-character-set=utf8 < select_order_user.sql | redis-cli -h "10.10.3.62" -p "6379" -a "" --pipe
