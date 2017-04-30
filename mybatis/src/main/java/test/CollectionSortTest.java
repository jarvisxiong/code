/**
 * 
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月12日 下午4:28:46
 * @version 1.0.0
 * @copyright www.ffzxnet.com
 */
package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.ffzx.order.api.dto.OmsOrderdetail;

/***
 * @author yinglong.huang
 * @email yinglong.huang@ffzxnet.com
 * @date 2017年4月12日 下午4:28:46
 * @version V1.0
 */
public class CollectionSortTest {
	public static void main(String[] args) throws Exception {
		 
		List<OmsOrderdetail> dataList = new ArrayList<OmsOrderdetail>();
		OmsOrderdetail obj1 = new OmsOrderdetail();
		obj1.setCommodityTitle("1");
		obj1.setBuyNum(1);
		obj1.setActSalePrice(new BigDecimal("1"));
		
		OmsOrderdetail obj2 = new OmsOrderdetail();
		obj2.setCommodityTitle("2");
		obj2.setBuyNum(2);
		obj2.setActSalePrice(new BigDecimal("2"));
		
		OmsOrderdetail obj3 = new OmsOrderdetail();
		obj3.setCommodityTitle("3");
		obj3.setActSalePrice(new BigDecimal("3"));
		obj3.setBuyNum(3);
		dataList.add(obj2);
		dataList.add(obj1);
		dataList.add(obj3);
		for (OmsOrderdetail data : dataList) {
			System.err.println("title:"+data.getCommodityTitle()+",buyNum:"+data.getBuyNum());
		}
		System.err.println("排序后");
		Collections.sort(dataList,new Comparator() {
			public int compare(Object arg0,Object arg1){
				OmsOrderdetail data0 = (OmsOrderdetail) arg0;
				OmsOrderdetail data1 = (OmsOrderdetail) arg1;
				//return data0.getBuyNum().compareTo(data1.getBuyNum());
				return data0.getActSalePrice().compareTo(data1.getActSalePrice());
			}
		});
	for (OmsOrderdetail data : dataList) {
		System.err.println("title:"+data.getCommodityTitle()+",buyNum:"+data.getBuyNum());
	}
	}
}
