<%@ page contentType="text/html;charset=UTF-8" %>
<div id="uploadDiv" class="hide">
    <form id="uploadForm" method="post" enctype="multipart/form-data" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr class="FormData">
                <td class="CaptionTD"><!-- <font color="red">*</font>参数名： --></td>
                <td class="DataTD">&nbsp;
                    <input type="file" id="uploadModelFile" name="modelFile"
                           onchange="JY.File.fileType(this,'zip|bar|bpmn|bpmn20.xml|xml')"/>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error">
                    <input type="hidden" name="id">
                    <input type="hidden" name="usertype">
                </td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>审批人员：</td>
                <td class="DataTD">&nbsp;
                    <input id="preOrg" name="username" type="text" jyValidate="required" readonly value=""
                           class="FormElement ui-widget-content ui-corner-all" onclick="showPreOrg(); return false;"/>
                    <input type="hidden" name="users" value="0">
                    <a href="#" title="清空" onclick="emptyPreOrg(); return false;" class="lrspace3 aBtnNoTD"
                       data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>
                    <div id='preOrgContent' class="menuContent ztreeMC" style="display: none; position: absolute;">
                        <ul id="preOrgTree" class="ztree preOrgTree"></ul>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
