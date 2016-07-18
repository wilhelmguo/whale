<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>
            <tr class="FormData">
                <p style="color: red;">说明:上午签退时间和下午签到时间必须同时设置或不设置.
                    设置则表示中午需要打卡,不设置表示中午不需要打卡,输入格式'hh:mm',例如:18:00</td>
                </p>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>上班时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" role="textbox" maxlength="64" name="morning"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">上午签退时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text"  role="textbox" maxlength="32" name="beforenoon"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">下午签到时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text"  role="textbox" maxlength="64" name="afternoon"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><font color="red">*</font>下班时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" role="textbox" maxlength="64" name="night"
                           class="FormElement ui-widget-content ui-corner-all">
                </td>
            </tr>

            </tbody>
        </table>
    </form>
</div>