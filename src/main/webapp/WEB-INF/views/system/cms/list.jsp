<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html >
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <%@include file="../common/includeBaseSet.jsp" %>
    <%@include file="../common/includeSystemSet.jsp" %>
    <link rel="stylesheet" href="${jypath}/static/plugins/zTree/3.5/zTreeStyle.css"/>
    <script src="${jypath}/static/plugins/zTree/3.5/jquery.ztree.core-3.5.min.js"></script>
    <script src="${jypath}/static/plugins/ueditor/ueditor.config.js"></script>
    <script src="${jypath}/static/plugins/ueditor/ueditor.all.min.js"></script>
    <link rel="stylesheet" href="${jypath}/static/plugins/webuploader/css/webuploader.css"/>
    <script src="${jypath}/static/plugins/webuploader/js/webuploader.js"></script>
    <script type="text/javascript" charset="utf-8" src="${jypath}/static/plugins/ueditor/lang/zh-cn/zh-cn.js"></script>
    <style>
        #clipArea {
            /*margin: 20px;*/
            height: 250px;
            width: 400px;
        }

        #file {
            margin: 20px;
        }

        #clipBtn {
            margin: 20px;
        }

        #view {
            margin-left: 100px;
            width: 210px;
            height: 120px;
            position: absolute;
            left: 380px;
            top:0px;

        }
    </style>
    <!--[if IE]>
    <script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="page-content">
    <div id="row-fluid" class="row-fluid">
        <div class="col-xs-12">
            <form id="baseForm" class="form-inline" method="POST" onsubmit="return false;">
                <div class="row">
                    <div class="widget-main">
                        <input type="text" name="keyWord" placeholder="这里输入标题" class="input-large">
                        &nbsp;&nbsp;<span id="selectisValid"><label></label>：<select data-placeholder="状态"
                                                                                     name="isValid"
                                                                                     class="chosen-select isSelect75"></select></span>
                        &nbsp;&nbsp;
                        <button id='searchBtn' class="btn btn-warning  btn-xs" title="过滤" type="button"
                                onclick="getbaseList(1)"><i class="icon-search bigger-110 icon-only"></i></button>
                    </div>
                </div>
                <input type='hidden' class='pageNum' name='pageNum' value='1'/>
                <input type='hidden' class='pageSize' name='pageSize' value='5'/>
            </form>

            <table id="baseTable" class="table table-striped table-bordered table-hover">
                <thead>
                <tr>
                    <th style="width:3%" class="center">
                        <label><input type="checkbox" class="ace"><span class="lbl"></span></label>
                    </th>
                    <th style="width:5%" class="center hidden-480">序号</th>
                    <th style="width:10%" class="center">标题</th>
                    <th style="width:10%" class="center hidden-480">发布人</th>
                    <th style="width:12%" class="center "><i class="icon-time bigger-110 hidden-480"></i>发布时间</th>
                    <th style="width:12%" class="center hidden-480"><i class="icon-time bigger-110 hidden-480"></i>修改时间
                    </th>
                    <%--<th style="width:5%"  class="center hidden-480">发布IP</th>--%>
                    <th style="width:15%" class="center hidden-480">状态</th>
                    <th style="width:15%" class="center">操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
            <div class="row">
                <div class="col-sm-4">
                    <div class="dataTables_info customBtn">
                        <c:forEach var="pbtn" items="${permitBtn}">
                            <a href="#" title="${pbtn.name}" id="${pbtn.btnId}" class="lrspace3"><i
                                    class='${pbtn.icon} bigger-220'></i></a>
                        </c:forEach>
                    </div>
                </div>
                <div class="col-sm-8">
                    <!--设置分页位置-->
                    <div id="pageing" class="dataTables_paginate paging_bootstrap">
                        <ul class="pagination"></ul>
                    </div>
                </div>
            </div>


        </div>

    </div>
    <div id="auDiv" hidden="hidden">
        <form id="auForm" method="POST" onsubmit="return false;">
            <div class="message-container">
                <div class="message-navbar align-center clearfix" style="width: 90%;margin-left: 1%">
                    <div class="message-bar">

                    </div>
                    <div class="message-item-bar">
                        <div class="messagebar-item-left">
                            <a onclick="insertClose();return false;" class="btn-back-message-list no-hover-underline"
                               href="#">
                                <i class="icon-arrow-left blue bigger-110 middle"></i>
                                <b class="middle bigger-110">关闭</b>
                            </a>
                        </div>


                    </div>
                </div>
                <div class="message-list-container">
                    <form id="mailform" class="form-horizontal message-form col-xs-12" method="POST"
                          onsubmit="return false;">
                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>标题：</label>

                            <div class="col-sm-11 col-xs-12">
                                <input type="hidden" name="id">
                                <input jyValidate="required" type="text" style="width:100%;" name="title" id="title"
                                       value="${pd.title }"
                                       placeholder="这里输入标题" title="标题"/>
                                <%--<input type="title" maxlength="5000" class="col-xs-12" name="toList">--%>
                                <%--<h3 style="align-content: center" id="cmstitle">标题</h3>--%>
                                <%--<p>--%>
                                <%--<span style="color:gray;" id="cmsaddtime">发布时间</span>--%>
                                <%--<span style="color:gray;" id="cmspublisher">发布人</span>--%>
                                <%--</p>--%>
                            </div>
                        </div>
                        <br>
                        <div class="hr hr-18 dotted"></div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"><font
                                    color="red">*</font>公告封面：</label>
                            <div class="col-sm-11 col-xs-12">
                                <div id="clipArea"></div>
                                <div id="view" ></div>
                                <input style="float: left" type="file" id="file">
                                <button id="clipBtn"class="btn btn-primary btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"><span class="ui-button-text"><i
                                        class="icon-ok bigger-110"></i>&nbsp;截取并上传</span></button>
                                <!--用来存放item-->
                                <!-- style只为显示效果，真正用请去掉 -->
                                <input id="cover" hidden jyValidate="required" readonly="readonly"
                                       type="text"
                                       name="cover">
                            </div>
                        </div>
                        <br>

                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"><font color="red">*</font>内容：</label>
                            <div class="col-sm-11 col-xs-12">
                                <script id="editor" style="width:100%;height:300px;" type="text/plain"></script>
                                <input type="hidden" name="content" id="content">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label no-padding-right"><font
                                    color="red">*</font>发布状态：</label>
                            <div class="col-sm-11 col-xs-12">
                                <select name="status">
                                    <option value="0">未发布</option>
                                    <option value="1">已发布</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="form-group">
                            <div class="col-sm-11 col-xs-12">
                                <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
                                    <div class="ui-dialog-buttonset">
                                        <button onclick="add()" type="button"
                                                class="btn btn-primary btn-xs ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only"
                                                role="button" aria-disabled="false"><span class="ui-button-text"><i
                                                class="icon-ok bigger-110"></i>&nbsp;保存</span></button>

                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <br>
                    </form>
                </div>
            </div>



        </form>
    </div>
    <div id="writeDiv" hidden class="tab-pane">
        <div class="message-container">
            <div class="message-navbar align-center clearfix">
                <div class="message-bar">

                </div>
                <div class="message-item-bar">
                    <div class="messagebar-item-left">
                        <a onclick="detailClose();return false;" class="btn-back-message-list no-hover-underline"
                           href="#">
                            <i class="icon-arrow-left blue bigger-110 middle"></i>
                            <b class="middle bigger-110">关闭</b>
                        </a>
                    </div>


                </div>
            </div>
            <div class="message-list-container">
                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <%--<input type="title" maxlength="5000" class="col-xs-12" name="toList">--%>
                        <h3 style="align-content: center" id="cmstitle">标题</h3>
                        <p>
                            <span style="color:gray;" id="cmsaddtime">发布时间</span>
                            <span style="color:gray;" id="cmspublisher">发布人</span>
                        </p>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <p>
                            <span style="color:gray;" id="cmscover">封面</span>
                        </p>
                    </div>
                </div>

                <div class="hr hr-18 dotted"></div>
                <div class="form-group">
                    <div class="col-sm-11 col-xs-12">
                        <p id="cmscontent">内容</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- #addorUpdateFrom -->
    <%@include file="form.jsp" %>
    <!-- #dialog-confirm -->
    <%@include file="../common/dialog.jsp" %>
</div>
<script src="${jypath}/static/plugins/tabs/js/tab-control.min.js"></script>
<script type="text/javascript">
    UE.getEditor('editor');
</script>
<script src="${jypath}/static/plugins/cropper/js/iscroll-zoom.js"></script>
<script src="${jypath}/static/plugins/cropper/js/hammer.js"></script>
<script src="${jypath}/static/plugins/cropper/js/jquery.photoClip.js"></script>
<script src="${jypath}/static/js/system/cms/news.js"></script>
</body>
</html>