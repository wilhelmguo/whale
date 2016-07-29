<%@ page contentType="text/html;charset=UTF-8" %>
<!-- 组织 -->
<div id="auOrgDiv" class="hide">
    <form id="auOrgForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>
            <!-- <tr class="FormData" >
                <td class="CaptionTD">状态：</td>
                <td class="DataTD">&nbsp;
                    <label class="inline isValidCheckbox">
                        <input type="checkbox" checked="checked" sh-isValid="" role="checkbox" class="FormElement ace ace-switch ace-switch-5" />
                        <span  class="lbl"></span>
                        cb-isValid和Yes和No选择框配套使用
                        <input type="hidden" hi-isValid=""  name="isValid" value="1" />
                    </label>
                </td>
            </tr>		 -->
            <tr class="FormData">
                <td class="CaptionTD"><font color="red">*</font>公司名称：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" maxlength="25" name="name"
                           class="FormElement ui-widget-content ui-corner-all"></td>
            </tr>
            <%--<tr id="preOrgTR" class="FormData">--%>
            <%--<td  class="CaptionTD">上级组织：</td>--%>
            <%--<td class="DataTD">&nbsp;--%>
            <%--<input id="preOrg" type="text" readonly value="" class="FormElement ui-widget-content ui-corner-all" onclick="showPreOrg(); return false;"/>--%>
            <%--<input type="hidden" name="pId" value="0" >--%>
            <%--<a href="#" title="清空" onclick="emptyPreOrg(); return false;" class="lrspace3 aBtnNoTD" data-toggle="modal"><i class='icon-remove bigger-120 red'></i></a>--%>
            <%--<div id='preOrgContent' class="menuContent ztreeMC" style="display: none; position: absolute;">--%>
            <%--<ul id="preOrgTree" class="ztree preOrgTree"></ul>--%>
            <%--</div>--%>
            <%--</td>--%>
            <%--</tr>		--%>
            <tr class="FormData">
                <td class="CaptionTD">公司部门：</td>
                <td class="DataTD" style="font-family: Open Sans;font-size: 12px">&nbsp;
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="总经办"> 总经办
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="客服部"> 客服部
                        </label>
                    </div>
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="运营部"> 运营部
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="人力资源部"> 人力资源部
                        </label>
                    </div>
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="商务部"> 商务部
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="市场部"> 市场部
                        </label>
                    </div>
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="销售部"> 销售部
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="信息技术部"> 信息技术部
                        </label>
                    </div>
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="财务部"> 财务部
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="采购部"> 采购部
                        </label>
                    </div>
                    <div>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="行政部"> 行政部
                        </label>
                        <label class="checkbox-inline">
                            <input type="checkbox" name="zzbm" value="培训部"> 培训部
                        </label>
                    </div>

                </td>

            </tr>
            <tr class="FormData">
                <td class="CaptionTD">公司描述：</td>
                <td class="DataTD">&nbsp;
                    <textarea rows="2" cols="10" maxlength="100" name="description" multiline="true"
                              class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
    <br/><br/><br/><br/>
</div>
<!-- 角色 -->
<div id="auDiv" class="hide">
    <form id="auForm" method="POST" onsubmit="return false;">
        <table cellspacing="0" cellpadding="0" border="0" class="customTable">
            <tbody>
            <tr style="display:none">
                <td colspan="2" class="ui-state-error"><input type="hidden" name="id"></td>
            </tr>
            <tr class="FormData">
                <p style="color: red">*此处建议只增加管理员角色,其他角色建议使用相应公司管理员账号登陆添加</p>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">状态：</td>
                <td class="DataTD">&nbsp;
                    <label class="inline isValidCheckbox">
                        <input type="checkbox" checked="checked" sh-isValid="" role="checkbox"
                               class="FormElement ace ace-switch ace-switch-5"/>
                        <span class="lbl"></span>
                        <!-- cb-isValid和Yes和No选择框配套使用-->
                        <input type="hidden" hi-isValid="" name="isValid" value="1"/>
                    </label>
                </td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD"><font color="red">*</font>角色名称：</td>
                <td class="DataTD">&nbsp;
                    <input type="text" jyValidate="required" maxlength="25" name="name"
                           class="FormElement ui-widget-content ui-corner-all"/></td>
            </tr>
            <tr class="FormData">
                <td class="CaptionTD">角色描述：</td>
                <td class="DataTD">&nbsp;
                    <textarea rows="2" cols="10" maxlength="100" name="description" multiline="true"
                              class="FormElement ui-widget-content ui-corner-all isSelect147"></textarea>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<!-- 授权界面 -->
<div id="authorityDiv" class="hide">
    <div class="row-fluid">
        <button title="选择显示层级" value='1' class="btn btn-xs btn-success"><i class="icon-desktop"></i></button>
        <%--<button title="选择显示层级" value='2' class="btn btn-xs"><i class="icon-th"></i></button>		--%>
        <%--<button title="选择显示层级" value='3' class="btn btn-xs"><i class="icon-user"></i></button>		--%>
        <%--<button title="选择显示层级" value='4' class="btn btn-xs"><i class="icon-bitbucket"></i></button>	--%>
        <input type="hidden" name="layer" value="1">
        <input type="hidden" name="auth" value="org">
        <input type="hidden" name="roleId" value="">
        <div class="col-xs-12">
            <ul id="authorityTree" class="ztree authorityTree"></ul>
        </div>
    </div>
</div>

<div><h4>添加公司流程如下:</h4>
    <p style="color: red">
        1.点击添加公司,输入信息,添加公司.选中公司,对公司进行赋权<br>
        2.选择相应公司,点击添加角色按钮,添加角色,并对角色进行赋权<br>
        3.点击添加管理员账户,添加管理员账户,默认用户名:admin密码:1<br>
    </p></div>