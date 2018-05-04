<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" class="edit-form-div">
		<form id="form_id" method="post" style="width:100%;height:100%;">
			<input name="treeId" type="hidden"/>
			<fieldset>
				<table class="edit-form-table">
					<tr>
						<th>名称：</th>
						<td><input name="rsName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
						<th>编号：</th>
						<td><input name="rsCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
					</tr>
					<tr>
						<th>父编号：</th>
						<td><input name="parentCode" class="easyui-textbox" /></td>
						<th>类型：</th>
						<td><input name="rsType" class="easyui-textbox" /></td>
					</tr>
					<tr>
						<th>状态：</th>
						<td><input name="rsStatus" class="easyui-textbox" /></td>
						<th>序号：</th>
						<td><input name="seqNo" type="text" class="easyui-numberspinner" value="1" data-options="min:0,precision:0"/></td>
					</tr>
				 	<tr>
						<th>短链接：</th>
						<td colspan="3"><input name="shortUrl" class="easyui-textbox" style="width:600px;"/></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
