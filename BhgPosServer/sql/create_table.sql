-- 创建按小时统计PV/CLICK的表
create table if not exists advertisement_visit_frame_hour_statistics (
  id bigint(20) not null auto_increment,
  statistics_time datetime not null comment '时间',
  advertisement_uuid bigint(20) not null comment '广告投放id',
  page_view bigint(20) not null default '0' comment '展示数',
  user_view bigint(20) not null default '0' comment '用户访问数',
  clicks bigint(20) not null default '0' comment '点击数',
  frame tinyint(4) default null comment '投放帧数',
  create_time datetime default null comment '创建时间',
  update_time datetime default null comment '更新时间',
  primary key (id)
) engine=innodb default charset=utf8;
create index i_advertisement_uuid on advertisement_visit_frame_hour_statistics(advertisement_uuid);
create index i_statistics_time on advertisement_visit_frame_hour_statistics(statistics_time);
create index i_frame on advertisement_visit_frame_hour_statistics(frame);
create index i_statistics_time_advertisement_uuid_frame on advertisement_visit_frame_hour_statistics(statistics_time, advertisement_uuid, frame);