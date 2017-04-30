SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS order_recycle;




/* Create Tables */

-- 客户订单回收管理
CREATE TABLE order_recycle
(
	-- 主键
	id varchar(32) NOT NULL COMMENT '主键',
	-- 客户签收单号
	order_no_signed varchar(50) COMMENT '客户签收单号',
	-- 客户签收单条形码
	bar_code_signed varchar(50) COMMENT '客户签收单条形码',
	-- 合伙人
	partner varchar(60) COMMENT '合伙人',
	-- 合伙人工号
	partner_no varchar(20) COMMENT '合伙人工号',
	-- 合伙人地址
	partner_address varchar(200) COMMENT '合伙人地址',
	-- 合伙人电话
	partner_phone varchar(40) COMMENT '合伙人电话',
	-- 销售订单号
	sale_no varchar(50) COMMENT '销售订单号',
	-- 商品名称
	commodity_name varchar(200) COMMENT '商品名称',
	-- 商品条形码
	commodity_bar_code varchar(50) COMMENT '商品条形码',
	-- 商品数量
	commodity_num int COMMENT '商品数量',
	-- 商品单价
	commodity_price decimal(10,2) COMMENT '商品单价',
	-- 交易金额
	commodity_money decimal(10,2) COMMENT '交易金额',
	-- 物流回收状态
	logistics_recyle_status char(1) COMMENT '物流回收状态:0未回收,1已回收',
	-- 财务回收状态
	finance_recyle_status char(1) COMMENT '财务回收状态:0未回收,1已回收',
	-- 回收确认时间
	recyle_date datetime COMMENT '回收确认时间',
	-- 调出仓库
	warehouse_called varchar(32) COMMENT '调出仓库',
	-- 出库时间
	delivery_date datetime COMMENT '出库时间',
	create_by varchar(32),
	create_date datetime,
	last_update_by varchar(32),
	last_update_date datetime,
	remarks varchar(100),
	PRIMARY KEY (id)
) COMMENT = '客户订单回收管理';



