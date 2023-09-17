create database traffic_violation_statistics_dispatch;
use traffic_violation_statistics_dispatch;

create table if not exists camera
(
	id int not null,
	locations varchar(512) not null comment '地址',
	create_time datetime null comment '创建时间',
	constraint camera_id_uindex
		unique (id)
);

alter table camera
	add primary key (id);

create table if not exists illegal_vehicle_statistics
(
	id bigint not null comment 'id',
	vehicle_type varchar(25) not null comment '车类型',
	violation_type varchar(25) not null comment '违规类型',
	camera_id int not null comment '摄像头id',
	create_time datetime default CURRENT_TIMESTAMP null comment '时间',
	constraint illegal_vehicle_statistics_id_uindex
		unique (id),
	constraint illegal_vehicle_statistics_camera_id_fk
		foreign key (camera_id) references camera (id)
);

alter table illegal_vehicle_statistics
	add primary key (id);

create table if not exists message
(
	id bigint not null,
	from_camera int not null comment '发送消息来自 摄像头 id',
	context text null comment '消息内容',
	create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
	is_being_read tinyint(1) default 0 not null comment '是否被阅读',
	constraint message_id_uindex
		unique (id),
	constraint message_camera_fk_from_username
		foreign key (from_camera) references camera (id)
);

alter table message
	add primary key (id);

create table if not exists resource
(
	resource_id bigint not null,
	resource_path varchar(512) not null,
	resource_from int null,
	resource_del tinyint(1) default 0 null,
	resource_is_processing tinyint(1) default 0 null,
	resource_processing varchar(512) null,
	constraint resource_resource_id_uindex
		unique (resource_id),
	constraint resource_resource_path_uindex
		unique (resource_path),
	constraint resource_resource_processing_uindex
		unique (resource_processing),
	constraint resource_camera_fk_id
		foreign key (resource_from) references camera (id)
);

alter table resource
	add primary key (resource_id);

