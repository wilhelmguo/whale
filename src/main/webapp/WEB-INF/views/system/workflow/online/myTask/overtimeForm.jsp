<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDivOvertime" class="hide">
	<form id="auFormOvertime" method="POST" onsubmit="return false;">
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
					<td class="CaptionTD">加班时长：</td>
					<td class="DataTD">&nbsp;
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="duration" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>

				<tr class="FormData">
					<td class="CaptionTD">开始时间：</td>
					<td class="DataTD">&nbsp; 
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="starttime" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
					<td class="CaptionTD">结束时间：</td>
					<td class="DataTD">&nbsp; 
						<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="endtime" class="FormElement ui-widget-content ui-corner-all">
					</td>
				</tr>
				<tr class="FormData">
						<td class="CaptionTD">加班原因：</td>
						<td class="DataTD">&nbsp;
						<textarea rows="2" disabled="disabled" readonly cols="10" maxlength="100" name="reason" multiline="true" class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
						</td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div id="rejectDivOvertime" class="hide">
<form id="rejectFormOvertime" method="POST" onsubmit="return false;">
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
