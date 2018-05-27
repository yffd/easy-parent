<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" class="edit-form-div">
		<form id="form_id" method="post" style="width:100%;height:100%;">
			<input name="id" value="" type="hidden"/>
			<fieldset>
				<table class="edit-form-table">
					<tr>
						<th>租户名称：</th>
						<td><input name="tenantName" type="text" readonly="readonly"/></td>
						<th>租户编号：</th>
						<td><input name="tenantCode" type="text" readonly="readonly"/></td>
					</tr>
					<tr>
						<th>账号ID：</th>
						<td><input name="accountId" type="text" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入账号"/></td>
						<th>账号密码：</th>
						<td><input name="accountPwd" type="password" /></td>
					</tr>
					<tr>
						<th>账号状态：</th>
						<td><input id="accountStatus_id" name="accountStatus" type="text"/></td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
