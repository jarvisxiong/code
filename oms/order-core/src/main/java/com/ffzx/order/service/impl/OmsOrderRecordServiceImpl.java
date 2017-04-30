package com.ffzx.order.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.order.api.vo.OmsOrderVo;
import com.ffzx.order.mapper.OmsOrderRecordMapper;
import com.ffzx.order.service.OmsOrderRecordService;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 
 * @author ffzx
 * @date 2016-05-10 14:04:50
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
@Service("omsOrderRecordService")
public class OmsOrderRecordServiceImpl extends BaseCrudServiceImpl implements OmsOrderRecordService {

    @Resource
    private OmsOrderRecordMapper omsOrderRecordMapper;

    @Override
    public CrudMapper init() {
        return omsOrderRecordMapper;
    }

	/* (non-Javadoc)
	 * @see com.ffzx.order.service.OmsOrderRecordService#findLoisticsStatusByOrderNos(java.util.List)
	 */
	@Override
	public List<OmsOrderVo> findLogisticsStatusByOrderNos(List<String> orderNoList) {
		return omsOrderRecordMapper.selectLogisticsStatusByOrderNos(orderNoList);
		
	}
}