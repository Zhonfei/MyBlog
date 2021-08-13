console.log("load authority");
function loadAuth(){
	$.ajax({
				type:"get",
				url:"/blog/admin/getMenuAuth?roleCode="+$("#roleSel").val(),
				async:false,
				dataType:"json",
				success:function(data){
					if(data){
						$("#menuauth").html('');
						$(data).each(function(){
							var checkFlag = (this.flag=="1"?'checked="checked"':' ');
							var switchId = this.flag+''+this.em_role_id+this.menu_code;
							$("#menuauth").append('<tr>'+
							      '<td>'+this.em_role_name+'</td>'+
							      '<td>'+this.menu_code+'</td>'+
							      '<td><img src="'+this.menu_ico+'" width="17px" height="17px"/></td>'+
							      '<td>'+this.menu_name+'</td>'+
							      '<td><div class="switch tiny round"><input class="switchflag" id="'+switchId+'" type="checkbox" '+checkFlag+'><label for="'+switchId+'"></label></div> </td>'
							      +'</tr>');
						});
					}
				}
			});
			$(".switchflag").click(function(){
				$.ajax({
					type:"post",
					url:"/blog/admin/updateMenuAuth",
					data:{
						menuAuthInfo:this.id,
						emkey:getEmKey()
					},
					async:true,
					success:function(){}
				});
			});
}
