function loadMainPage(){
	console.log(111111);
	if($("#roleSel").val()){
		$.ajax({
			type:"get",
			url:"usr/getMenuByRole?roleCode="+$("#roleSel").val(),
			async:false,
			dataType:"json",
			success:function(data){
				$("#mainmenu").html("");
				if(data){
					$("#mainmenu").html("<li><label>Heading</label></li>");
					$("#mainmenutabs").html("");
					$(data).each(function(){
						$("#mainmenu").append("<li><a href='#"+this.menuUrl+"'><img src='"+this.menuIco+"' width='15%' height='15%'>"+this.menuName+"</a></li>");
						if("home" === this.menuUrl){
							$("#mainmenutabs").append('<div class="content active" id="'+this.menuUrl+'"></div>');
						}else{
							$("#mainmenutabs").append('<div class="content" id="'+this.menuUrl+'"></div>');
						}
						var mUrl = this.menuUrl;
						console.log(222222);
						console.log(mUrl);
						$.ajax({
							type:"get",
							url:getRequestUrlByMenuUrl(mUrl),
							async:false,
							success:function(data){
								$("#"+mUrl).html(data);
							}
						});
						console.log(666666);
					});
					console.log(777777);
					loadHome();
					loadAuth();
					loadMenu();
				}
			}
		});
		console.log(8888888);
	}
}
$(function () {
	var emkey = getEmKey();
	$("#roleSel").change(function(){
		loadMainPage();
	});
	
	$.ajax({
		type:"get",
		url:"usr/getRolesByEmKey?emkey="+emkey,
		async:false,
		dataType:"json",
		success:function(data){
			if(data){
				$("#roleSel").html("");
				$(data).each(function(){
					$("#roleSel").append("<option value='"+this.role_code+"'>"+this.role_nameZH+"</option>");
				});
				//sessionStorage.setItem("role",$("#roleSel").val());
				loadMainPage();
			}
		}
	});
	//alert($("#roleSel").val());
	
});