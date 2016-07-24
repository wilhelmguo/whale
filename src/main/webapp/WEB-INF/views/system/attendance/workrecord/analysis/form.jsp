<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="4" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>员工：</td>
                <td class="DataTD">&nbsp;
                    <input readonly type="text" role="textbox" maxlength="64" name="employeeName"
                           class="FormElement ui-widget-content ui-corner-all"></td>
                </td>
                <td class="CaptionTD"><span style="color: red; ">*</span>考勤日期：</td>
                <td class="DataTD">&nbsp;
                    <input readonly type="text" jyValidate="required" role="textbox" maxlength="32" name="date"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD">上班时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="morning"
                           class="FormElement ui-widget-content ui-corner-all"></td>
                <td class="CaptionTD">下班时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="night"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD">上午签退时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="beforenoon"
                           class="FormElement ui-widget-content ui-corner-all"></td>
                <td class="CaptionTD">下午签到时间：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="afternoon"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD" colspan="1">地理位置：</td>
                <td class="DataTD" colspan="3">&nbsp;
                    <input readonly type="text" role="textbox" maxlength="64" name="location"
                           class="FormElement ui-widget-content ui-corner-all"></td>

            </tr>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD" colspan="1">考勤图片：</td>
                <td id="picture" class="DataTD" colspan="3">&nbsp;
                    <%--<img width="100px" src="${jypath}/static/images/system/indexadv.png"></td>--%>
            </tr>


            </tbody>
        </table>
    </form>
</div>