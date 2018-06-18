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
							<td><input name="appName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>编号：</th>
							<td><input name="appCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
						</tr>
						<tr>
							<th>域名：</th>
							<td><input name="appDomain" class="easyui-textbox" required="required"/></td>
							<th>端口：</th>
							<td><input name="appPort" class="easyui-textbox" required="required"/></td>
						</tr>
						<tr>
							<th>上下文：</th>
							<td colspan="3"><input name="appContextPath" class="easyui-textbox" style="width:585px;" /></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
