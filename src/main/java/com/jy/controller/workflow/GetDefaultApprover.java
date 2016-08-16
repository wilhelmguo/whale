package com.jy.controller.workflow;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.account.Account;
import com.jy.entity.system.org.Role;
import com.jy.service.system.account.AccountService;
import com.jy.service.system.org.RoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author gsw on 16-8-16
 * @version 1.0
 */
@Controller
@RequestMapping("/backstage/workflow/approver/")
public class GetDefaultApprover extends BaseController<Object> {
    @Autowired
    public RoleService roleService;
    @Autowired
    public AccountService accountService;

    @RequestMapping(value = "find", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes find() {
        AjaxRes ar = getAjaxRes();
        try {
            Account ac = AccountShiroUtil.getCurrentUser();
            String role = ac.getRoleId();
            Role r = new Role();
            r.setId(role);
            List<Role> roles = roleService.find(r);
            if (CollectionUtils.isNotEmpty(roles)) {
                String pid = roles.get(0).getPid();
                Account account = new Account();
                account.setRoleId(pid);
                List<Account> acs = accountService.find(account);
                if (CollectionUtils.isNotEmpty(acs)) {
                    ar.setSucceed(acs.get(0));
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg(Const.DATA_FAIL);
        }
        return ar;
    }
}
