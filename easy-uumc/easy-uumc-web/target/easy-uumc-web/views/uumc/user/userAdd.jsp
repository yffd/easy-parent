<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
function accountInput(self) {
	$('input[name="acntId"]').val($(self).val());
	$('input[name="acntPwd"]').val($(self).val());
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post" >
				<fieldset>
					<legend class="text-center text-primary">用户基本信息</legend>
					<table>
						<tr>
							<th>名称：</th>
							<td><input name="userName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>编号：</th>
							<td><input name="userCode" onblur="accountInput(this);" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
						</tr>
						<tr>
							<th>状态：</th>
							<td><input name="userStatus" class="easyui-textbox" required="required"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</fieldset>
				<div style="min-height:10px;"/>
				<fieldset>
					<legend class="text-center text-primary">所属机构信息</legend>
					<table>
						<tr>
							<th>机构：</th>
							<td><input name="orgCode" class="easyui-textbox" required="required"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</fieldset>
				<div style="min-height:10px;"/>
				<fieldset>
					<legend class="text-center text-primary">账号信息</legend>
					<table>
						<tr>
							<th>账号ID：</th>
							<td><input name="acntId" class="easyui-textbox" required="required"/></td>
							<th>账号密码：</th>
							<td><input name="acntPwd" type="password" class="easyui-textbox" required="required"/></td>
						</tr>
						<tr>
							<th>账号类型：</th>
							<td><input name="acntType" class="easyui-textbox" required="required"/></td>
							<th>账号状态：</th>
							<td><input name="acntStatus" class="easyui-textbox" required="required"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
