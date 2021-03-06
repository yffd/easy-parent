<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
function setKeyValue(self) {
	var obj = $('input[name="keyValue"]');
	obj.val($(self).val());
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" class="edit-form-div">
		<form id="form_id" method="post" style="width:100%;height:100%;">
			<input name="category" type="hidden"/>
			<fieldset>
				<table class="edit-form-table">
					<tr>
						<th>名称：</th>
						<td><input name="keyName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
						<th>编号：</th>
						<td><input name="keyCode" onblur="setKeyValue(this)" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
					</tr>
					<tr>
						<th>父编号：</th>
						<td><input name="parentCode" class="easyui-textbox" /></td>
						<th>序号：</th>
						<td><input name="seqNo" type="text" class="easyui-numberspinner" value="1" data-options="min:0,precision:0"/></td>
					</tr>
					<tr>
						<th>值：</th>
						<td><input name="keyValue" class="easyui-textbox" /></td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
