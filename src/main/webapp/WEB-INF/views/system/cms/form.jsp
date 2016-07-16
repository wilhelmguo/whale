<%@ page contentType="text/html;charset=UTF-8" %>

<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>
            <tr>
                <td>标题:</td>
                <td><input type="text" style="width:100%;" name="title" id="title" value="${pd.title }"
                           placeholder="这里输入标题" title="标题"/></td>
            </tr>
            <tr>
                <td style="width: 10%">内容:</td>
                <td id="nr">
                    <script id="editor" style="width:100%;height:500px;" type="text/plain"></script>
                    <input type="hidden" name="content" id="content">${pd.content }</input>
                </td>
            </tr>
            <%--<tr>--%>
            <%--<td>发布:</td>--%>
            <%--<td>--%>
            <%--<input type="text" name="publisher" id="publisher" value="${pd.publisher }" placeholder="这里输入发布人"--%>
            <%--title="发布人"/>--%>
            <%--&lt;%&ndash;<input type="number" name="sequence" id="sequence" value="${pd.sequence }" placeholder="权重(输入数字)"&ndash;%&gt;--%>
            <%--&lt;%&ndash;title="权重越大,排列越靠前"/>&ndash;%&gt;--%>
            <%--</td>--%>
            <%--</tr>--%>
            <tr>
                <td>状态:</td>
                <td>
                    <%--<select name="recommand" title="推荐否">--%>
                    <%--<option value="0"--%>
                    <%--<c:if test="${pd.recommand == '0' }">selected</c:if> >未推荐--%>
                    <%--</option>--%>
                    <%--<option value="1"--%>
                    <%--<c:if test="${pd.recommand == '1' }">selected</c:if> >已推荐--%>
                    <%--</option>--%>
                    <%--</select>--%>
                    <select name="status" title="状态">
                        <option value="0"
                                <c:if test="${pd.status == '0' }">selected</c:if> >未发布
                        </option>
                        <option value="1"
                                <c:if test="${pd.status == '1' }">selected</c:if> >已发布
                        </option>
                    </select>
                </td>
            </tr>
            </tbody>
        </table>

        <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
                src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>

    </form>
</div>
