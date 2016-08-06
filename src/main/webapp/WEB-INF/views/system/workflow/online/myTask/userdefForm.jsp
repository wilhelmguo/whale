<%@ page contentType="text/html;charset=UTF-8" %>
<div id="auDivUserdef" class="hide">
	<form id="auFormUserdef" method="POST" onsubmit="return false;">
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
					<td class="CaptionTD">申请明细：</td>
					<td class="DataTD">&nbsp;
						<p id="attachUserdef">内容</p>
						<%--<input type="text" disabled="disabled" readonly role="textbox" maxlength="64" name="attach" class="FormElement ui-widget-content ui-corner-all">--%>
					</td>
				</tr>

			</tbody>
		</table>
	</form>
</div>
<div id="rejectDivUserdef" class="hide">
<form id="rejectFormUserdef" method="POST" onsubmit="return false;">
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