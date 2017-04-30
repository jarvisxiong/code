package com.ffzx.permission.model;

import java.io.Serializable;

/**
 * 
 * @author Generator
 * @date 2016-01-29 11:10:12
 * @version 1.0.0
 * @copyright facegarden.com
 */
public class SysMenuCoderules implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Id.
     */
    private String coderulesId;

    /**
     * 菜单id.
     */
    private String menuId;

    /**
     * 
     * {@linkplain #coderulesId}
     *
     * @return the value of sys_menu_coderules.coderules_id
     */
    public String getCoderulesId() {
        return coderulesId;
    }

    /**
     * {@linkplain #coderulesId}
     * @param coderulesId the value for sys_menu_coderules.coderules_id
     */
    public void setCoderulesId(String coderulesId) {
        this.coderulesId = coderulesId == null ? null : coderulesId.trim();
    }

    /**
     * 
     * {@linkplain #menuId}
     *
     * @return the value of sys_menu_coderules.menu_id
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * {@linkplain #menuId}
     * @param menuId the value for sys_menu_coderules.menu_id
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }
}