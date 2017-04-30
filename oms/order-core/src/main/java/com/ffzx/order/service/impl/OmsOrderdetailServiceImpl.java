package com.ffzx.order.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.exception.ServiceException;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.api.dto.OmsOrderdetail;
import com.ffzx.order.mapper.OmsOrderdetailMapper;
import com.ffzx.order.service.OmsOrderdetailService;

/**
 * 
 * @author ffzx
 * @date 2016-05-11 12:45:52
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("omsOrderdetailService")
public class OmsOrderdetailServiceImpl extends BaseCrudServiceImpl implements OmsOrderdetailService {

    @Resource
    private OmsOrderdetailMapper omsOrderdetailMapper;

    @Override
    public CrudMapper init() {
        return omsOrderdetailMapper;
    }
   
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<OmsOrderdetail> selectJoinSku(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return omsOrderdetailMapper.selectJoinSkuByParams(params);
	}

	@Override
	public Integer selectBuyCount(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return omsOrderdetailMapper.selectBuyCount(params);
	}

	/**
	 * 
	 * 雷-----2016年8月30日 (非 Javadoc)
	 * <p>
	 * Title: getOrderdetailByCode
	 * </p>
	 * <p>
	 * Description: 去主库做查询（问题：同sku申请了一次售后成功，返回页面的查询可能有延时，导致再次申请）
	 * </p>
	 * 
	 * @param params
	 * @return
	 * @see com.ffzx.order.service.OmsOrderdetailService#getOrderdetailByCode(java.util.Map)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public OmsOrderdetail getOrderdetailByCode(Map<String, Object> params) {

		return this.omsOrderdetailMapper.selectByCode(params);
	}

	@Override
	public List<OmsOrderdetail> getOrderDetailList(Map<String,Object> params) {
		List<OmsOrderdetail> odList  =  omsOrderdetailMapper.selectByParams(params);
		return odList;
	}

	@Override
	public List<OmsOrderdetail> getdetailList(Map<String,Object> params) {
		List<OmsOrderdetail> odList  =  omsOrderdetailMapper.selectJoinOrderRemind(params);
		return odList;
	}

	@Override
	public List<OmsOrderdetail> getdetailListBySku(Map<String, Object> params) {
		List<OmsOrderdetail> odList  =  omsOrderdetailMapper.selectJoinOrderRemindGroupBySku(params);
		return odList;
	}

	@Override
	public List<OmsOrderdetail> findDetailToSendApp(String orderNo) {
		return omsOrderdetailMapper.findDetailToSendApp(orderNo);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void updateOrderDetaiStatus(String omsOrderDetailId, String status) throws ServiceException {
		try {
			OmsOrderdetail orderdetail = new OmsOrderdetail();
			orderdetail.setId(omsOrderDetailId);
			orderdetail.setOrderDetailStatus(status);
			this.omsOrderdetailMapper.updateByPrimaryKeySelective(orderdetail);
		} catch (Exception e) {
			logger.error("AftersaleApplyController.updateOrderDetaiStatus-Exception=》", e);
			throw new ServiceException(e);
		}
	}

	/**
	 * 雷-----2016年9月14日
	 * (非 Javadoc)
	 * <p>Title: getdetailByIDList</p>
	 * <p>Description: 根据主商品id查询赠品的订单明细</p>
	 * @param id
	 * @return
	 * @see com.ffzx.order.service.OmsOrderdetailService#getdetailByIDList(java.lang.String)
	 */
	@Override
	public List<OmsOrderdetail> getdetailByIDList(String mainCommodityId) {
		return this.omsOrderdetailMapper.getdetailByIDList(mainCommodityId);
	}
	
	/**
	 * 雷-----2016年9月18日
	 * (非 Javadoc)
	 * <p>Title: getOrderDetailListByApplyId</p>
	 * <p>Description: 根据售后申请单id查询售后订单明细</p>
	 * @param applyId
	 * @return
	 * @see com.ffzx.order.service.AftersaleApplyItemService#getOrderDetailListByApplyId(java.lang.String)
	 */
	@Override
	public List<OmsOrderdetail> getOrderDetailListByApplyId(String applyId) {
		return  this.omsOrderdetailMapper.getOrderDetailListByApplyId(applyId);
	}

	/**
	 * 雷-----2016年9月19日
	 * (非 Javadoc)
	 * <p>Title: updateStatusByPrimaryKey</p>
	 * <p>Description: 根据id修改订单明细的状态</p>
	 * @param detail
	 * @see com.ffzx.order.service.OmsOrderdetailService#updateStatusByPrimaryKey(com.ffzx.order.api.dto.OmsOrderdetail)
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public int updateStatusByPrimaryKey(OmsOrderdetail detail) throws ServiceException {
		return this.omsOrderdetailMapper.updateStatusByPrimaryKey(detail);
	}

	/***
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月26日 下午6:09:57
	 * @version V1.0
	 * @return 
	 */
	@Override
	public int updateSettlement(Map<String, Object> params) {
		return this.omsOrderdetailMapper.updateSettlement(params);
	}

	/***
	 * 
	 * @param params
	 * @return
	 * @author yinglong.huang
	 * @email yinglong.huang@ffzxnet.com
	 * @date 2017年3月27日 下午5:13:01
	 * @version V1.0
	 * @return 
	 */
	@Override
	public List<OmsOrderdetail> selectByCountPriceSettlment(Map<String, Object> params) {
		return this.omsOrderdetailMapper.selectByCountPriceSettlment(params);
	}
}