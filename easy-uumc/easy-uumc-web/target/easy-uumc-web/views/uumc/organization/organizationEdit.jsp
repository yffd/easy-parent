<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div class="form-div">
			<form id="form_id" method="post">
				<input type="hidden" name="id" />
				<fieldset>
					<table>
						<tr>
							<th>名称：</th>
							<td><input name="orgName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
							<th>编号：</th>
							<td><input name="orgCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
						</tr>
						<tr>
							<th>父名称：</th>
							<td><input name="parentName" readonly="true" class="easyui-textbox" /></td>
							<th>父编号：</th>
							<td><input name="parentCode" readonly="true" class="easyui-textbox" /></td>
						</tr>
						<tr>
							<th>序号：</th>
							<td><input name="seqNo" type="text" class="easyui-numberspinner" value="1" data-options="min:0,precision:0"/></td>
							<th></th>
							<td></td>
						</tr>
					</table>
				</fieldset>
			</form>
		</div>
	</div>
</div>
