SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS activity_commodity_sku;
DROP TABLE IF EXISTS activity_commodity;
DROP TABLE IF EXISTS activity_manager;
DROP TABLE IF EXISTS activity_pre_sale_tag;
DROP TABLE IF EXISTS panicbuy_remind;




/* Create Tables */

-- 活动商品设置表
CREATE TABLE activity_commodity
(
	id varchar(32) NOT NULL COMMENT '主键',
	activity_no varchar(50) NOT NULL COMMENT '关联活动编号',
	pic_path varchar(200) COMMENT '图片路径',
	activity_type varchar(30) NOT NULL COMMENT '活动类型(抢购，预售，普通，新用户，批发）',
	commodity_no varchar(50) NOT NULL COMMENT '商品编号',
	commodity_id varchar(32) NOT NULL COMMENT '关联商品ID',
	activity_title varchar(200) COMMENT '活动标题',
	commodity_barcode varchar(50) NOT NULL COMMENT '商品条形码',
	show_price varchar(20) NOT NULL COMMENT '活动价格显示(包含区间价格)',
	start_date timestamp COMMENT '开始时间',
	end_date timestamp COMMENT '结束时间',
	sort_top_no int(3) COMMENT '置顶序号',
	sort_no int(3) COMMENT '排序号',
	is_recommend varchar(2) COMMENT '是否推荐商品 1:是,0:不是',
	recommend_sort int(3) COMMENT '推荐商品排序',
	id_limit_count int(11) COMMENT '单个会员限购数',
	limit_count int(11) COMMENT '总限购数量',
	sale_increase int(11) DEFAULT 0 COMMENT '销量增量值',
	enable_special_count varchar(2) COMMENT '是否启用特殊数量值 1:启用,0:不启用',
	special_count int(11) COMMENT '特殊数量值',
	activity_id varchar(32) NOT NULL COMMENT '活动ID',
	deliver_date datetime COMMENT '发货时间',
	islimit char COMMENT 'islimit是否显示限定数量，0否，1是',
	is_neworder char COMMENT '是否显示最新下单的用户',
	buy_count char COMMENT '购买数量选择',
	follow_count int DEFAULT 0 COMMENT '关注人数',
	activity_commodity_status char DEFAULT '0' COMMENT '是否抢完  抢完0未抢完  3抢完',
	max_price decimal(10,2) COMMENT '最大价格',
	min_price decimal(10,2) COMMENT '最小价格',
	pre_sale_tag_id varchar(32) COMMENT '活动预售标签id',
	PRIMARY KEY (id)
) COMMENT = '活动商品设置表';


-- 活动商品明细表
CREATE TABLE activity_commodity_sku
(
	id varchar(32) NOT NULL COMMENT '主键',
	activity_no varchar(50) COMMENT '关联活动编号',
	activity_price decimal(13,2) NOT NULL COMMENT '活动价格',
	commodity_sku_id varchar(32) NOT NULL COMMENT 'sku ID',
	commodity_sku_no varchar(50) NOT NULL COMMENT '商品sku编号',
	commodity_sku_barcode varchar(50) COMMENT '商品sku条形码',
	commodity_sku_title varchar(200) COMMENT '商品sku标题',
	selection_start int(11) COMMENT '开始区间数(批发管理用)',
	selection_end int(11) COMMENT '结束区间数(批发管理用)',
	attr_group varchar(100) COMMENT '属性组合(针对有属性商品)',
	activity_type varchar(30) COMMENT '活动类型(抢购，预售，普通，新用户，批发）',
	limit_count int(11) COMMENT '限购数量(预售抢购用)',
	activity_commodity_id varchar(32) NOT NULL COMMENT '关联商品设置表ID',
	activity_id varchar(32) NOT NULL COMMENT '关联活动ID',
	commodity_price decimal(13,2) COMMENT '优惠价',
	commodity_no varchar(50) COMMENT '商品编号',
	PRIMARY KEY (id)
) COMMENT = '活动商品明细表';


-- 活动管理表
CREATE TABLE activity_manager
(
	id varchar(32) NOT NULL COMMENT '主键',
	activity_type varchar(30) NOT NULL COMMENT '活动类型(抢购，预售，普通，新用户，批发）',
	activity_no varchar(50) NOT NULL COMMENT '活动编号',
	release_status char DEFAULT '0' NOT NULL COMMENT '发布状态1:已发布,0:未发布',
	activity_status char DEFAULT '0' NOT NULL COMMENT '活动状态（即将开始0，进行中1，已结束2）',
	title varchar(200) NOT NULL COMMENT '活动标题',
	start_date timestamp COMMENT '开始时间',
	end_date timestamp COMMENT '结束时间',
	operator varchar(30) COMMENT '操作人',
	show_count_down char DEFAULT '0' COMMENT '是否显示倒计时 1:显示,0:不显示',
	pic_path varchar(200) COMMENT '活动图片路径',
	create_date timestamp COMMENT '创建时间',
	create_by varchar(32) COMMENT '创建人',
	last_update_date timestamp COMMENT '最后更新时间',
	last_update_by varchar(30) COMMENT '最后更新人',
	remarks varchar(500) COMMENT '活动描述',
	del_flag char DEFAULT '0' COMMENT '删除标记(0：正常；1：删除；2：审核)',
	top_sore int DEFAULT 1 COMMENT '置顶序号',
	PRIMARY KEY (id)
) COMMENT = '活动管理表';


-- 活动预售标签表
CREATE TABLE activity_pre_sale_tag
(
	id varchar(32) NOT NULL COMMENT '主键ID',
	number int(11) NOT NULL COMMENT '序号',
	name varchar(20) NOT NULL COMMENT '标签名称',
	create_date timestamp COMMENT '创建时间',
	del_flag int(11) DEFAULT 0 COMMENT '删除(0：正常；1：删除）',
	PRIMARY KEY (id)
) COMMENT = '活动预售标签表';


-- 抢购提醒表
CREATE TABLE panicbuy_remind
(
	id varchar(32) NOT NULL COMMENT '主键',
	activity_id varchar(32) NOT NULL COMMENT '活动id',
	commodity_id varchar(32) NOT NULL COMMENT '商品Id',
	member_id varchar(32) NOT NULL COMMENT '会员id',
	isremind char DEFAULT '0' COMMENT '是否提醒(1:加入提醒 ，0:取消提醒)',
	is_jiguang_remind char DEFAULT '0' COMMENT '是否极光提醒（0未提醒，1提醒）',
	PRIMARY KEY (id)
) COMMENT = '抢购提醒表';



/* Create Foreign Keys */

ALTER TABLE activity_commodity_sku
	ADD FOREIGN KEY (activity_commodity_id)
	REFERENCES activity_commodity (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE activity_commodity
	ADD FOREIGN KEY (activity_id)
	REFERENCES activity_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE activity_commodity_sku
	ADD FOREIGN KEY (activity_id)
	REFERENCES activity_manager (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



