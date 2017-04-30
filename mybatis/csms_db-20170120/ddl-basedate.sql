use `base_data_db`;

CREATE TABLE if not exists `partner_service_station`(
  `id` varchar(32) NOT NULL,
  `code` varchar(50) DEFAULT NULL COMMENT '系统编码',
  `my_code` varchar(50) DEFAULT NULL COMMENT '自编码',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `address_id` varchar(50) DEFAULT NULL COMMENT '地址id',
  `address_deal` varchar(100) DEFAULT NULL COMMENT '地址详细',
  `level` varchar(10) DEFAULT NULL COMMENT '等级',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `partner_id` varchar(32) DEFAULT NULL COMMENT '合伙人id',
  `partner_code` varchar(40) DEFAULT NULL COMMENT '合伙人code',
  `partner_name` varchar(40) DEFAULT NULL COMMENT '合伙人名称',
  `act_flag` varchar(2) DEFAULT NULL COMMENT '状态',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建人时间',
  `last_update_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `last_update_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE if not exists `partner_service_station_log` (
  `id` varchar(32) NOT NULL,
  `Service_Station_code` varchar(32) DEFAULT NULL COMMENT '服务站管理code',
  `partner_id` varchar(32) DEFAULT NULL COMMENT '合伙人id',
  `Partner_code` varchar(32) DEFAULT NULL COMMENT '合伙人工号',
  `Partner_name` varchar(32) DEFAULT NULL COMMENT '合伙人姓名',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建人时间',
  `last_update_by` varchar(32) DEFAULT NULL COMMENT '操作人',
  `last_update_date` datetime DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

