/**
 * 
 */

$(function(){
     console.log("login.js");
     
     /*console.log(window.location.href.split("?"));
     
     var paramList = new Array();
     var paramPair = new Array();
     var custName; 
     var LOB; 
     var surveyType;
     
     var param =window.location.href.split("?");
     if(param.length > 0 ){
	     paramList = param[1].split("&");
	     for(var i=0; i<paramList.length; i++){
	    	 paramPair = paramList[i].split("=");
	    	 if(paramPair[0]=="custName") {
	    		 custName = paramPair[1];
	    		 console.log("custName: " + custName);
	    	 }
	    	 else if(paramPair[0]=="LOB"){
	    		 LOB = 	paramPair[1];
	    		 console.log("LOB: " + LOB);
	    	 }
	    	 else if(paramPair[0]=="surveyType"){
	    		 surveyType=paramPair[1];
	    		 console.log("surveyType: " + surveyType);
	    	 }
	    	 
	     }
	     $('#username').val(custName);
	     $('#lob').val(LOB);
	     $('#surveyType').val(surveyType);
		 
     }   */
     /*$('#login_submit').click(function(){
    	 $.ajax({
				url: "login",
				type: 'POST',
				timeout: 100000,
				dataType:'json',
				data:   $('#frm_login').serialize(),
							
				success: function(data,response,status){
					if(data.success==true){
						$.messager.alert("系统提示","保存成功！");
						 
					}
					else{
						if(data.msg  != ""){
							$.messager.alert("系统提示: ",data.msg);
						}else{
							$.messager.alert("系统提示: ",  " 保存失败，请联系 mouhaixiang@chinasoftinc.com");
						}
					}
						
				},
				error: function(data){
					alert(data);
					$.messager.alert("出错，请检查数据！");
				}
				
			});
			
			
    	 
    	 
    	 
     });
     */
     $('#frm_login').submit(function(){
    	 return true;
     })
     
})

