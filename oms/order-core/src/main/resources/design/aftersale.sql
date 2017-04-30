SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS aftersale_apply_item;
DROP TABLE IF EXISTS aftersale_record;
DROP TABLE IF EXISTS aftersale_apply;
DROP TABLE IF EXISTS aftersale_pickup;
DROP TABLE IF EXISTS aftersale_refund;




/* Create Tables */

-- 售后申请单
CREATE TABLE aftersale_apply
(
	id varchar(32) NOT NULL COMMENT '主键',
	apply_no varchar(50) NOT NULL COMMENT '售后申请单号',
	order_no varchar(50) NOT NULL COMMENT '关联订单编号',
	pickup_no varchar(50) COMMENT '关联售后取货单号',
	refund_no varchar(50) COMMENT '关联退款单号',
	exchange_order_no varchar(50) COMMENT '关联换货订单号',
	apply_status varchar(30) NOT NULL COMMENT '售后申请单状态',
	apply_type varchar(50) NOT NULL COMMENT '售后申请单,服务类型',
	service_approve_date timestamp COMMENT '客服审核时间',
	service_approve_desc varchar(200) COMMENT '客服审核描述',
	storage_approve_date timestamp COMMENT '仓储审核时间',
	storage_approve_desc varchar(200) COMMENT '仓储审核描述',
	apply_person_id varchar(32) NOT NULL COMMENT '申请人ID',
	apply_person_name varchar(30) NOT NULL COMMENT '申请人姓名',
	apply_person_phone varchar(30) COMMENT '申请人手机号',
	apply_person_address varchar(200) COMMENT '会员下单选择的收货地址',
	reason_select varchar(100) COMMENT '售后原因选择',
	reason_explain varchar(600) COMMENT '售后原因说明',
	remarks varchar(100) COMMENT '备注',
	create_by varchar(32) COMMENT '创建者',
	create_date timestamp COMMENT '创建时间(申请时间)',
	last_update_by varchar(32) COMMENT '最后更新者',
	last_update_date timestamp COMMENT '最后更新时间',
	del_flag varchar(2) COMMENT '删除标记(0：正常；1：删除；2：审核)',
	-- 订单ID
	order_id varchar(32) NOT NULL COMMENT '订单ID',
	-- 售后申请的图片（以 ; 隔开）
	apply_pic_img varchar(500) COMMENT '售后申请的图片',
	PRIMARY KEY (id)
) COMMENT = '售后申请单';


-- 售后申请单明细表
CREATE TABLE aftersale_apply_item
(
	-- 主键
	id varchar(32) NOT NULL COMMENT '主键',
	-- 关联商品图片路径
	commodity_pic varchar(200) COMMENT '关联商品图片路径',
	-- 关联商品名称
	commodity_name varchar(100) COMMENT '关联商品名称',
	-- 商品条形码
	commodity_barcode varchar(50) COMMENT '商品条形码',
	-- 商品规格
	commodity_specifications varchar(50) COMMENT '商品规格',
	-- 商品单位
	-- 
	commodity_unit varchar(30) COMMENT '商品单位',
	-- 支付单价
	commodity_price decimal(13,2) COMMENT '支付单价',
	-- 实付金额
	act_pay_amount decimal(13,2) COMMENT '实付金额',
	-- 商品购买类型
	commodity_buy_type varchar(30) COMMENT '商品购买类型',
	-- 商品购买数量
	commodity_buy_num int(11) COMMENT '商品购买数量',
	-- 商品退货数量
	return_num int(11) COMMENT '商品退货数量',
	-- 售后状态
	aftersale_status varchar(30) COMMENT '售后状态',
	apply_id varchar(32) NOT NULL COMMENT '关联售后申请单ID',
	apply_no varchar(50) COMMENT '售后申请单编号',
	PRIMARY KEY (id)
) COMMENT = '售后申请单明细表';


-- 售后取货单
CREATE TABLE aftersale_pickup
(
	id varchar(32) NOT NULL COMMENT '主键',
	-- 取货单号
	pickup_no varchar(50) NOT NULL COMMENT '取货单号',
	-- 关联订单号
	order_no varchar(50) NOT NULL COMMENT '关联订单号',
	-- 关联售后申请单号
	apply_no varchar(50) NOT NULL COMMENT '关联售后申请单号',
	-- 关联退款单号
	refund_no varchar(50) COMMENT '关联退款单号',
	-- 关联换货订单号
	exchange_order_no varchar(50) COMMENT '关联换货订单号',
	-- 售后取货单状态
	pickup_status varchar(50) NOT NULL COMMENT '售后取货单状态',
	-- (售后取货单申请时间)创建时间
	create_date timestamp NOT NULL COMMENT '(售后取货单申请时间)创建时间',
	-- 创建人
	create_by varchar(32) COMMENT '创建人',
	-- 最后更新时间
	last_update_date timestamp COMMENT '最后更新时间',
	-- 最后更新人
	last_update_by varchar(32) COMMENT '最后更新人',
	-- 售后申请单ID
	apply_id varchar(50) NOT NULL COMMENT '售后申请单ID',
	PRIMARY KEY (id)
) COMMENT = '售后取货单';


-- 售后记录表
CREATE TABLE aftersale_record
(
	id varchar(32) NOT NULL COMMENT '主键',
	description varchar(300) COMMENT '操作记录描述',
	create_date timestamp COMMENT '创建时间',
	opr_id varchar(32) COMMENT '创建人ID',
	opr_name varchar(30) COMMENT '创建人姓名',
	apply_no varchar(50) NOT NULL COMMENT '关联售后申请单编号',
	apply_id varchar(32) NOT NULL COMMENT '关联售后申请单ID',
	PRIMARY KEY (id)
) COMMENT = '售后记录表';


-- 退款单表
CREATE TABLE aftersale_refund
(
	-- 主键
	id varchar(32) NOT NULL COMMENT '主键',
	-- 退款单号
	refund_no varchar(50) NOT NULL COMMENT '退款单号',
	-- 售后申请单号
	apply_no varchar(50) NOT NULL COMMENT '售后申请单号',
	-- 关联订单号
	order_no varchar(50) NOT NULL COMMENT '关联订单号',
	-- 关联售后取货单单号
	pickup_no varchar(50) COMMENT '关联售后取货单单号',
	-- 关联换货订单号
	exchange_order_no varchar(50) COMMENT '关联换货订单号',
	-- 退款单状态
	refund_status varchar(50) COMMENT '退款单状态',
	-- 创建时间
	create_date timestamp NOT NULL COMMENT '创建时间',
	-- 创建人
	create_by varchar(32) COMMENT '创建人',
	-- 最后更新时间
	last_update_date timestamp COMMENT '最后更新时间',
	-- 最后更新人
	last_update_by varchar(32) COMMENT '最后更新人',
	-- 售后申请单ID
	apply_id varchar(50) NOT NULL COMMENT '售后申请单ID',
	PRIMARY KEY (id)
) COMMENT = '退款单表';



/* Create Foreign Keys */

ALTER TABLE aftersale_apply_item
	ADD FOREIGN KEY (apply_id)
	REFERENCES aftersale_apply (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE aftersale_record
	ADD FOREIGN KEY (apply_id)
	REFERENCES aftersale_apply (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



