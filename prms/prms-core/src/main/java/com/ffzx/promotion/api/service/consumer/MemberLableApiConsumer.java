package com.ffzx.promotion.api.service.consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.member.api.dto.MemberLabel;
import com.ffzx.member.api.service.MemberLabelApiService;
import com.ffzx.promotion.util.AppMapUtils;

 /**
 * @Description: TODO
 * Member office dubbo消费者
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月5日 下午6:25:13
 * @version V1.0 
 *
 */
@Service("memberLableApiConsumer")
public class MemberLableApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(MemberLableApiConsumer.class);

	@Autowired
	private MemberLabelApiService memberLabelApiService;
	/**
	 * 返回会员信息
	 * @param memberParams
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @return
	 * @throws ServiceException
	 */
	public List<Object>  selectLabelByParams(MemberLabel memberLabel,Page page, String orderByField, String orderBy) throws ServiceException{
		Map<String, Object> params=AppMapUtils.toMapObjcet(memberLabel);
		ResultDto resDto = memberLabelApiService.selectLabelByParams(page, orderByField, orderBy, params);
			Page returnpage = (Page) resDto.getData();
			page.setTotalCount(returnpage.getTotalCount());
			return (List<Object>) returnpage.getRecords();
	}
	
	/**
	 * 返回会员信息列表
	 * @param memberParams
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @return
	 * @throws ServiceException
	 */
	public List<MemberLabel>  selectLabelByParams(List<String> ids) throws ServiceException{
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("ids", ids);
		ResultDto resDto = memberLabelApiService.selectLabelListByLabelIds(ids);
		return (List<MemberLabel>) resDto.getData();
	}

}
