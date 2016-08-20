<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <%@include file="../../../common/includeBaseSet.jsp" %>
    <%@include file="../../../common/includeSystemSet.jsp" %>
    <link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css"/>
    <script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.excheck-3.5.js"></script>
    <script src="${jypath}/static/plugins/ueditor/ueditor.config.js"></script>
    <script src="${jypath}/static/plugins/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${jypath}/static/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
</head>
<body>
<div class="page-content">

    <div class="page-header">
        <h1>
            报销申请表
            <small><i class="icon-double-angle-right"></i>&nbsp;员工-直属领导-财务（核对（比如发票）并审批）

            </small>
        </h1>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <form id='leaveFrom' class="form-horizontal" method="POST" onsubmit="return false;">

                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>审批人</label>
                    <div class="col-sm-3">
                        <input type="hidden" name="pId" value="0">
                        <input id="preOrgName" type="text" style="float: left" name="approverName" jyValidate="required" readonly
                                >
                        <input id="preOrg" hidden type="text" name="approver" jyValidate="required" readonly
                               class="col-xs-10 col-sm-10">
                        <%--<a href="#" title="清空" onclick="emptyPreOrg(); return false;"  class="col-xs-1 col-sm-1"--%>
                        <%--data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>--%>
                        <div id='preOrgContent'
                             class="datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu"
                             style="display: none; position: absolute;z-index: 10000">
                            <ul id="preOrgTree" class="ztree preOrgTree"></ul>
                        </div>
                    </div>

                </div>
                <div class="form-group">

                    <label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>报销金额</label>
                    <div class="col-sm-3">
                        <input placeholder="请输入数字" name="amount" onchange="" value="" jyValidate="required,number" type="text">
                    </div>
                    <label class="col-sm-2 control-label no-padding-right"><font color="red">*</font>报销类别</label>
                    <div class="col-sm-3">
                        <input name="type" placeholder="如:采购经费,活动经费" onchange="" value="" jyValidate="required" type="text">
                    </div>
                </div>

                <div class="space-4"></div>
                <div class="form-group">
                    <label class="col-sm-2 control-label no-padding-right">费用明细</label>
                    <div class="col-sm-7">
                        <script id="editor" style="width:100%;height:130px;" type="text/plain"></script>
                        <input type="hidden" name="attach" id="attach">
                        <%--<textarea placeholder="请输入费用明细描述" rows="3" cols="14" maxlength="128" style="width: 100%" name="detail"--%>
                                  <%--multiline="true" class="FormElement ui-widget-content ui-corner-all "></textarea>--%>
                    </div>

                </div>
                <div class="space-4"></div>
                <div class="clearfix form-actions">
                    <div class="col-md-offset-3 col-md-9">
                        <button type="button" class="btn btn-info" onclick="submitApply()">
                            <i class="icon-ok bigger-110"></i> 提交
                        </button>
                    </div>
                </div>

            </form>
        </div>

    </div>

</div>
<%@include file="../../../common/dialog.jsp" %>
<script src="${jypath}/static/js/system/workflow/online/claim.js"></script>
<script type="text/javascript">
    UE.getEditor('editor');
</script>
</body>
</html>