<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="/static/webuploader/webuploader.css">
<script type="text/javascript" src="/static/webuploader/webuploader.min.js"></script>
<style>
 	table{width:100%;border:0;margin:0px auto 0;border-collapse:collapse;border-spacing:0;text-align:center;}
 	table th{background:#73B1E0;color:#FFF;}
 	table tr:nth-child(odd){background:#F4F4F4;}
 	table td:nth-child(even){color:#C00;}
 	table tr:hover{background:#D9E9F8;color:#000;}
 	table td,table th{border:1px solid #EEE;font-weight:normal;line-height:30px;font-size:14px;}
	.state{margin:0px;}
</style>

<script type="text/javascript">
	var uploader = WebUploader.create({
	    // swf文件路径
	    swf: '/static/webuploader/Uploader.swf',
	    // 文件接收服务端。
	    server: '',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    disableGlobalDnd: true,
	    accept: {
	    	title: '文件类型',
    	  	extensions: 'zip,bar,bpmn,xml,png'
	    }
	});
	//当有文件被添加进队列的时候
	uploader.on('fileQueued', function( file ) {
		var fileId=file.id;
		var trStr='<tr id='+fileId+'>';
		trStr+='<td style="text-align:left;">'+file.name+'</td>';
		trStr+='<td>'+file.size+' kb</td>';
		trStr+='<td><p class="state">等待上传...</p></td>';
		trStr+='<td id="progress_'+fileId+'">0%</td>';
		trStr+='<td><a onClick="removeFile(\''+fileId+'\');" href="javascript:void(0);">删除</a></td>';
		trStr+='</tr>';
		$('#thelist').append( trStr );
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#progress_'+file.id );
	    $li.text(Math.floor(percentage * 100) + '%' );
	    $('#'+file.id).find('p.state').text('上传中');
	});
	// 成功处理
	uploader.on( 'uploadSuccess', function( file ) {
	    $( '#'+file.id ).find('p.state').text('已上传');
	});
	// 失败处理
	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});
	// 完成处理
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});
	
	function removeFile(fileId) {
		uploader.removeFile(fileId, true);
		$('#'+fileId).remove();
	}
	function uploadFn() {
		var serverURL = $('#uploadURL').val();
		uploader.option('server', serverURL);
		uploader.upload();
	}
</script>
<input type="hidden" id="uploadURL"/>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="overflow:hidden;height:20px;">
		<div style="height:100%;background:#eee;margin:0px;padding:3px;color:red;">支持文件格式：zip、bar、bpmn、bpmn20.xml</div>
	</div>
	<div data-options="region:'center',border:false" style="position:relative;">
		<div style="height:100%;">
			<table id="fileContainerId">
				<thead>
					<tr>
						<th style="width:40%;">文件名称</th>
						<th style="width:15%;">文件大小</th>
						<th style="width:15%;">文件状态</th>
						<th style="width:15%;">上传进度</th>
						<th style="width:15%;">操作</th>
					</tr>
				</thead>
				<tbody id="thelist"></tbody>
			</table>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="overflow:hidden;height:45px;background:#eee;">
		<div id="picker" style="float:left;margin:4px;">选择文件</div>
		<div style="margin:4px;"><button style="height:35px;width:78px;" onclick="uploadFn();">开始上传</button></div>
	</div>
</div>
