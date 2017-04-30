package com.ffzx.permission;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ffzx.commerce.framework.system.entity.SysMenu;
import com.ffzx.permission.mapper.SysMenuMapper;

/**
 * SysMenuTest
 * 
 * @author CMM
 *
 * 2016年5月30日 下午4:10:41
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-core.xml"})
public class SysMenuTest {
	
	@Autowired
	private SysMenuMapper sysMenuMapper;
	
	@Test
    public void testMenu() throws Exception{
		
		SysMenu menu = new SysMenu();
		menu.setDelFlag(null);
		menu.setParentIds("%\\%%");
		List<SysMenu> menuList = sysMenuMapper.getByParentIdsLike(menu);
		
		System.err.println("更新之前数据==================================================");
		for (SysMenu myMenu : menuList) {
			System.err.println(myMenu.getName() + " ==>> " + myMenu.getId() + " ==>> " + myMenu.getParentIds());
		}
		
		for (SysMenu myMenu : menuList) {
			if("0".equals(myMenu.getParentId())){ // myMenu为一级菜单
				myMenu.setParentIds("0,");
			}else{
				SysMenu parentMenu1 = sysMenuMapper.selectByPrimaryKey(myMenu.getParentId());
				if("0".equals(parentMenu1.getParentId())){ // myMenu为二级菜单
					myMenu.setParentIds("0," + parentMenu1.getId() + ",");
				}else {
					SysMenu parentMenu2 = sysMenuMapper.selectByPrimaryKey(parentMenu1.getParentId());
					if("0".equals(parentMenu2.getParentId())){ // myMenu为三级菜单
						myMenu.setParentIds("0," + parentMenu2.getId() + "," + parentMenu1.getId() + ",");
					}else {
						SysMenu parentMenu3 = sysMenuMapper.selectByPrimaryKey(parentMenu2.getParentId());
						if("0".equals(parentMenu3.getParentId())){ // myMenu为四级菜单
							myMenu.setParentIds("0," + parentMenu3.getId() + "," + parentMenu2.getId() + "," + parentMenu1.getId() + ",");
						}else {
							SysMenu parentMenu4 = sysMenuMapper.selectByPrimaryKey(parentMenu3.getParentId());
							if("0".equals(parentMenu4.getParentId())){ // myMenu为五级菜单
								myMenu.setParentIds("0," + parentMenu4.getId() + "," + parentMenu3.getId() + "," + parentMenu2.getId() + "," + parentMenu1.getId() + ",");
							}else {
								SysMenu parentMenu5 = sysMenuMapper.selectByPrimaryKey(parentMenu4.getParentId());
								if("0".equals(parentMenu5.getParentId())){ // myMenu为六级菜单
									myMenu.setParentIds("0," + parentMenu5.getId() + "," + parentMenu4.getId() + "," + parentMenu3.getId() + "," + parentMenu2.getId() + "," + parentMenu1.getId() + ",");
								}else {
									SysMenu parentMenu6 = sysMenuMapper.selectByPrimaryKey(parentMenu5.getParentId());
									if("0".equals(parentMenu6.getParentId())){ // myMenu为七级菜单
										myMenu.setParentIds("0," + parentMenu6.getId() + "," + parentMenu5.getId() + "," + parentMenu4.getId() + "," + parentMenu3.getId() + "," + parentMenu2.getId() + "," + parentMenu1.getId() + ",");
									}
								}
							}
						}
					}
				}
			}
		}
		
		System.err.println("更新之后数据==================================================");
		for (SysMenu myMenu : menuList) {
			System.err.println(myMenu.getName() + " ==>> " + myMenu.getId() + " ==>> " + myMenu.getParentIds());
		}
		
		System.err.println("开始更新数据===========================================");
		//更新数据
		for (SysMenu myMenu : menuList) {
			sysMenuMapper.updateByPrimaryKeySelective(myMenu);
		}
		
	}

}
