use wxmovie_log;

set @start_time = '2016-01-18 17:00:00';
set @end_time = '2016-01-21 15:00:00';

insert into 
    advertisement_unique_visitor_statistics(statistics_time, advertisement_uuid, unique_visitor, create_time, update_time)
select 
    v.statistics_time,
    v.advertisement_uuid,
    v.unique_visitor,
    v.create_time,
    v.update_time
from
    `wxmovie_log_prepare`.advertisement_unique_visitor_statistics v
where
    v.`statistics_time` >= @start_time
    and v.`statistics_time` <= @end_time
;