<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post" >
				<input type="hidden" name="id"/>
				<fieldset>
					<table>
						<tr>
							<th>账号ID：</th>
							<td><input name="acntId" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入" /></td>
							<th>账号密码：</th>
							<td><input name="acntPwd" class="easyui-textbox easyui-validatebox" required="required" /></td>
						</tr>
						<tr>
							<th>账号状态：</th>
							<td><input name="acntStatus" class="easyui-textbox" required="required"/></td>
							<th>账号类型：</th>
							<td><input name="acntType" class="easyui-textbox" required="required"/></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
