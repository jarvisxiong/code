SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS commodity;
DROP TABLE IF EXISTS commodity_sku;




/* Create Tables */

-- 商品表
CREATE TABLE commodity
(
	-- 商品id
	id varchar(32) NOT NULL COMMENT '商品id',
	-- 商品编码
	code varchar(32) NOT NULL COMMENT '商品编码',
	-- 商品名称
	name varchar(200) NOT NULL COMMENT '商品名称',
	-- 商品别名
	alias_name varchar(200) NOT NULL COMMENT '商品别名',
	-- 商品标题
	title varchar(200) NOT NULL COMMENT '商品标题',
	-- 商品副标题
	sub_title varchar(200) COMMENT '商品副标题',
	-- 商品类别id
	category_id varchar(32) NOT NULL COMMENT '商品类别id',
	-- 商品属性
	commodity_attribute_parameter varchar(2000) COMMENT '商品属性',
	-- 品牌id
	brand_id varchar(32) COMMENT '品牌id',
	-- 品牌名称
	brand_name varchar(100) COMMENT '品牌名称',
	-- 供应商id
	vendor_id varchar(40) NOT NULL COMMENT '供应商id',
	-- 供应商名称
	vendor_name varchar(100) COMMENT '供应商名称',
	-- 零售价
	retail_price decimal(20,2) NOT NULL COMMENT '零售价',
	-- 优惠价
	preferential_price varchar(50) NOT NULL COMMENT '优惠价',
	-- 商品计量单位id
	unit_id varchar(50) NOT NULL COMMENT '商品计量单位id',
	-- 是否区域性商品（0:是，1：否）
	area_category char COMMENT '是否区域性商品（0:是，1：否）',
	-- 0：无库存可购买；1：无库存不可购买
	stock_limit char(2) COMMENT '0：无库存可购买；1：无库存不可购买',
	-- 是否拆单（0：是；1：否）
	split_single char COMMENT '是否拆单（0：是；1：否）',
	-- 是否在供应商仓库（0：是;1:否）
	warehouse char COMMENT '是否在供应商仓库（0：是;1:否）',
	-- 常见的状态有,draft:草稿、un_publish:未发布、publish:发布等
	status varchar(40) COMMENT '常见的状态有,draft:草稿、un_publish:未发布、publish:发布等',
	-- 商品图标
	thumbnail varchar(2000) COMMENT '商品图标',
	-- 商品展示图
	small_image varchar(2000) COMMENT '商品展示图',
	-- 商品详情图
	big_image varchar(2000) COMMENT '商品详情图',
	-- 搜索关键词
	keyword varchar(600) COMMENT '搜索关键词',
	-- 是否热销,1:热销,0:不热销
	is_hot_sale char COMMENT '是否热销,1:热销,0:不热销',
	-- 是否新品,1:新品,0:非新品
	is_new char COMMENT '是否新品,1:新品,0:非新品',
	-- 发布时间
	publish_time datetime COMMENT '发布时间',
	-- 创建者
	create_by varchar(32) COMMENT '创建者',
	-- 创建时间
	create_date datetime COMMENT '创建时间',
	-- 更新者
	last_update_by varchar(32) COMMENT '更新者',
	-- 更新时间
	last_update_date datetime COMMENT '更新时间',
	-- 备注信息
	remarks varchar(500) COMMENT '备注信息',
	-- 实物是否入库(0:是，1：否)
	entity_storage char COMMENT '实物是否入库(0:是，1：否)',
	-- 购买类型
	buy_type char(40) COMMENT '购买类型',
	-- 商品计量单位名称
	unit_name varchar(50) COMMENT '商品计量单位名称',
	-- 商品计量单位组Id
	unit_group_id varchar(32) COMMENT '商品计量单位组Id',
	-- 销售属性名称组合,用逗号隔开
	sale_attribute_name text COMMENT '销售属性名称组合,用逗号隔开',
	-- 销售属性组合
	sale_attribute_ids text COMMENT '销售属性组合',
	-- sku编码
	sku_code varchar(40) COMMENT 'sku编码',
	-- 条形码
	bar_code varchar(50) COMMENT '条形码',
	PRIMARY KEY (id),
	UNIQUE (code)
) COMMENT = '商品表' DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;


-- 商品sku
CREATE TABLE commodity_sku
(
	-- sku id
	id varchar(32) NOT NULL COMMENT 'sku id',
	-- 商品编码
	commodity_code varchar(20) COMMENT '商品编码',
	-- sku code
	sku_code varchar(40) COMMENT 'sku code',
	-- 条形码
	barcode varchar(50) COMMENT '条形码',
	-- 销售属性值id组合,用逗号隔开
	commodity_attribute_value_ids text COMMENT '销售属性值id组合,用逗号隔开',
	-- 销售属性值组合
	commodity_attribute_values text COMMENT '销售属性值组合',
	-- 销售属性id组合
	commodity_attribute_ids text COMMENT '销售属性id组合',
	-- 销售属性组合
	commodity_attributes text COMMENT '销售属性组合',
	-- 是否显示，1:显示,0:不显示
	is_show char COMMENT '是否显示，1:显示,0:不显示',
	-- sku价格
	price decimal(10,2) COMMENT 'sku价格',
	-- 优惠价
	favourable_price decimal(10,2) COMMENT '优惠价',
	-- 扣点方案id
	discount_plan_id varbinary(32) COMMENT '扣点方案id',
	-- 排序
	sort int COMMENT '排序',
	-- 状态，预售:pre_sale,正常销售:nomal_sale
	status varchar(50) COMMENT '状态，预售:pre_sale,正常销售:nomal_sale',
	-- 创建者
	create_by varchar(32) COMMENT '创建者',
	-- 创建时间
	create_date datetime COMMENT '创建时间',
	-- 更新者
	last_update_by varchar(32) COMMENT '更新者',
	-- 更新时间
	last_update_date datetime COMMENT '更新时间',
	-- 备注信息
	remarks varchar(500) COMMENT '备注信息',
	-- 禁用状态,0启用1禁用
	act_flag varchar(2) COMMENT '禁用状态,0启用1禁用',
	-- 删除标记
	del_flag char DEFAULT '0' NOT NULL COMMENT '删除标记',
	PRIMARY KEY (id)
) COMMENT = '商品sku' DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;



