SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS coupon_admin_category;
DROP TABLE IF EXISTS coupon_admin_coupon_grant;
DROP TABLE IF EXISTS coupon_receive;
DROP TABLE IF EXISTS coupon_admin;
DROP TABLE IF EXISTS coupon_grant_member;
DROP TABLE IF EXISTS coupon_grant;




/* Create Tables */

-- 优惠券管理
CREATE TABLE coupon_admin
(
	id varchar(32) NOT NULL COMMENT '优惠券管理id',
	number varchar(30) NOT NULL COMMENT '编码',
	face_value decimal(10,2) DEFAULT 0 NOT NULL COMMENT '面值',
	name varchar(50) NOT NULL COMMENT '优惠券名称',
	consumption_limit decimal(10,2) NOT NULL COMMENT '消费限制(-1)无限制',
	effective_date_state char DEFAULT '0' NOT NULL COMMENT '有效期状态(1:指定有效期，0:自定义有效期)',
	effective_date_start timestamp NOT NULL COMMENT '有效期开始时间',
	effective_date_end timestamp NOT NULL COMMENT '有效期结束时间',
	coupon_state char DEFAULT '0' NOT NULL COMMENT '优惠券状态（0未生效，1已生效，2已过期）',
	effective_date_num decimal(10,2) COMMENT '有效天数',
	goods_select char COMMENT '选择商品(0全部商品，2指定商品，3指定商品类目',
	other_limit char DEFAULT '0' COMMENT '其他限制(0无限制）1有限制',
	create_date timestamp COMMENT '创建时间',
	remarks varchar(500) COMMENT '备注',
	create_by varchar(50) COMMENT '创建者',
	last_update_by varchar(50) COMMENT '更新者',
	last_update_date timestamp COMMENT '更新时间',
	del_flag char DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；2：审核）',
	surplus int COMMENT '剩余数量',
	PRIMARY KEY (id)
) COMMENT = '优惠券管理';


-- 类目表和商品表和优惠券管理中间表
CREATE TABLE coupon_admin_category
(
	id varchar(32) NOT NULL COMMENT 'id',
	category_id varchar(32) COMMENT '类目表id',
	coupon_admin_id varchar(32) NOT NULL COMMENT '优惠券管理id',
	commodity_id varchar(32) COMMENT '商品表id',
	type char(1) DEFAULT '0' NOT NULL COMMENT '2指定商品，3指定商品类目',
	PRIMARY KEY (id)
) COMMENT = '类目表和商品表和优惠券管理中间表';


-- 优惠价发放和优惠券管理中间表
CREATE TABLE coupon_admin_coupon_grant
(
	id varchar(32) NOT NULL COMMENT '优惠价发放和优惠券管理中间表id',
	coupon_admin_id varchar(32) NOT NULL COMMENT '优惠券管理id',
	coupon_grant_id varchar(32) NOT NULL COMMENT '优惠券发放id',
	PRIMARY KEY (id)
) COMMENT = '优惠价发放和优惠券管理中间表';


-- 优惠券发放
CREATE TABLE coupon_grant
(
	id varchar(32) NOT NULL COMMENT '优惠券发放id',
	number varchar(50) NOT NULL COMMENT '编码',
	name varchar(50) NOT NULL COMMENT '发放名称',
	grant_date timestamp NOT NULL COMMENT '发放时间',
	grant_mode char DEFAULT '0' NOT NULL COMMENT '发放方式(0系统推送,1用户领取,2指定用户)',
	grant_type char DEFAULT '0' NOT NULL COMMENT '发放类型(0商品，1注册，2分享)',
	receive_state char DEFAULT '0' NOT NULL COMMENT '领取状态(0未开始,1领取中,2领取完成，3，已结束)',
	receive_limit int DEFAULT 1 NOT NULL COMMENT '领取限制(-1)无限制',
	grant_num int DEFAULT 1 NOT NULL COMMENT '发放张数',
	surplus_num int DEFAULT 0 COMMENT '剩余量',
	zongnum int DEFAULT 0 COMMENT '发放总数量',
	receive_num int DEFAULT 0 COMMENT '领取量',
	url varchar(500) COMMENT '链接',
	state char DEFAULT '0' NOT NULL COMMENT '状态，0不暂停，1暂停领取',
	create_date timestamp COMMENT '创建日期',
	remarks varchar(500) COMMENT ' 备注',
	create_by varchar(32) COMMENT '创建者',
	last_update_by varchar(32) COMMENT '更新者',
	last_update_date timestamp COMMENT '更新日期',
	del_flag char DEFAULT '0' NOT NULL COMMENT '删除标记（0：正常；1：删除；2：审核）',
	is_grant char DEFAULT '0' COMMENT '是否已发放（0未发放,1发放)',
	PRIMARY KEY (id)
) COMMENT = '优惠券发放';


-- 优惠券发放和会员中间表（指定用户）
CREATE TABLE coupon_grant_member
(
	id varchar(32) NOT NULL COMMENT '优惠券发放和会员中间表id',
	coupon_grant_id varchar(32) NOT NULL COMMENT '优惠券发放id',
	member_id varchar(32) NOT NULL COMMENT '会员id',
	PRIMARY KEY (id)
) COMMENT = '优惠券发放和会员中间表（指定用户）';


-- 优惠券领取
CREATE TABLE coupon_receive
(
	id varchar(32) NOT NULL COMMENT '优惠券领取id',
	member_id varchar(50) NOT NULL COMMENT '会员id',
	nick_name varchar(50) NOT NULL COMMENT '昵称',
	phone varchar(15) NOT NULL COMMENT '会员手机号',
	receive_date timestamp NOT NULL COMMENT '领取时间',
	use_state char DEFAULT '0' NOT NULL COMMENT '使用状态(0未使用，1使用)',
	use_date timestamp COMMENT '使用时间',
	create_by varchar(32) COMMENT '创建者',
	create_date timestamp COMMENT '创建日期 : 创建日期',
	remarks varchar(500) COMMENT '备注',
	coupon_admin_id varchar(32) NOT NULL COMMENT '优惠券管理id',
	coupon_grant_id varchar(32) NOT NULL COMMENT '优惠券发放id',
	PRIMARY KEY (id)
) COMMENT = '优惠券领取';



/* Create Foreign Keys */

ALTER TABLE coupon_admin_category
	ADD FOREIGN KEY (coupon_admin_id)
	REFERENCES coupon_admin (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE coupon_admin_coupon_grant
	ADD FOREIGN KEY (coupon_admin_id)
	REFERENCES coupon_admin (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE coupon_receive
	ADD FOREIGN KEY (coupon_admin_id)
	REFERENCES coupon_admin (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE coupon_admin_coupon_grant
	ADD FOREIGN KEY (coupon_grant_id)
	REFERENCES coupon_grant (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE coupon_grant_member
	ADD FOREIGN KEY (coupon_grant_id)
	REFERENCES coupon_grant (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE coupon_receive
	ADD FOREIGN KEY (coupon_grant_id)
	REFERENCES coupon_grant (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



