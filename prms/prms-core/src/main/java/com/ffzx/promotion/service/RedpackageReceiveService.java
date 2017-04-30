package com.ffzx.promotion.service;

import java.util.List;

import com.ffzx.commerce.framework.page.Page;
import com.ffzx.commerce.framework.service.BaseCrudService;
import com.ffzx.promotion.api.dto.Redpackage;
import com.ffzx.promotion.api.dto.RedpackageReceive;

/**
 * redpackage_receive数据库操作接口
 * 
 * @author ffzx
 * @date 2016-11-07 09:41:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
public interface RedpackageReceiveService extends BaseCrudService {


	public List<Redpackage> findList(Page pageObj,RedpackageReceive redpackageReceive);
}