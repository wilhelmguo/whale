<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDivPatch" class="hide">
	<form id="auFormPatch" method="POST" onsubmit="return false;">
		<table cellspacing="0" cellpadding="0" border="0" class="customTable">
			<tbody>
				<tr style="display: none">
					<td colspan="2" class="ui-state-error">
						<input type="hidden" name="id">
					</td>
				</tr>

				<tr class="FormData">
					<td class="CaptionTD">申请人：</td>
					<td class="DataTD">&nbsp; 
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="account_id" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">日期：</td>
					<td class="DataTD">&nbsp;
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="date" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>

				<tr class="FormData">
					<td class="CaptionTD">上班时间：</td>
					<td class="DataTD">&nbsp; 
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="morning" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">上午签退时间：</td>
					<td class="DataTD">&nbsp; 
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="beforenoon" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">下午签到时间：</td>
					<td class="DataTD">&nbsp;
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="afternoon" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">下班时间：</td>
					<td class="DataTD">&nbsp;
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="night" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div id="rejectDivPatch" class="hide">
<form id="rejectFormPatch" method="POST" onsubmit="return false;">
	<table cellspacing="0" cellpadding="0" border="0" class="customTable">
		<tbody>
			<tr class="FormData">
				<td class="CaptionTD"><font color="red">*</font>驳回理由：</td>
				<td class="DataTD">&nbsp;
					<textarea rows="2"  cols="10" maxlength="100" name="rejectReason" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
				</td>
			</tr>
		</tbody>
	</table>
</form>
</div>
