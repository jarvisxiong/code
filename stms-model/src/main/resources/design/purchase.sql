SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS purchase_sku;
DROP TABLE IF EXISTS purchase;
DROP TABLE IF EXISTS purchase_return;
DROP TABLE IF EXISTS vendor_commodity_setup;




/* Create Tables */

-- 采购单表
CREATE TABLE purchase
(
	id varchar(32) NOT NULL COMMENT '主键',
	purchase_code varchar(40) NOT NULL COMMENT '采购单号',
	purchase_status char(2) NOT NULL COMMENT '采购单状态 0:待提交,1:待重新提交,2:待审核,3:已审核',
	close_flag char(1) NOT NULL COMMENT '关闭状态(0:正常,1:已关闭)',
	purchase_type char(1) COMMENT '采购模式(0:联营促销,1:联营)',
	department_id varchar(32) COMMENT '采购部门id',
	department_name varchar(50) COMMENT '采购部门名称',
	buyer_id varchar(32) COMMENT '采购员ID',
	buyer_name varchar(30) COMMENT '采购员姓名',
	puchase_date datetime COMMENT '采购日期',
	warehose_code varchar(40) NOT NULL COMMENT '收货仓库编码',
	warehose_address varchar(200) COMMENT '仓库地址',
	vendor_number varchar(40) NOT NULL COMMENT '供应商编码',
	vendor_name varchar(100) NOT NULL COMMENT '供应商名称',
	purchase_source char(2) NOT NULL COMMENT '采购单来源 0:手工新增,1:销售订单,2:退货单',
	remarks varchar(300) COMMENT '备注',
	create_by varchar(32) COMMENT '创建人ID',
	create_date datetime COMMENT '创建日期',
	last_update_date datetime COMMENT '最后更新时间',
	last_update_by varchar(32) COMMENT '最后更新人',
	del_flag char(1) NOT NULL COMMENT '"删除标记(0：正常；1：删除；2：审核)"',
	PRIMARY KEY (id)
) COMMENT = '采购单表';


-- 采购退货单
CREATE TABLE purchase_return
(
	id varchar(32) NOT NULL COMMENT '主键',
	department_id varchar(32) NOT NULL COMMENT '采购部门ID',
	department_name varchar(50) NOT NULL COMMENT '采购部门名称',
	buyer_id varchar(32) NOT NULL COMMENT '采购员ID',
	buyer_name varchar(30) NOT NULL COMMENT '采购员名称',
	return_date datetime COMMENT '退货日期',
	warehouse_code varchar(40) NOT NULL COMMENT '仓库编码',
	warehouse_address varchar(200) COMMENT '仓库地址',
	return_type char(2) NOT NULL COMMENT '退货类型(0:库存退货(良品),1:库存退货(不良品),2:检验退货)',
	return_way char(2) NOT NULL COMMENT '退货方式(0:退货补货 (审核通过生成对应采购单),1:退货扣款) ',
	restock_way char(2) NOT NULL COMMENT '补货方式(0:源订单补货,1:创建补货订单)',
	return_status char(2) NOT NULL COMMENT '采购退货单状态 0:待退货,1:退货中,2:退货完成',
	vendor_number varchar(40) NOT NULL COMMENT '供应商编码',
	vendor_name varchar(100) NOT NULL COMMENT '供应商名称',
	source_type char(2) NOT NULL COMMENT '源单类型',
	source_number varchar(40) COMMENT '源单单号(关联质检单单号)',
	purchase_number varchar(50) COMMENT '关联采购单号',
	remarks varchar(200) COMMENT '退货原因',
	create_by varchar(32) COMMENT '创建人',
	create_date datetime COMMENT '创建时间',
	last_update_by varchar(32) COMMENT '最后更新人',
	last_update_date datetime COMMENT '最后更新时间',
	del_flag char(1) COMMENT '删除标记(0：正常；1：删除；2：审核)',
	PRIMARY KEY (id)
) COMMENT = '采购退货单';


-- 采购单明细表
CREATE TABLE purchase_sku
(
	id varchar(32) NOT NULL COMMENT '主键',
	sku_code varchar(40) NOT NULL COMMENT '关联sku编码',
	serial_number int(11) COMMENT '序号(sku排序)',
	purchase_num int(11) NOT NULL COMMENT '采购数量',
	purchase_price decimal(13,2) NOT NULL COMMENT '采购单价',
	purchase_amount decimal(13,2) COMMENT '采购金额(单价*数量)',
	tax_rate decimal(13,2) COMMENT '税率(供应商那边自动带出,可手动更改)',
	tax_amount decimal(13,2) COMMENT '税额',
	total_amount decimal(13,2) COMMENT '价税合计',
	delivery_date datetime COMMENT '交货日期',
	delivery_num int(11) COMMENT '交货数量',
	expect_arrival_date datetime COMMENT '预计到货日期',
	bus_close_status char(1) NOT NULL COMMENT '业务关闭状态(0:正常,1:关闭)',
	purchase_code varchar(32) NOT NULL COMMENT '关联采购单号',
	PRIMARY KEY (id)
) COMMENT = '采购单明细表';


-- 供应商商品设置表
CREATE TABLE vendor_commodity_setup
(
	id varchar(32) NOT NULL COMMENT '主键',
	commodity_id varchar(32) NOT NULL COMMENT '商品ID',
	commodity_code varchar(40) NOT NULL COMMENT '商品编码',
	vendor_id varchar(32) NOT NULL COMMENT '供应商ID',
	vendor_number varchar(50) NOT NULL COMMENT '供应商编码',
	vendor_name varchar(50) NOT NULL COMMENT '供应商名称',
	delivery_cycle int(11) COMMENT '送货周期',
	lead_time int(11) COMMENT '提前期',
	purchase_batch int(11) COMMENT '采购批量',
	least_order_num int(11) COMMENT '最小订单量',
	create_by varchar(32) COMMENT '创建人ID',
	create_date datetime COMMENT '创建时间',
	last_update_date datetime COMMENT '最后更新时间',
	last_update_by varchar(32) COMMENT '最后更新人ID',
	PRIMARY KEY (id)
) COMMENT = '供应商商品设置表';



/* Create Foreign Keys */

ALTER TABLE purchase_sku
	ADD FOREIGN KEY (purchase_code)
	REFERENCES purchase (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



