  
-- 2015-10-22上线sql  
use wxmovie_log;
alter table advertisement_visit_statistics add is_settled tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否已经结算';
update advertisement_visit_statistics set is_settled = 1;