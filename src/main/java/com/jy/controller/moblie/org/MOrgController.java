package com.jy.controller.moblie.org;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.echarts.series.Map;
import com.jy.common.utils.tree.entity.ZNodes;
import com.jy.controller.base.BaseController;
import com.jy.entity.system.org.Role;
import com.jy.service.system.org.OrgService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author gsw on 16-8-10
 * @version 1.0
 */
@Controller
@RequestMapping("/moblie/api/v1/orgs")
public class MOrgController extends BaseController<Role> {
    @Autowired
    public OrgService orgService;

    private static final String SECURITY_URL = "/backstage/org/role/index";

    @RequestMapping(value = "/trees", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes getCompanyUserPreOrgTree() {
        AjaxRes ar = getAjaxRes();
        if (ar.setNoAuth(doSecurityIntercept(Const.RESOURCES_TYPE_MENU, SECURITY_URL))) {
            try {
                HashMap<String, List<ZNodes>> map = new HashMap<String, List<ZNodes>>();
                List<ZNodes> r = orgService.getCompanyUserPreOrgTree(getCompany());
//                for (ZNodes z : r) {
//                    if ("o".equals(z.getOther())) {
//                        List<ZNodes> list = map.get(z.getName());
//                        if (CollectionUtils.isEmpty(list)) {
//                            map.put(z.getName(), new ArrayList<ZNodes>());
//                        }
//                    }
//                }
                ar.setSucceed(r);
            } catch (Exception e) {
                logger.error(e.toString(), e);
                ar.setFailMsg(Const.DATA_FAIL);
            }
        }
        return ar;
    }
}
