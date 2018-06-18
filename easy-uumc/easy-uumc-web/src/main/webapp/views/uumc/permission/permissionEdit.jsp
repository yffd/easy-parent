<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post" >
				<input type="hidden" name="id"/>
				<fieldset>
					<table>
						<tr>
							<th>权限名称：</th>
							<td><input name="pmsName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>权限编号：</th>
							<td><input name="pmsCode" readonly="true" class="easyui-textbox" required="required" /></td>
						</tr>
						<tr>
							<th>系统应用编号：</th>
							<td><input name="appCode" readonly="true" class="easyui-textbox" required="required"/></td>
							<th>系统资源编号：</th>
							<td><input name="rsCode" readonly="true" class="easyui-textbox" required="required"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
