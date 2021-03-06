<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="applyForm" hidden="hidden" class="page-content">
    <div class="row">
        <div class="col-xs-12">
            <form id='leaveFrom' class="form-horizontal" method="POST" onsubmit="return false;">
                <div class="message-container">
                    <div class="message-navbar align-center clearfix" style="width: 90%;margin-left: 1%">
                        <div class="message-bar">

                        </div>
                        <div class="message-item-bar">
                            <div class="messagebar-item-left">
                                <a onclick="insertClose();return false;"
                                   class="btn-back-message-list no-hover-underline"
                                   href="#">
                                    <i class="icon-arrow-left blue bigger-110 middle"></i>
                                    <b class="middle bigger-110">关闭</b>
                                </a>
                            </div>


                        </div>
                    </div>
                    <div class="space-4"></div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>审批人</label>
                        <div class="col-sm-3">
                            <input type="hidden" name="pId" value="0">
                            <input id="preOrgName" type="text" style="float: left" name="approverName"
                                   jyValidate="required"
                                   readonly
                                   onclick="showPreOrg(); return false;">
                            <input id="preOrg" hidden type="text" name="approver" jyValidate="required" readonly
                                   class="col-xs-10 col-sm-10">
                            <a href="#" title="清空" onclick="emptyPreOrg(); return false;" class="col-xs-1 col-sm-1"
                               data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
                            <div id='preOrgContent'
                                 class="datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu"
                                 style="display: none; position: absolute;z-index: 10000">
                                <ul id="preOrgTree" class="ztree preOrgTree"></ul>
                            </div>
                        </div>

                    </div>
                    <div class="form-group">

                        <label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>日期</label>
                        <div class="col-sm-2">
                            <input name="date" id="datepicker" readonly value="" jyValidate="required"
                                   class="date-picker"
                                   type="text"
                                   placeholder="补卡日期">
                        </div>
                        <label class="col-sm-2 control-label no-padding-right">上班时间</label>
                        <div class="col-sm-2">
                            <input placeholder="格式hh:mm例如:18:00" name="morning" onchange=""
                                   type="text">
                        </div>
                    </div>
                    <div class="form-group">

                        <label class="col-sm-2 control-label no-padding-right">上午签退时间</label>
                        <div class="col-sm-2">
                            <input placeholder="格式hh:mm例如:18:00" name="beforenoon" onchange=""
                                   type="text">
                        </div>
                        <label class="col-sm-2 control-label no-padding-right">下午签到时间</label>
                        <div class="col-sm-2">
                            <input placeholder="格式hh:mm例如:18:00" name="afternoon" onchange=""
                                   type="text">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="space-4"></div>
                        <label class="col-sm-2 control-label no-padding-right">下班时间</label>
                        <div class="col-sm-2">
                            <input placeholder="格式hh:mm例如:18:00" name="night" onchange=""
                                   type="text">
                        </div>
                    </div>
                    <%--<div class="form-group">--%>
                    <%--<label class="col-sm-2 control-label no-padding-right">加班原因</label>--%>
                    <%--<div class="col-sm-7">--%>
                    <%--<textarea placeholder="请输入加班原因" rows="3" cols="14" maxlength="128" style="width: 100%"--%>
                    <%--name="reason"--%>
                    <%--multiline="true" class="FormElement ui-widget-content ui-corner-all "></textarea>--%>
                    <%--</div>--%>

                    <%--</div>--%>
                    <div class="space-4"></div>
                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <button type="button" class="btn btn-info" onclick="submitApply()">
                                <i class="icon-ok bigger-110"></i> 提交
                            </button>
                        </div>
                    </div>

                </div>
            </form>
        </div>

    </div>

</div>
<%@include file="../../common/dialog.jsp" %>
<%--<script src="${jypath}/static/js/system/workflow/online/patch.js"></script>--%>
