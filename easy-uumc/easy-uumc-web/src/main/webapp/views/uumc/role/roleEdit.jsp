<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post">
				<input type="hidden" name="id"/>
				<fieldset>
					<table>
						<tr>
							<th>名称：</th>
							<td><input name="roleName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>编号：</th>
							<td><input name="roleCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
						</tr>
						<tr>
							<th>状态：</th>
							<td><input name="roleStatus" type="text" /></td>
							<th></th>
							<td></td>
						</tr>
						<tr>
							<th>备注：</th>
							<td colspan="3"><textarea name="remark" class="easyui-textbox" style="width:500px;height:80px;"></textarea></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>