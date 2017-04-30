

CREATE DATABASE  If Not Exists   csms_db  Character Set UTF8;
use csms_db;

CREATE TABLE  if not exists`chat_session` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `chat_no` varchar(30) DEFAULT NULL COMMENT '编号',
  `chat_status` varchar(10) DEFAULT NULL COMMENT '会话状态.0";会话中   1 回话结束',
  `im_member_account` varchar(30) DEFAULT NULL COMMENT '会员IM账号',
  `im_cs_account` varchar(30) DEFAULT NULL COMMENT 'IM客服账号',
  `chat_type` varchar(30) DEFAULT NULL COMMENT '聊天入口',
  `cs_phone` varchar(20) DEFAULT NULL COMMENT '客服手机号',
  `member_type` varchar(30) DEFAULT NULL COMMENT '会员类型(合伙人or普通)',
  `member_area` varchar(30) DEFAULT NULL COMMENT '会员所属地区',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '会话开始时间 : \r\n',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '会话结束时间',
  `bussiness_class` varchar(50) DEFAULT NULL COMMENT '业务分类',
  `bussiness_type` varchar(50) DEFAULT NULL COMMENT '业务类型Id',
  `build_work_order` varchar(10) DEFAULT NULL COMMENT '是否生成了工单(0:否,1:是)',
  PRIMARY KEY (`id`),
  KEY `customer_message_chat_no_index` (`chat_no`),
  KEY `chat_session_bussiness_type` (`bussiness_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天会话表';

-- ----------------------------
-- Table structure for chat_setting_other
-- ----------------------------
CREATE TABLE  if not exists`chat_setting_other` (
  `id` varchar(32) NOT NULL,
  `tip_title` varchar(100) DEFAULT NULL COMMENT '提示语标题',
  `tip` varchar(20) DEFAULT NULL COMMENT '提示语类型',
  `tip_content` varchar(100) DEFAULT NULL COMMENT '提示语',
  `conditions` varchar(200) DEFAULT NULL COMMENT '满足的条件',
  `remarks` varchar(100) DEFAULT NULL COMMENT '备注',
  `status` char(1) DEFAULT NULL COMMENT '是否勾选:0=否；1=是',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `last_update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_tip` (`tip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线客服-其他设置表';

-- ----------------------------
-- Table structure for common_language
-- ----------------------------
CREATE TABLE  if not exists`common_language` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `content` varchar(300) DEFAULT NULL COMMENT '内容',
  `chat_entrance` varchar(30) DEFAULT NULL COMMENT '留言入口',
  `sort_index` int(11) DEFAULT NULL COMMENT '排序',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `status` varchar(2) DEFAULT NULL COMMENT '状态(0:启用,1:禁用)',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服留言常用语';

CREATE TABLE  if not exists`customer_auto_conditions` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `auto_reply_id` varchar(32) DEFAULT NULL COMMENT '自动回复id',
  `message_entry` varchar(50) DEFAULT NULL COMMENT '留言入口',
  `type` varchar(2) DEFAULT NULL COMMENT '条件类型',
  `conditions` varchar(255) DEFAULT NULL COMMENT '条件信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自动回复的条件表';

CREATE TABLE  if not exists`customer_auto_reply` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `reply_content` varchar(1000) DEFAULT NULL COMMENT '自动回复内容',
  `message_entry` varchar(50) DEFAULT NULL COMMENT '留言入口',
  `effect_time` time DEFAULT NULL COMMENT '生效时间',
  `failure_time` time DEFAULT NULL COMMENT '失效时间',
  `only_reply` tinyint(1) DEFAULT NULL COMMENT '仅回复一次',
  `reply_status` varchar(10) DEFAULT NULL COMMENT '0:禁用,1:启用,2:已删除',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_by_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `last_update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `last_update_name` varchar(50) DEFAULT NULL COMMENT '修改人名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自动回复设置表';

CREATE TABLE  if not exists`customer_member_management` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `employee_id` varchar(32) DEFAULT NULL COMMENT '部门id',
  `employee` varchar(1000) DEFAULT NULL COMMENT '部门',
  `phone` varchar(20) NOT NULL COMMENT '成员帐号',
  `huanxin_account` varchar(100) DEFAULT NULL COMMENT '卖场环信帐号',
  `huanxin_password` varchar(255) DEFAULT NULL COMMENT '环信密码',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '昵称 : 昵称',
  `headimgurl` varchar(250) DEFAULT NULL COMMENT '头像',
  `message_entry` varchar(50) DEFAULT NULL COMMENT '接待入口',
  `effect_time` time DEFAULT NULL COMMENT '上班时间',
  `failure_time` time DEFAULT NULL COMMENT '下班时间',
  `max_receive` int(13) DEFAULT NULL COMMENT '最大接待人数',
  `reply_status` varchar(10) DEFAULT NULL COMMENT '0:禁用,1:启用,2:已删除',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_by_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `last_update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `last_update_name` varchar(50) DEFAULT NULL COMMENT '修改人名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='成员管理表';

CREATE TABLE  if not exists`customer_message` (
  `id` varchar(32) NOT NULL COMMENT '主键id',
  `chat_no` varchar(32) DEFAULT NULL COMMENT '会话编号',
  `msg_id` varchar(50) DEFAULT NULL COMMENT '消息id',
  `msg_type` varchar(50) DEFAULT NULL COMMENT '消息类型。txt: 文本消息；img: 图片；loc: 位置；audio: 语音',
  `chat_type` varchar(50) DEFAULT NULL COMMENT '用来判断单聊还是群聊。chat: 单聊；groupchat: 群聊',
  `user_from` varchar(50) DEFAULT NULL COMMENT '发送人username',
  `user_to` varchar(50) DEFAULT NULL COMMENT '接收人的username或者接收group的ID',
  `msg_content` varchar(255) DEFAULT NULL COMMENT '消息内容',
  `msg_ext` text COMMENT '扩展消息部分',
  `audio_length` int(11) DEFAULT NULL COMMENT '语音时长，单位为秒，这个属性只有语音消息有',
  `msg_url` varchar(355) DEFAULT NULL COMMENT '图片语音等文件的网络URL，图片和语音消息有这个属性',
  `filename` varchar(255) DEFAULT NULL COMMENT '文件名字，图片和语音消息有这个属性',
  `secret` varchar(255) DEFAULT NULL COMMENT '获取文件的secret，图片和语音消息有这个属性',
  `lat` varchar(50) DEFAULT NULL COMMENT '发送的位置的纬度，只有位置消息有这个属性',
  `lng` varchar(50) DEFAULT NULL COMMENT '位置经度，只有位置消息有这个属性',
  `addr` varchar(555) DEFAULT NULL COMMENT '位置消息详细地址，只有位置消息有这个属性',
  `send_date` datetime DEFAULT NULL COMMENT '消息发送时间',
  `create_date` timestamp NULL DEFAULT NULL COMMENT ' 记录创建时间',
  `timestamp_long` bigint(32) DEFAULT '0' COMMENT '时间毫秒数，根据这个时间的最大值获取数据',
  `local_fileurl` varchar(500) DEFAULT NULL COMMENT '本地图片url',
  PRIMARY KEY (`id`),
  KEY `customer_message_chat_no_index` (`chat_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天消息记录表';

CREATE TABLE  if not exists`customer_queue_accounts` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `job_num` varchar(255) DEFAULT NULL COMMENT '工号',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `account` varchar(50) DEFAULT NULL COMMENT '账号',
  `assign_id` varchar(32) DEFAULT NULL COMMENT '队列分配表id',
  `default_account` tinyint(4) DEFAULT NULL COMMENT '是否为默认账号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='队列分配的账号';

CREATE TABLE  if not exists`customer_queue_assign` (
  `id` varchar(32) NOT NULL DEFAULT '' COMMENT '主键id',
  `message_entry` varchar(50) DEFAULT NULL COMMENT '留言入口',
  `accounts_assign` varchar(600) DEFAULT NULL COMMENT '分配的账号',
  `account_default` varchar(50) DEFAULT NULL COMMENT '默认账号',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_by_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `last_update_by` varchar(32) DEFAULT NULL COMMENT '修改人',
  `last_update_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `last_update_name` varchar(50) DEFAULT NULL COMMENT '修改人名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `message_entry` (`message_entry`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='队列分配配置表';

CREATE TABLE  if not exists`im_member` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `im_account` varchar(30) DEFAULT NULL COMMENT 'IM账号',
  `im_pass_word` varchar(30) DEFAULT NULL COMMENT 'IM密码',
  `member_id` varchar(32) DEFAULT NULL COMMENT '会员ID',
  `member_phone` varchar(20) DEFAULT NULL COMMENT '会员手机号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `head_pic` varchar(150) DEFAULT NULL COMMENT '头像',
  `member_type` varchar(30) DEFAULT NULL COMMENT '合伙人or普通用户',
  `member_area` varchar(30) DEFAULT NULL COMMENT '客服所属地区',
  PRIMARY KEY (`id`),
  UNIQUE KEY `im_account` (`im_account`),
  UNIQUE KEY `member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员环信账号表';

CREATE TABLE  if not exists`im_service` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `im_account` varchar(30) DEFAULT NULL COMMENT 'im账号',
  `im_pass_word` varchar(32) DEFAULT NULL COMMENT '密码',
  `cs_sys_account` varchar(30) DEFAULT NULL COMMENT '客服系统账号(成员账号)',
  `cs_phone` varchar(20) DEFAULT NULL COMMENT '客服手机号',
  `cs_sys_id` varchar(32) DEFAULT NULL COMMENT '客服系统ID',
  `cs_scope` varchar(50) DEFAULT NULL COMMENT '业务范围(受理 订单,商品,麦场客服 咨询)',
  `head_pic` varchar(150) DEFAULT NULL COMMENT '头像',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `cs_dept_id` varchar(32) DEFAULT NULL COMMENT '部门ID',
  `cs_dept_name` varchar(30) DEFAULT NULL COMMENT '部门名称',
  `cs_company_id` varchar(32) DEFAULT NULL COMMENT '公司ID',
  `cs_company_name` varchar(30) DEFAULT NULL COMMENT '公司名称',
  `max_reception` int(11) DEFAULT '0' COMMENT '最大接待量',
  `cur_reception` int(11) DEFAULT '0' COMMENT '当前接待量',
  `work_date` time NOT NULL COMMENT '上班时间',
  `off_work_date` time NOT NULL COMMENT '下班时间',
  `online` int(1) NOT NULL DEFAULT '1' COMMENT '是否在线0不在线1在线',
  PRIMARY KEY (`id`),
  UNIQUE KEY `im_account` (`im_account`),
  UNIQUE KEY `cs_sys_account` (`cs_sys_account`),
  UNIQUE KEY `cs_sys_id` (`cs_sys_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服环信账号表';

CREATE TABLE  if not exists`test_table` (
  `id` varchar(30) NOT NULL COMMENT '实体 id',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  if not exists`work_order` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `work_order_no` varchar(50) NOT NULL COMMENT '工单编号',
  `work_order_status` varchar(10) DEFAULT NULL COMMENT '0:待分派,1:待处理,2:已处理,3:已完结,4:已删除',
  `work_order_type` varchar(50) DEFAULT NULL COMMENT '工单类型',
  `work_order_content` varchar(1000) DEFAULT NULL COMMENT '工单内容',
  `work_order_title` varchar(300) DEFAULT NULL COMMENT '工单标题',
  `bussiness_class` varchar(50) DEFAULT NULL COMMENT '业务大类',
  `bussiness_type` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `urgency_level` varchar(50) DEFAULT NULL COMMENT '紧急程度',
  `oms_order_no` varchar(50) DEFAULT NULL COMMENT '销售订单号',
  `oms_order_create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '销售订单创建时间',
  `after_sale_no` varchar(50) DEFAULT NULL COMMENT '售后申请单号',
  `bussiness_people_name` varchar(30) DEFAULT NULL COMMENT '业务人名称',
  `bussiness_people_phone` varchar(30) DEFAULT NULL COMMENT '业务人电话',
  `expected_time` decimal(11,2) DEFAULT NULL COMMENT '预计时长',
  `service_way` varchar(50) DEFAULT NULL COMMENT '服务方式',
  `service_property` varchar(50) DEFAULT NULL COMMENT '服务性质',
  `customer_flag` varchar(2) DEFAULT NULL COMMENT '是否下单客户(0:是,1:不是)',
  `assign_company_name` varchar(100) DEFAULT NULL COMMENT '指派公司名称',
  `assign_company_id` varchar(32) DEFAULT NULL COMMENT '指派公司ID',
  `assign_dep_name` varchar(100) DEFAULT NULL COMMENT '指派部门名称',
  `assign_dep_id` varchar(32) DEFAULT NULL COMMENT '指派部门ID',
  `assign_person_name` varchar(50) DEFAULT NULL COMMENT '指派人姓名',
  `assign_person_id` varchar(32) DEFAULT NULL COMMENT '指派人ID',
  `complaint_company_name` varchar(100) DEFAULT NULL COMMENT '被投诉公司名称',
  `complaint_company_id` varchar(32) DEFAULT NULL COMMENT '被投诉公司ID',
  `complaint_dep_name` varchar(100) DEFAULT NULL COMMENT '被投诉部门名称',
  `complaint_dep_id` varchar(32) DEFAULT NULL COMMENT '被投诉部门ID',
  `complaint_person_name` varchar(50) DEFAULT NULL COMMENT '被投诉人',
  `complaint_person_phone` varchar(20) DEFAULT NULL COMMENT '被投诉人电话',
  `complaint_person_id` varchar(32) DEFAULT NULL COMMENT '被投诉人ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '客户ID',
  `customer_name` varchar(30) DEFAULT NULL COMMENT '客户姓名',
  `customer_phone` varchar(30) DEFAULT NULL COMMENT '客户手机号',
  `customer_city_id` varchar(32) DEFAULT NULL COMMENT '客户区县(下单)',
  `customer_city_name` varchar(50) DEFAULT NULL COMMENT '客户区县',
  `customer_gender` varchar(10) DEFAULT NULL COMMENT '客户性别',
  `partner_flag` varchar(2) DEFAULT NULL COMMENT '是否合伙人(0:是,1:否)',
  `customer_register_date` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '客户注册时间',
  `customer_address` varchar(200) DEFAULT NULL COMMENT '客户地址',
  `create_by` varchar(32) DEFAULT NULL COMMENT '制单人',
  `create_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `create_by_name` varchar(50) DEFAULT NULL COMMENT '创建人名称',
  `comment_content` varchar(1000) DEFAULT NULL COMMENT '客户评价内容',
  `comment_tags` varchar(500) DEFAULT NULL COMMENT '客户评价标签',
  `assign_date` timestamp NULL DEFAULT NULL COMMENT '工单派发时间',
  `finish_date` timestamp NULL DEFAULT NULL COMMENT '工单结单时间',
  `assign_parent_ids` varchar(660) DEFAULT NULL COMMENT '指派部门上级ID组合',
  PRIMARY KEY (`id`),
  UNIQUE KEY `work_order_no` (`work_order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客服工单';

CREATE TABLE  if not exists`work_order_business_type` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `business_code` varchar(50) DEFAULT NULL COMMENT '业务编码',
  `business_name` varchar(50) DEFAULT NULL COMMENT '业务类型名称',
  `business_category` varchar(50) DEFAULT NULL COMMENT '业务分类',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `business_status` char(1) DEFAULT NULL COMMENT '状态:0=删除；1=禁用；2=启用',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `last_update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  if not exists`work_order_commodity` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `work_order_no` varchar(50) NOT NULL COMMENT '关联工单编码',
  `per_index` int(11) DEFAULT NULL COMMENT '序号',
  `commodity_barcode` varchar(50) DEFAULT NULL COMMENT '商品条形码',
  `commodity_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `commodity_attrs` varchar(200) DEFAULT NULL COMMENT '商品组合属性',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单商品信息';

CREATE TABLE  if not exists`work_order_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `work_order_no` varchar(50) NOT NULL COMMENT '关联工单编号',
  `work_name` varchar(300) DEFAULT NULL COMMENT '业务名称',
  `content` varchar(800) DEFAULT NULL COMMENT '内容',
  `record_type` varchar(30) DEFAULT NULL COMMENT '操作类型',
  `operator_company_id` varchar(32) DEFAULT NULL COMMENT '操作人公司id',
  `operator_company` varchar(100) DEFAULT NULL COMMENT '操作人公司',
  `operator_dep_id` varchar(32) DEFAULT NULL COMMENT '操作人部门id',
  `operator_dep` varchar(100) DEFAULT NULL COMMENT '操作人部门',
  `operator_name` varchar(50) DEFAULT NULL COMMENT '操作人',
  `duration` varchar(32) DEFAULT NULL COMMENT '占用时长',
  `operator_id` varchar(32) DEFAULT NULL COMMENT '操作人ID',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '记录创建时间',
  `operate_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工单操作记录表';

CREATE TABLE  if not exists`work_order_setting_other` (
  `id` varchar(32) NOT NULL COMMENT '主键ID',
  `apply_module_code` varchar(50) DEFAULT NULL COMMENT '应用编码',
  `apply_module_name` varchar(50) DEFAULT NULL COMMENT '应用名称',
  `apply_value` varchar(10) DEFAULT NULL COMMENT '值',
  `sort` int(4) DEFAULT NULL COMMENT '排序',
  `apply_status` char(2) DEFAULT NULL COMMENT '状态:0=删除；1=禁用；2=启用',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `is_default` char(1) DEFAULT NULL COMMENT '是否默认:0=否;1=是',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建者',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建日期',
  `last_update_by` varchar(50) DEFAULT NULL COMMENT '更新者',
  `last_update_date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Procedure structure for add_column_proc
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_column_proc`;
DELIMITER ;;
CREATE  PROCEDURE `add_column_proc`(t_name varchar(100),col_name varchar(100),datatype varchar(100),remark varchar(255))
BEGIN
    DECLARE  CurrentDatabase VARCHAR(100);
    DECLARE  sql_str VARCHAR(1000);
    SELECT DATABASE() INTO CurrentDatabase;
    if not EXISTS(select 1 FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=CurrentDatabase AND table_name=t_name AND COLUMN_NAME=col_name) then
        -- 要执行的字段新增
        set @sql_str=concat('ALTER TABLE ',t_name ,' ADD COLUMN ' ,col_name,' ', datatype,' DEFAULT NULL  NULL   COMMENT \'',remark,'\'');
        -- set @sql_str='ALTER TABLE brand ADD COLUMN test varchar(10) DEFAULT NULL  NULL   COMMENT \'test\'';
        -- EXECUTE sql_str;
        PREPARE st from @sql_str;
        EXECUTE st;
        DEALLOCATE PREPARE st;
    end if;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for add_index_proc
-- ----------------------------
DROP PROCEDURE IF EXISTS `add_index_proc`;
DELIMITER ;;
CREATE   PROCEDURE `add_index_proc`(t_name varchar(100),_index_name varchar(100),_index_type varchar(100),_index_method varchar(100),col_names varchar(100))
BEGIN
	DECLARE  CurrentDatabase VARCHAR(100);
	DECLARE  sql_str VARCHAR(1000);
	DECLARE  default_index_type VARCHAR(100) DEFAULT 'index';
	DECLARE  flag int;
	
	SELECT DATABASE() INTO CurrentDatabase;
	SELECT 1  FROM information_schema.statistics WHERE table_schema=CurrentDatabase AND table_name = t_name AND index_name = _index_name ;
	select CurrentDatabase,t_name,_index_name,flag;
	if not EXISTS(SELECT 1  FROM information_schema.statistics WHERE table_schema=CurrentDatabase AND table_name = t_name AND index_name = _index_name ) then 
		set @default_index_type='index';
		set @sql_str=CONCAT('ALTER TABLE ',t_name);
		if _index_type!= '' THEN
			set @default_index_type=CONCAT(_index_type,' ',@default_index_type);
		end if;
		
		set @sql_str=concat(@sql_str,' add ',@default_index_type,' ',_index_name,'(',col_names,')');
		
	SELECT @sql_str,_index_type,default_index_type,col_names;
	
		if _index_method!= '' THEN
			set @sql_str=CONCAT(@sql_str,' using ',_index_method);
		end if;
	
		
		PREPARE st from @sql_str;
		EXECUTE st;
		DEALLOCATE PREPARE st;

	end if;

END
;;
DELIMITER ;


ALTER  TABLE work_order  modify
`customer_id`  varchar(32) DEFAULT NULL COMMENT '客户ID维护有纰漏，请取客户phone';