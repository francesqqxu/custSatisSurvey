/**
 * 
 */

 var ahref;
 var loginFrm; 
 
$(function(){

	 console.log("loginCust.js");
	 loginFrm = document.frm_loginCust;
	 console.log("loginFrm=", loginFrm);
	 console.log("loginFrm.action=",loginFrm.action);
	 
	 $('a').click(function() {
            console.log("当前URL为:", $(this).attr('href'));
            ahref = $(this).attr('href');
            console.log("ahref=",ahref);
            //document.frm_loginCust.action=ahref
            loginFrm.action = ahref
            console.log("loginFrm.action in a = ", loginFrm.action);
             
     });
	 
	$('#frm_loginCust').submit(function(){
		  
		  console.log("loginFrm.action in submit=",loginFrm.action);
		  loginFrm.submit(); 
		  //document.frm_loginCust.submit();
		  //return true;
		
     });
     
	 
	
})

