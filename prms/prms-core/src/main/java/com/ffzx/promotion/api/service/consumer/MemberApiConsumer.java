package com.ffzx.promotion.api.service.consumer;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.page.Page;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.promotion.util.AppUtils;

 /**
 * @Description: TODO
 * Member office dubbo消费者
 * @author yuzhao.xu
 * @email  yuzhao.xu@ffzxnet.com
 * @date 2016年5月5日 下午6:25:13
 * @version V1.0 
 *
 */
@Service("memberApiConsumer")
public class MemberApiConsumer {
	// 记录日志
	private final static Logger logger = LoggerFactory.getLogger(MemberApiConsumer.class);

	@Autowired
	private MemberApiService memberApiService;
	
	/**
	 * 跟进手机号获取单个会员
	 */
	public Member getMember(String phone) throws ServiceException{
		ResultDto resultDto=memberApiService.getMember(phone);
		if (resultDto.getCode().equals(ResultDto.OK_CODE)) {
			Member member=(Member) resultDto.getData();
			if(member==null){
				return new Member();
			}
			return member;
		}
		return new Member();
	}
	/**
	 * 返回会员信息
	 * @param memberParams
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @return
	 * @throws ServiceException
	 */
	public List<Object>  getAllMember(Map<String, Object> memberParams,Page page, String orderByField, String orderBy) throws ServiceException{
		logger.debug("MemberApiConsumer-getObjectList=》dubbo调用：MemberApiConsumer.getAllMember - Start");
		ResultDto resDto = memberApiService.getAllMember(memberParams, page, orderByField, orderBy);
		logger.debug("MemberApiConsumer-getObjectList=》dubbo调用：MemberApiConsumer.getAllMember -",resDto.getMessage());
		List<Object> list = null;
		if (resDto.getCode().equals(ResultDto.OK_CODE)) {
			list = (List<Object>) resDto.getData();
		}
		logger.debug("MemberApiConsumer-getObjectList=》dubbo调用：MemberApiConsumer.getObjectList - End");
		return list;
	}
	/**
	 * 返回会员信息
	 * @param memberParams
	 * @param page
	 * @param orderByField
	 * @param orderBy
	 * @return
	 * @throws ServiceException
	 */
	public Set<String> getMemberByLabels(List<String> labelIds) throws ServiceException{
		Set<String> memberSet=null;
//		String ip="192.168.2.150";
//		memberApiService=AppUtils.superRpc(MemberApiService.class, AppUtils.UC, ip);
		ResultDto resultDto=memberApiService.getMemberByLabels(labelIds);
		if (resultDto.getCode().equals(ResultDto.OK_CODE)) {
			memberSet = (Set<String>) resultDto.getData();
		}else{
			throw new ServiceException(resultDto.getMessage()+" 获取标签下的会员异常");
		}
		logger.info("会员系统返回的数据"+(memberSet==null?"空":memberSet.size())+"条");
		return memberSet;
	}
	
	

}
