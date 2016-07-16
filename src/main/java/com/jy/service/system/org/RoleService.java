package com.jy.service.system.org;

import java.util.List;

import com.jy.common.mybatis.Page;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.entity.system.org.Role;
import com.jy.service.base.BaseService;

public interface RoleService extends BaseService<Role>{

	 /**
     * 权限列表包含按钮
     * @param roleId 角色Id
     * @param layer  显示层级
     * @return
     */
	public List<ZNodes> listAuthorized(String roleId,String layer);
	 /**
     * 根据角色Id保存权限列表
     * @param roleId 角色Id
     * @param auss 权限数组
     * @return
     */
	public void saveAuthorized(String roleId,String auss,String layer);
	 /**
     * 获取所有角色
     * @return
     */
	public Page<Role> findAllRoleByPage(Role o,Page<Role> page);
}
