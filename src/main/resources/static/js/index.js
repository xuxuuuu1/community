$(function(){
	$("#publishBtn").click(publish);
});
//post有三个参数
//访问路径、向服务器提交的数据一个json对象
//声明一个回调函数，浏览器得到服务器响应后会调用function
function publish() {
	$("#publishModal").modal("hide");
	//通过id选择器获取文本框里面的值
	//整个意思也就是获取dom节点的id是recipient-name的value值
	var title = $("#recipient-name").val();
	var content = $("#message-text").val();
	$.post(
		CONTEXT_PATH + "/discuss/add",
		{"title":title,"content":content},
		function (data){
			data = $.parseJSON(data);
			//在提示框中显示返回的信息
			$("hintModal").text(data.msg);
			//显示提示框
			$("#hintModal").modal("show");
			//2s后自动隐藏提示框
			setTimeout(function(){
				$("#hintModal").modal("hide");
				//刷新页面
				if (data.code == 0) {
					window.location.reload();
				}
			}, 2000);
		}
	);

}