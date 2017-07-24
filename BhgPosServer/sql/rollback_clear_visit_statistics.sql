-- 广告投放回退脚本，清除统计日志中不存在的广告统计。
use wxmovie_v5;
drop table if exists wxmovie_log.clear_advertisement_uuid;
create table wxmovie_log.clear_advertisement_uuid as
select 
    distinct t1.advertisement_uuid
from 
    (select distinct avfs.advertisement_uuid from wxmovie_log.advertisement_visit_frame_statistics avfs) t1
    left join 
    (select distinct a.advertisement_uuid from advertisement a) t2
    on t1.advertisement_uuid = t2.advertisement_uuid
where
    t2.advertisement_uuid is null
;

delete from wxmovie_log.advertisement_visit_frame_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_area_code_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_cinema_movie_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_material_area_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_material_visit_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_unique_visitor_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);

delete from wxmovie_log.advertisement_visit_statistics where exists(select 1 from wxmovie_log.clear_advertisement_uuid c where c.advertisement_uuid = advertisement_uuid);