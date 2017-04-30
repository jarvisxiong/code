-- 在线客服-其他设置表 初始化数据
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('00018848dfd8457aac383bda70f701et', '问候语', 'tip_greet', '', '', '客户发起新会话时，将在发送首条消息之后收到系统自动发送的问候语', '0', 'admin', '2017-01-20 10:38:26', 'admin', '2016-12-13 16:32:07');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('00037769o85247e594b6013e1766bc47', '会话关闭提示', 'tip_close', '', '', '会话关闭（人为+系统）后，系统自动发出结束语', '0', 'admin', '2017-01-20 10:38:26', 'admin', '2016-12-13 16:32:07');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('00046372c5ea407a8791d2200faf3499', '下班提示语', 'tip_off_work', '', '', '客户接入时，如果当前处于下班时间，系统自动发出的提示语', '0', 'admin', '2017-01-20 10:38:26', 'admin', '2016-12-13 16:32:07');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('0007e08a1ef24a6abaff106632ec4d3d', '待接入用户超过最大接待量时，系统自动发送提示:', 'tip_queue', '', '', '', '0', 'admin', '2017-01-20 10:38:26', 'admin', '2016-12-13 16:32:07');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('bb329919d21d11e684f3408d5cce9d19', '商品详情入口：', 'tip_type_co', '0,0', '', NULL, '1', 'admin', '2017-01-20 10:38:52', 'admin', '2017-01-04 09:33:01');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('c03e5d31d21d11e684f3408d5cce9d19', '麦场客服入口：', 'tip_type_cu', '0,0', '', NULL, '1', 'admin', '2017-01-20 10:38:53', 'admin', '2017-01-04 09:33:10');
INSERT ignore INTO `csms_db`.`chat_setting_other` (`id`, `tip_title`, `tip`, `tip_content`, `conditions`, `remarks`, `status`, `create_by`, `create_date`, `last_update_by`, `last_update_date`) VALUES ('e56c4777d21d11e684f3408d5cce9d19', '订单详情入口：', 'tip_type_or', '0,0', '', NULL, '1', 'admin', '2017-01-20 10:38:56', 'admin', '2017-01-04 09:33:06');