select 
	cast(sys.columns.name as varchar(255)) as column_name, 
	cast(sys.types.name as varchar(255)) as data_type, 
	sys.columns.max_length as data_length, 
	sys.columns.is_nullable as nullable, 
	(select count(*) from sys.identity_columns where sys.identity_columns.object_id = sys.columns.object_id and sys.columns.column_id = sys.identity_columns.column_id) as main_key ,
	(select cast(value as varchar(255)) from sys.extended_properties where sys.extended_properties.major_id = sys.columns.object_id and sys.extended_properties.minor_id = sys.columns.column_id) as comment
from sys.columns, 
	sys.tables, 
	sys.types 
where 
	sys.columns.object_id = sys.tables.object_id 
	and sys.columns.system_type_id=sys.types.system_type_id 
	and sys.tables.name=? 
order by sys.columns.column_id;