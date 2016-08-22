package com.jy.controller.msg;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.mybatis.Page;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.oa.task.MsgInfo;
import com.jy.service.oa.task.MsgInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 打卡页面
 */
@Controller
@RequestMapping(value = "/backstage/msg/")
public class MsgController extends BaseController<MsgInfo> {

    private static final String SECURITY_URL = "/backstage/msg/index";

    @Autowired
    public MsgInfoService service;


    /**
     * 打卡
     */
    @RequestMapping(value = "index")
    public String index(org.springframework.ui.Model model) {
        if (doSecurityIntercept(Const.RESOURCES_TYPE_MENU)) {
            model.addAttribute("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_FUNCTION));
            return "/system/msg/list";
        }
        return Const.NO_AUTHORIZED_URL;
    }

    @RequestMapping(value = "findByPage", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRes findByPage(Page<MsgInfo> page, MsgInfo o) {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, "/backstage/msg/index"))) {
            try {
                o.setCompany(getCompany());
                o.setReceiverid(AccountShiroUtil.getCurrentUser().getAccountId());
                Page<MsgInfo> result = service.findByPage(o, page);
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("permitBtn", getPermitBtn(Const.RESOURCES_TYPE_BUTTON));
                p.put("list", result);
                ar.setSucceed(p);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }

}
