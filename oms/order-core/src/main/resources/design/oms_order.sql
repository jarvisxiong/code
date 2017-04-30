SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS daily_sales;
DROP TABLE IF EXISTS oms_order_record;
DROP TABLE IF EXISTS oms_order;
DROP TABLE IF EXISTS oms_orderdetail;
DROP TABLE IF EXISTS summary_sales;




/* Create Tables */

-- 日销量统计表
CREATE TABLE daily_sales
(
	id varchar(32) NOT NULL COMMENT '主键',
	commodity_code varchar(40) NOT NULL COMMENT '商品编码',
	sale_num int(11) DEFAULT 0 NOT NULL COMMENT '销售数量',
	statistics_date datetime NOT NULL COMMENT '统计日期',
	PRIMARY KEY (id)
) COMMENT = '日销量统计表';


-- 订单表
CREATE TABLE oms_order
(
	-- 主键
	id varchar(32) NOT NULL COMMENT '主键',
	order_no varchar(50) NOT NULL COMMENT '订单编号,该订单唯一标示',
	pay_order_no varchar(50) NOT NULL COMMENT '对应支付网关订单编号',
	-- 订单状态
	status varchar(50) NOT NULL COMMENT '订单状态',
	-- 支付类型
	pay_type varchar(50) NOT NULL COMMENT '支付类型',
	-- 订单购买类型
	buy_type varchar(50) NOT NULL COMMENT '订单购买类型',
	-- 下单会员ID
	member_id varchar(32) NOT NULL COMMENT '下单会员ID',
	-- 下单会员姓名
	member_name varchar(50) COMMENT '下单会员姓名',
	-- 下单会员电话
	member_phone varchar(30) COMMENT '下单会员电话',
	-- 购买数量
	buy_count int(11) COMMENT '购买数量',
	-- 关联合伙人ID
	partner_id varchar(32) NOT NULL COMMENT '关联合伙人ID',
	-- 订单总金额
	total_price decimal(13,2) NOT NULL COMMENT '订单总金额',
	-- 运费
	send_cost decimal(13,2) COMMENT '运费',
	-- 优惠金额
	-- 本订单使用的优惠券总金额（拆分的订单，是按比例的优惠金额之和)
	favorable_price decimal(13,2) NOT NULL COMMENT '优惠金额',
	-- 订单支付金额
	-- 该订单的所有商品的实付金额之和
	actual_price decimal(13,2) NOT NULL COMMENT '订单支付金额',
	-- 订单支付时间,
	-- 存支付网关返回的支付时间
	pay_time timestamp COMMENT '订单支付时间',
	-- 退单时间
	charge_back_time timestamp COMMENT '退单时间',
	-- 订单完成时间
	finish_time timestamp COMMENT '订单完成时间',
	-- 发货时间
	send_commodity_time timestamp COMMENT '发货时间',
	-- 预计发货时间
	pre_send_commodity_time timestamp COMMENT '预计发货时间',
	-- 最后打印时间
	last_print_time timestamp COMMENT '最后打印时间',
	-- 打印次数
	print_count int(11) COMMENT '打印次数',
	-- 绑定仓库ID
	storage_id varchar(32) COMMENT '绑定仓库ID',
	-- 配送员
	send_person varchar(30) COMMENT '配送员',
	-- 退单操作人
	charge_back_opr varchar(32) COMMENT '退单操作人',
	-- 订单所属省
	province varchar(30) COMMENT '订单所属省',
	-- 订单所属市
	city varchar(30) COMMENT '订单所属市',
	-- 订单所属区/县
	area varchar(50) COMMENT '订单所属区/县',
	-- 详细收货地址
	address_info varchar(300) COMMENT '详细收货地址',
	-- 收货人电话
	consign_phone varchar(30) COMMENT '收货人电话',
	-- 收货人姓名
	consign_name varchar(30) COMMENT '收货人姓名',
	-- 支付ID
	charge_id varchar(50) COMMENT '支付ID',
	-- 是否需要发票
	-- 1:是,0:否
	is_invoice varchar(2) COMMENT '是否需要发票1:是,0:否',
	-- 发票描述
	invoice_desc varchar(80) COMMENT '发票描述',
	-- 对应优惠券ID
	coupon_id varchar(32) COMMENT '对应优惠券ID',
	-- 对应优惠券名称
	coupon_name varchar(50) COMMENT '对应优惠券名称',
	-- 优惠券金额
	coupon_amount decimal(13,2) COMMENT '优惠券金额',
	-- 订单来源
	-- 0:android,1:ios,2:pc
	order_source varchar(2) COMMENT '订单来源0:android,1:ios,2:pc',
	-- 当前物流状态
	cur_loistics_status varchar(50) COMMENT '当前物流状态',
	-- 订单调拨状态
	out_order_status varchar(50) COMMENT '订单调拨状态',
	-- 关联活动编号
	activity_no varchar(50) COMMENT '关联活动编号',
	-- 订单类型
	-- 区分是普通订单or换货订单
	order_type varchar(30) COMMENT '订单类型',
	-- 备注
	remarks varchar(100) COMMENT '备注',
	-- 创建时间(下单时间)
	create_date timestamp COMMENT '创建时间(下单时间)',
	-- 创建者
	create_by varchar(32) COMMENT '创建者',
	-- 最后更新时间
	last_update_date timestamp COMMENT '最后更新时间',
	-- 最后更新人
	last_update_by varchar(32) COMMENT '最后更新人',
	-- 删除标记(0：正常；1：删除；2：审核)
	del_flag char COMMENT '删除标记(0：正常；1：删除；2：审核)',
	member_account varchar(50) COMMENT '下单会员账号',
	send_person_name varchar(50) COMMENT '配送员名称',
	service_point varchar(100) COMMENT '配送服务点',
	warehouse_code varchar(40) COMMENT '订单配送仓库编码',
	-- 订单是否装箱复核 0否 1是
	order_pack_status varchar(2) COMMENT '订单是否装箱复核',
	-- 合伙人编号
	partner_code varchar(50) COMMENT '合伙人编号',
	storage_name varchar(30) COMMENT '仓库名字',
	allocation_error varchar(2) COMMENT '订单分配错误标示',
	send_person_phone varchar(50) COMMENT '配送人电话',
	distribution_date datetime COMMENT '配送单确认收货时间',
	tran_time timestamp COMMENT '交易完成时间',
	PRIMARY KEY (id)
) COMMENT = '订单表';


-- oms_orderdetail
CREATE TABLE oms_orderdetail
(
	-- 主键
	id varchar(32) NOT NULL COMMENT '主键',
	-- 关联订单ID
	order_id varchar(32) NOT NULL COMMENT '关联订单ID',
	-- 关联订单编号
	order_no varchar(50) NOT NULL COMMENT '关联订单编号',
	-- 商品编号
	commodity_no varchar(50) COMMENT '商品编号',
	-- 商品标题
	commodity_title varchar(200) COMMENT '商品标题',
	-- 商品图片路径
	commodity_image varchar(200) COMMENT '商品图片路径',
	-- 商品条形码
	commodity_barcode varchar(50) COMMENT '商品条形码',
	-- 商品规格
	commodity_specifications varchar(50) COMMENT '商品规格',
	-- 商品单位
	commodity_unit varchar(20) COMMENT '商品单位',
	-- 商品单价
	commodity_price decimal(13,2) COMMENT '商品单价',
	-- 实际支付金额
	act_pay_amount decimal(13,2) COMMENT '实际支付金额',
	-- 购买数量
	buy_num int COMMENT '购买数量',
	-- 发货日期
	ready_send_time timestamp COMMENT '发货日期',
	-- 关联活动详情ID
	activity_commodity_item_id varchar(32) COMMENT '关联活动详情ID',
	-- 下单会员ID
	member_id varchar(32) COMMENT '下单会员ID',
	-- 订单商品状态 0：正常，1：退款处理中，2：换货处理中，3：退款成功，4：换货成功
	order_detail_status varchar(2) COMMENT '订单商品状态 0：正常，1：退款处理中，2：换货处理中，3：退款成功，4：换货成功',
	-- sku啥商品属性描述
	commodity_attribute_values varchar(200) COMMENT 'sku啥商品属性描述',
	-- 活动id
	activity_id varchar(32) COMMENT '活动id',
	-- skuId
	sku_id varchar(32) COMMENT 'skuId',
	-- sku商品编码
	sku_code varchar(50) COMMENT 'sku商品编码',
	-- 优惠金额(优惠券均分后的金额)
	favoured_amount decimal(13,2) COMMENT '优惠金额(优惠券均分后的金额)',
	warehouse_id varchar(32) COMMENT '仓库id',
	warehouse_code varchar(50) COMMENT '仓库编码',
	warehouse_name varchar(50) COMMENT '仓库名称',
	PRIMARY KEY (id)
) COMMENT = 'oms_orderdetail' DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


-- 订单操作记录表
CREATE TABLE oms_order_record
(
	id varchar(32) NOT NULL COMMENT '主键',
	description varchar(300) NOT NULL COMMENT '操作详情',
	opr_id varchar(32) COMMENT '操作人ID',
	opr_name varchar(30) COMMENT '操作人姓名',
	create_date timestamp NOT NULL COMMENT '创建时间',
	order_no varchar(50) NOT NULL COMMENT '关联订单编号',
	-- 主键
	order_id varchar(32) COMMENT '关联订单ID',
	-- 订单操作类型 0：售后状态 1：物流状态
	record_type varchar(10) COMMENT '订单操作类型',
	PRIMARY KEY (id)
) COMMENT = '订单操作记录表';


-- 销量汇总表
CREATE TABLE summary_sales
(
	id varchar(32) NOT NULL COMMENT '主键',
	commodity_code varchar(40) NOT NULL COMMENT '商品编号',
	sale_num int(11) DEFAULT 0 NOT NULL COMMENT '销售数量',
	update_date datetime COMMENT '更新时间',
	PRIMARY KEY (id)
) COMMENT = '销量汇总表';



/* Create Foreign Keys */

ALTER TABLE oms_order_record
	ADD FOREIGN KEY (order_id)
	REFERENCES oms_order (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



