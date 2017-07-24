set @merge_time := date_sub(now(), interval 1 hour);
use wxmovie_log;

select concat('[', now(), '] Start merge the ', date_format(@merge_time, '%Y-%m-%d %H:00:00'), ' advertisement visit records...');

-- 将不存在按小时统计的表(advertisement_visit_frame_hour_statistics)中的记录插入临时表
drop table if exists temp_advertisement_visit_frame_statistics;
create table temp_advertisement_visit_frame_statistics as
select 
	*
from
	advertisement_visit_frame_statistics t
where
	date_format(t.statistics_time, '%Y-%m-%d %H:00:00') <= @merge_time
	and not exists(
    	select 
        	* 
    	from 
        	advertisement_visit_frame_hour_statistics t1 
    	where 
        	t.advertisement_uuid = t1.advertisement_uuid
        	and t.frame = t1.frame
        	and date_format(t.statistics_time, '%Y-%m-%d %H:00:00') = t1.statistics_time 
    	limit 1
	)
;

-- 合并到按小时统计的表中
insert into advertisement_visit_frame_hour_statistics
select
    0 id,
    date_format(temp.statistics_time, '%Y-%m-%d %H:00:00') statistics_time,
    temp.advertisement_uuid,
    sum(temp.page_view) page_view,
    sum(temp.clicks) clicks,
    0 user_view,
    temp.frame,
    now() create_time,
    now() update_time
from
    temp_advertisement_visit_frame_statistics temp
group by
    temp.advertisement_uuid, temp.frame, date_format(temp.statistics_time, '%Y-%m-%d %H:00:00')
order by
    statistics_time, advertisement_uuid, frame
;

-- 删除已合并的按分钟统计的记录
delete avfs.* from
    advertisement_visit_frame_statistics avfs,
    temp_advertisement_visit_frame_statistics temp
where avfs.id = temp.id
;

-- 将已存在按小时统计的表(advertisement_visit_frame_hour_statistics)中的记录插入临时表
drop table if exists temp_advertisement_visit_frame_statistics;
create table temp_advertisement_visit_frame_statistics as
select 
	*
from
	advertisement_visit_frame_statistics t
where
	date_format(t.statistics_time, '%Y-%m-%d %H:00:00') <= @merge_time
	and exists(
    	select 
        	1 
    	from 
        	advertisement_visit_frame_hour_statistics t1 
    	where 
        	t.advertisement_uuid = t1.advertisement_uuid
        	and t.frame = t1.frame
        	and date_format(t.statistics_time, '%Y-%m-%d %H:00:00') = t1.statistics_time 
    	limit 0, 1
	)
;

-- 合并已存在在按小时统计的表中的数据
update 
    advertisement_visit_frame_hour_statistics t
    join (
        select
            date_format(temp.statistics_time, '%Y-%m-%d %H:00:00') statistics_time,
            temp.advertisement_uuid,
            sum(temp.page_view) page_view,
            sum(temp.clicks) clicks,
            0 user_view,
            temp.frame
        from
            temp_advertisement_visit_frame_statistics temp
        group by
            temp.advertisement_uuid, temp.frame, date_format(temp.statistics_time, '%Y-%m-%d %H:00:00')
    ) t1 on t.statistics_time = t1.statistics_time
    		and t.advertisement_uuid = t1.advertisement_uuid
    		and t.frame = t1.frame
set t.page_view = t.page_view + t1.page_view, t.clicks = t.clicks + t1.clicks
;


-- 删除已合并的按分钟统计的记录
delete avfs.* from
    advertisement_visit_frame_statistics avfs,
    temp_advertisement_visit_frame_statistics temp
where avfs.id = temp.id
;
select concat('[', now(), '] Finished merge the ', date_format(@merge_time, '%Y-%m-%d %H:00:00'), ' advertisement visit records...');