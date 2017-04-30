package com.ffzx.promotion.io;

import java.util.concurrent.Callable;

import com.ffzx.commerce.framework.constant.Constant;
import com.ffzx.commerce.framework.dto.ResultDto;
import com.ffzx.commerce.framework.utils.StringUtil;
import com.ffzx.member.api.dto.Member;
import com.ffzx.member.api.service.MemberApiService;
import com.ffzx.promotion.service.impl.CouponGrantServiceImpl;
import com.ffzx.promotion.util.ExcelUtils;

public class MemberExcelCall extends CouponGrantServiceImpl  implements Callable<String> {

    private MemberApiService memberApiService;
	private int i;
	private String[] data;
	public MemberExcelCall(int i,String[] data,MemberApiService memberApiService){
		this.i=i;
		this.data=data;
		this.memberApiService=memberApiService;
		
	}

	public MemberExcelCall(){}

	private final String locksysn="MemberExcelCall-lock";
	private final String fenhao="：";
	private final String douhao=" ,";
	private final String htmlbr="<br />";
	
	private final String phone="手机账号";
	private final String di="第";
	private String rowString="行,";
	//验证结果，异常信息
	private final String nomemberException="不是会员";
	private final String jingyongException="已禁用";
//	private final String existsException="已存在";
	private final String repeatException="已重复";
	
	private final String getMessage(Integer row,String phoneString,String exception){
		return di+row+rowString+phone+fenhao+phoneString+exception+htmlbr;
	}

	private void phoneValidate(String phoneString, int row){
		
		ResultDto resultDto=memberApiService.getMember(phoneString);
		Member m=(Member) resultDto.getData();
		synchronized(locksysn){//多线程比较加入phoneList
			if(phoneList.contains(phoneString)){
				errorVaildate.append(getMessage(row,phoneString, repeatException));//重复
				failnum.incrementAndGet();
			}else if(m==null || StringUtil.isEmpty(m.getId())){
				failnum.incrementAndGet();
				errorVaildate.append(getMessage(row,phoneString, nomemberException));//不是会员
			}else if(m.getIsDisable().equals(Constant.YES)){
				failnum.incrementAndGet();
				errorVaildate.append(getMessage(row,phoneString, jingyongException));//是否禁用
			}else{
				successnum.incrementAndGet();
				String membermessage=m.getId()+","+trimString(m.getNickName())+","+m.getPhone();
				successDate.append(membermessage+";");
			}
			phoneList.add(phoneString);
		}
	}
	@Override
	public String call(){
		// TODO Auto-generated method stub

		try {
			 int col=0;//第几列
			 int row=i+2;//第几行
			if(!ExcelUtils.getIsSuccess(data)){
				return "success";
			}
			String phoneString= trimString(data[col]);
			//条形码验证
			phoneValidate(phoneString, row);
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			errorVaildate.append(e);
			logger.error("",e);
		}catch(Exception e){
			errorVaildate.append(e);
			logger.error("",e);
		}
		return "success";
	}
	/**
	 * 清除左右空格
	 * @param date
	 * @return
	 */
	public String trimString(String date){
		if(StringUtil.isEmpty(date))
			return "";
		return date.trim();
	}
	
}
