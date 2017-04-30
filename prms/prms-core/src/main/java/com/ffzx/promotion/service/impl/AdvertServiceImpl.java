package com.ffzx.promotion.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.constant.RedisConstant;
import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.commerce.framework.utils.DateUtil;
import com.ffzx.commerce.framework.utils.RedisUtil;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.promotion.api.dto.Advert;
import com.ffzx.promotion.api.enums.AdvertStatus;
import com.ffzx.promotion.mapper.AdvertMapper;
import com.ffzx.promotion.service.AdvertService;


/**
 * @className AdvertServiceImpl
 *
 * @author hyl
 * @date 2016-05-03 17:18:53
 * @version 1.0.0
 */
@Service("advertService")
public class AdvertServiceImpl extends BaseCrudServiceImpl implements AdvertService {

	@Resource
	private RedisUtil redisUtil;
	
	@Resource
	private AdvertMapper advertMapper;
	
	@Override
	public CrudMapper init() {
		return advertMapper;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public int autoUpdateStatus() throws ServiceException {
		// TODO Auto-generated method stub		
			String key = "PRMS__autoUpdateStatus";
			if(!redisUtil.exists(key)){
			redisUtil.set(key, "YES", 60L);
			logger.info("更新广告数据==》》begain:"+DateUtil.formatDateTime(new Date()));
			List<Advert> advertList = this.advertMapper.selectByUpdateStatus(); 
			Date now = new Date();			
			if(advertList!=null&&advertList.size()>0){
				for(int i=0;i<advertList.size();i++){
					Advert ad_1 = advertList.get(i);
					List<Advert> advertList_update =  new ArrayList<Advert>();
					if(ad_1.getRegion()!=null){
					if(i<(advertList.size()-1)){	
					Advert ad_2 = advertList.get(i+1);
					if(ad_1.getRegion().getId().equals(ad_2.getRegion().getId())&&ad_1.getLocationIndex().intValue()==ad_2.getLocationIndex().intValue()){
							if(now.compareTo(ad_2.getStartDate())>=0){
								ad_1.setStatus(AdvertStatus.END);
								ad_1.setIsBackup(1);
								ad_2.setStatus(AdvertStatus.STARTTING);
								ad_2.setIsBackup(0);
								advertList_update.add(ad_1);
								advertList_update.add(ad_2);
								this.updateBatch(advertList_update);
							}
					}else if(now.compareTo(ad_1.getStartDate())>=0){
						ad_1.setStatus(AdvertStatus.STARTTING);
						this.modifyById(ad_1);
					}
					}else if(now.compareTo(ad_1.getStartDate())>=0){
						ad_1.setStatus(AdvertStatus.STARTTING);
						this.modifyById(ad_1);
					}
				}
			  }
			}
			logger.info("更新广告数据==》》end:"+DateUtil.formatDateTime(new Date()));
			}
		return 0;
	}	
	@Transactional(rollbackFor=Exception.class)
	public void updateBatch(List<Advert> advertList) throws ServiceException{
		for ( Advert advert : advertList) {
			this.modifyById(advert);
		}
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateAdvertDate(Advert advert, Advert alternate) throws ServiceException {
		logger.info("删除广告数据=================");
		// 删除广告
		this.deleteById(advert);
		// 判断广告是否是替补广告
		if (advert.getIsBackup() == null || 0 == advert.getIsBackup()) {
			if (StringUtil.isNotNull(alternate)) {
				logger.info("修改替补广告为原始广告=================");
				// 设置广告状态（是否是替补广告：1：是，0否）
				alternate.setIsBackup(0);
				// 修改广告
				this.modifyById(alternate);
			}
		}
	}
}