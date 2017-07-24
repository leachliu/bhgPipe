select
--	(select relname||'--'||(select description from pg_description where objoid=oid and objsubid=0) as comment from pg_class where oid=a.attrelid) as table_name,
	a.attname as column_name,
	format_type(a.atttypid,a.atttypmod) as data_type,
	(case when atttypmod-4>0 then atttypmod-4 else 0 end)data_length,
	(case when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='p')>0 then 'Y' else 'N' end) as main_key,
	(case when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='u')>0 then 'Y' else 'N' end) as unique_constraints,
	(case when (select count(*) from pg_constraint where conrelid = a.attrelid and conkey[1]=attnum and contype='f')>0 then 'Y' else 'N' end) as foreign_key,
	(case when a.attnotnull=true then 'Y' else 'N' end) as nullable,
	col_description(a.attrelid,a.attnum) as comment
from 
	pg_attribute a
where 
	attstattarget=-1 and attrelid in (
	select oid from pg_class 
	where relname in (
		select relname from pg_class 
		where relkind ='r' and relname = ?))
order by 
--	table_name,
	a.attnum;