<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>年度：</td>
                <td class="DataTD">&nbsp;
                    <span id="selectYear"><label></label><select data-placeholder="状态" name="year"></select></span>
                    <%--<input type="text" jyValidate="required" role="textbox" maxlength="64" name="year"--%>
                           <%--class="FormElement ui-widget-content ui-corner-all">--%>
                </td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">日期：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="32" name="workDate"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">种类：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="status"
                           class="FormElement ui-widget-content ui-corner-all">
                    <span id="selectType"><label></label><select data-placeholder="状态" name="status"></select></span>
                </td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><font color="red">*</font>规则名称：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" role="textbox" maxlength="64" name="name"
                           class="FormElement ui-widget-content ui-corner-all">
                </td>
            </tr>

            </tbody>
        </table>
    </form>
</div>