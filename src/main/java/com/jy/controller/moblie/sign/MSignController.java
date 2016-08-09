package com.jy.controller.moblie.sign;

import com.jy.common.ajax.AjaxRes;
import com.jy.common.utils.DateUtils;
import com.jy.common.utils.base.Const;
import com.jy.common.utils.security.AccountShiroUtil;
import com.jy.controller.base.BaseController;
import com.jy.entity.attendance.WorkRecord;
import com.jy.entity.attendance.WorkTime;
import com.jy.entity.system.account.Account;
import com.jy.service.attendance.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/moblie/api/v1/signs")
public class MSignController extends BaseController<WorkRecord> {

    @Autowired
    public WorkRecordService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes sign(String location, String bz, String pic, String type,String signtype) {
        AjaxRes ar = getAjaxRes();
        try {
            Date now = new Date();
            Account curentuser = AccountShiroUtil.getCurrentUser();
            WorkRecord o = new WorkRecord();
            o.setCompany(getCompany());
            o.setDate(DateUtils.getDateStart(now));
            o.setEmployee(curentuser.getAccountId());
            List<WorkRecord> list = service.find(o);
            if (list == null || list.size() == 0) {
                o.setId(get32UUID());
                o.setEmployeeName(curentuser.getName());
                o.setType(signtype);
                o.setWeek(DateUtils.getWeekOfDate(now));
                if (org.apache.commons.lang3.StringUtils.isNotBlank(location)) {
                    o.setLocation(location);
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(bz)) {
                    o.setDesc(bz);
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(pic)) {
                    o.setPicture(pic);
                }

                if ("1".equals(type)) {
                    o.setMorning(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("2".equals(type)) {
                    o.setBeforenoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("3".equals(type)) {
                    o.setAfternoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("4".equals(type)) {
                    o.setNight(DateUtils.formatDate(now, "HH:mm"));
                }
                service.insert(o);
            } else {
                WorkRecord wr = list.get(0);
                if ("1".equals(type)) {
                    wr.setMorning(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("2".equals(type)) {
                    wr.setBeforenoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("3".equals(type)) {
                    wr.setAfternoon(DateUtils.formatDate(now, "HH:mm"));
                }
                if ("4".equals(type)) {
                    wr.setNight(DateUtils.formatDate(now, "HH:mm"));
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(location)) {
                    wr.setLocation(location);
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(bz)) {
                    wr.setDesc(bz);
                }
                if (org.apache.commons.lang3.StringUtils.isNotBlank(pic)) {
                    wr.setPicture(pic);
                }
                service.update(wr);
            }
            ar.setSucceedMsg(Const.SAVE_SUCCEED);
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg(Const.SAVE_FAIL);
        }
        return ar;
    }


    @RequestMapping(value = "current", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes getOneRecord() {
        AjaxRes ar = getAjaxRes();
        try {
            Date now = new Date();
            Account curentuser = AccountShiroUtil.getCurrentUser();
            WorkRecord o = new WorkRecord();
            o.setCompany(getCompany());
            o.setDate(DateUtils.getDateStart(now));
            o.setEmployee(curentuser.getAccountId());
            List<WorkRecord> list = service.find(o);
            if (list != null && list.size() != 0) {
                ar.setSucceed(list.get(0));
            } else {
                ar.setFailMsg("获取失败");
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg("获取失败");
        }
        return ar;
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public AjaxRes list() {
        AjaxRes ar = getAjaxRes();
        try {
            Account curentuser = AccountShiroUtil.getCurrentUser();
            WorkRecord o = new WorkRecord();
            o.setCompany(getCompany());
            o.setStarttime(DateUtils.getMonthFirstDay());
            o.setEndtime(DateUtils.getMonthLastDay());
            o.setEmployee(curentuser.getAccountId());
            List<WorkRecord> list = service.findByDate(o);
            if (list != null && list.size() != 0) {
                ar.setSucceed(list);
            } else {
                ar.setFailMsg("获取失败");
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            ar.setFailMsg("获取失败");
        }
        return ar;
    }
}
