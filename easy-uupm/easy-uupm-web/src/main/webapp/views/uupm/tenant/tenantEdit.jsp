<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" class="edit-form-div">
		<form id="form_id" method="post" style="width:100%;height:100%;">
			<input name="id" value="" type="hidden"/>
			<fieldset>
				<table class="edit-form-table">
					<tr>
						<th>租户名称：</th>
						<td><input name="ttName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
						<th>租户简拼：</th>
						<td><input name="shortPinyin" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入"/></td>
					</tr>
					<tr>
						<th>租户全拼：</th>
						<td colspan="3"><input name="pinyin" style="width:575px;" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入"/></td>
					</tr>
					<tr>
						<th>租户类型：</th>
						<td><input name="ttType" type="text" /></td>
						<th>租户状态：</th>
						<td><input name="ttStatus" type="text"/></td>
					</tr>
					<tr>
						<th>服务方式：</th>
						<td><input name="serveType" type="text" /></td>
						<th>服务状态：</th>
						<td><input name="serveStatus" type="text" /></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
