package com.ffzx.permission.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    private String id;

    /**
     * 角色编号.
     */
    private String roleId;

    /**
     * 菜单编号.
     */
    private String menuId;
    
    public SysRoleMenu(){}
    public SysRoleMenu(String roleId, String menuId){
    	this.roleId = roleId;
    	this.menuId = menuId;
    }
    public SysRoleMenu(String id, String roleId, String menuId){
    	this.id = id;
    	this.roleId = roleId;
    	this.menuId = menuId;
    }
    

    /**
     * 
     * {@linkplain #roleId}
     *
     * @return the value of t_role_menu.role_id
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * {@linkplain #roleId}
     * @param roleId the value for t_role_menu.role_id
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    /**
     * 
     * {@linkplain #menuId}
     *
     * @return the value of t_role_menu.menu_id
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * {@linkplain #menuId}
     * @param menuId the value for t_role_menu.menu_id
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}