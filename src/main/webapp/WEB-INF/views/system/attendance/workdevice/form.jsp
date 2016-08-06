<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>

            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>设备类型：</td>
                <td class="DataTD">&nbsp;
                    <select id="type" name="type" >
                        <option value="0" selected>ip地址</option>
                        <%--<option value="1">mac地址</option>--%>
                    </select>
                </td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><span style="color: red; ">*</span>设备名称：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" role="textbox" maxlength="32" name="name"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">所属员工：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" role="textbox" maxlength="64" name="belongto"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>


            </tbody>
        </table>
    </form>
</div>