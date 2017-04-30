/**   
* @Title: IpAddrUtil.java 
* @Package cn.fuego.common.util.format 
* @Description: TODO
* @author Tang Jun   
* @date 2015-4-22 下午5:12:14 
* @version V1.0   
*/ 
package com.ff.common.util.format;

 /** 
 * @ClassName: IpAddrUtil 
 * @Description: TODO
 * @author Tang Jun
 * @date 2015-4-22 下午5:12:14 
 *  
 */
public class IpAddrUtil
{
	

	public static String getHostIpByMask(String ip,String mask)
	{
		long ipInt = ipToLong(ip);
		long maskInt = ipToLong(mask);
		long netSeg = ipInt&maskInt;
		
		return longToIP(netSeg);
	}
	
	
	public static long ipToLong(String strIp)
	{
		 long[] ip = new long[4];  
	        //先找到IP地址字符串中.的位置  
	        int position1 = strIp.indexOf(".");  
	        int position2 = strIp.indexOf(".", position1 + 1);  
	        int position3 = strIp.indexOf(".", position2 + 1);  
	        //将每个.之间的字符串转换成整型  
	        ip[0] = Long.parseLong(strIp.substring(0, position1));  
	        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));  
	        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));  
	        ip[3] = Long.parseLong(strIp.substring(position3+1));  
	        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];  
	}
	
	//将十进制整数形式转换成127.0.0.1形式的ip地址  
    public static String longToIP(long longIp)
    {  
        StringBuffer sb = new StringBuffer("");  
        //直接右移24位  
        sb.append(String.valueOf((longIp >>> 24)));  
        sb.append(".");  
        //将高8位置0，然后右移16位  
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));  
        sb.append(".");  
        //将高16位置0，然后右移8位  
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));  
        sb.append(".");  
        //将高24位置0  
        sb.append(String.valueOf((longIp & 0x000000FF)));  
        return sb.toString();  
    }  
	
	 /**
     * 转换为验码位数
     */
    public static int getNetMaskLength(String netmarks)
    {
        StringBuffer sbf;
        String str;
        int inetmask=0,count=0;
        String[] ipList=netmarks.split("\\.");
        for(int n=0;n<ipList.length;n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str=sbf.reverse().toString();
            count=0;
            for(int i=0;i<str.length();i++){
                i=str.indexOf('1',i);            
                if(i==-1){break;}
                count++;
            }
            inetmask+=count;
        }
        return inetmask;
    }
    
    private static StringBuffer toBin(int x)
    {
        StringBuffer result=new StringBuffer();
        result.append(x%2);
        x/=2;
        while(x>0){
            result.append(x%2);
            x/=2;
        }
        return result;
    }


}
