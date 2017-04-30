SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS choice_share_order;
DROP TABLE IF EXISTS join_record;
DROP TABLE IF EXISTS operate_record;
DROP TABLE IF EXISTS reward_luck_no;
DROP TABLE IF EXISTS share_draw;
DROP TABLE IF EXISTS share_order;
DROP TABLE IF EXISTS reward_manager;




/* Create Tables */

-- 精选晒单
CREATE TABLE choice_share_order
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	choice_image text COMMENT '精选晒单图片',
	create_by varchar(32) COMMENT '操作人',
	create_name varchar(20) COMMENT '操作人名字',
	update_date timestamp COMMENT '操作时间',
	PRIMARY KEY (id)
) COMMENT = '精选晒单';


-- 参与记录
CREATE TABLE join_record
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	phone varchar(20) NOT NULL COMMENT '参与人手机号',
	head_image varchar(200) COMMENT '头像',
	create_date timestamp COMMENT '参与时间',
	ticket varchar(15) NOT NULL COMMENT '奖券/抽奖码',
	reward_no varchar(20) NOT NULL COMMENT '抽奖管理编号',
	reward_id varchar(32) NOT NULL COMMENT '外键ID（免费抽奖ID）',
	member_id varchar(32) NOT NULL COMMENT '参与者Id',
	PRIMARY KEY (id)
) COMMENT = '参与记录';


-- 操作日志
CREATE TABLE operate_record
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	content varchar(200) NOT NULL COMMENT '操作内容',
	record_date timestamp COMMENT '操作时间',
	record_user varchar(20) COMMENT '操作人',
	record_by varchar(32) COMMENT '操作人',
	reward_id varchar(32) NOT NULL COMMENT '外键ID',
	PRIMARY KEY (id)
) COMMENT = 'operate_record';


-- 幸运号
CREATE TABLE reward_luck_no
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	luck_no varchar(20) NOT NULL COMMENT '幸运号',
	reward_id varchar(32) NOT NULL COMMENT '外键ID（抽奖管理ID）',
	take_count int(11) DEFAULT 0 COMMENT '参与人次',
	one_value varchar(20) COMMENT '数值A',
	two_value varchar(10) COMMENT '数值B',
	create_date timestamp COMMENT '创建时间',
	last_update_date timestamp COMMENT '最后更新时间',
	create_by varchar(32) COMMENT '操作人',
	create_name varchar(20) COMMENT '操作人名字',
	PRIMARY KEY (id)
) COMMENT = '幸运号';


-- 抽奖管理
CREATE TABLE reward_manager
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	title varchar(50) NOT NULL COMMENT '标题',
	reward_no varchar(20) NOT NULL COMMENT '免费抽奖编号',
	date_no varchar(10) NOT NULL COMMENT '抽奖期号',
	send_staus char DEFAULT '0' NOT NULL COMMENT '发布状态（0：未发布 1： 已发布）',
	reward_status char DEFAULT '0' NOT NULL COMMENT '活动状态（0：即将开始 1 进行中 2 已结束）',
	show_image text COMMENT '列表展示图',
	detail_image text COMMENT '详情展示图',
	start_date timestamp NOT NULL COMMENT '开始时间',
	end_date timestamp NOT NULL COMMENT '结束时间',
	reward_date timestamp NOT NULL COMMENT '开奖时间',
	create_date timestamp COMMENT '创建时间',
	create_by varchar(32) COMMENT '操作人',
	create_name varchar(20) COMMENT '操作人名字',
	last_update_date timestamp COMMENT '最后更新时间',
	del_flag char DEFAULT '0' NOT NULL COMMENT '删除状态（0 否 1 是）',
	PRIMARY KEY (id)
) COMMENT = '抽奖管理';


-- 分享抽奖
CREATE TABLE share_draw
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	count int(11) DEFAULT 1 NOT NULL COMMENT '剩余可抽奖次数',
	is_share char DEFAULT '0' NOT NULL COMMENT '是否分享（0：否 1：是）',
	reward_no varchar(20) NOT NULL COMMENT '免费抽奖编号',
	is_draw char DEFAULT '0' COMMENT '分享之前是否抽过奖（0 ：否 1：是）',
	update_date timestamp COMMENT '更新时间',
	reward_id varchar(32) NOT NULL COMMENT '外键ID',
	member_id varchar(32) NOT NULL COMMENT '分享的用户ID',
	PRIMARY KEY (id)
) COMMENT = '分享抽奖';


-- 设置晒单
CREATE TABLE share_order
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	reward_date_no varchar(10) NOT NULL COMMENT '抽奖期号',
	luck_name varchar(20) NOT NULL COMMENT '中奖人',
	receive_address varchar(500) NOT NULL COMMENT '领奖地址',
	show_image text NOT NULL COMMENT '晒单图片',
	create_by varchar(32) COMMENT '操作人',
	create_name varchar(20) COMMENT '操作人名字',
	update_date timestamp COMMENT '操作时间',
	reward_id varchar(32) NOT NULL COMMENT '外键ID 抽奖管理ID',
	PRIMARY KEY (id)
) COMMENT = '设置晒单';



/* Create Foreign Keys */

ALTER TABLE join_record
	ADD FOREIGN KEY (reward_id)
	REFERENCES reward_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE operate_record
	ADD FOREIGN KEY (reward_id)
	REFERENCES reward_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE reward_luck_no
	ADD FOREIGN KEY (reward_id)
	REFERENCES reward_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE share_draw
	ADD FOREIGN KEY (reward_id)
	REFERENCES reward_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE share_order
	ADD FOREIGN KEY (reward_id)
	REFERENCES reward_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



