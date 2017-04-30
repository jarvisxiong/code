package com.ffzx.permission.service.impl;

import com.ffzx.commerce.framework.dao.CrudMapper;
import com.ffzx.commerce.framework.service.impl.BaseCrudServiceImpl;
import com.ffzx.permission.mapper.SysRoleMenuMapper;
import com.ffzx.permission.service.SysRoleMenuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Generator
 * @date 2015-12-31 18:52:35
 * @version 1.0.0
 * @copyright facegarden.com
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl extends BaseCrudServiceImpl implements SysRoleMenuService {

	@Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public CrudMapper init() {
        return sysRoleMenuMapper;
    }
}