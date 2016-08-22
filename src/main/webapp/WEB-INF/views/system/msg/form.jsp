<%@ page contentType="text/html;charset=UTF-8" %>
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


