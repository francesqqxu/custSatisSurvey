/**
 * 
 */


var login_user;

$(function(){
	
	console.log("surveyExport.js");
	
	var $modal = $('#surveyExport').modal({show: true});
	login_user = window.parent.document.getElementById("login_name").innerText;
	
	$('#lob').selectpicker({width:265});
	initSelectOptions("lob",login_user);
	
	$('#evalBegDate').datetimepicker({
	    format:"yyyy-mm-dd",
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		initialDate: new Date()
    }).on('hide',function(e){
    	$('#fm-surveyExport').data('bootstrapValidator')
    	.updateStatus('evalBegDate','NOT_VALIDATED',null)
    	.validateField('evalBegDate');
    });
 
	$('#evalEndDate').datetimepicker({
	    format:"yyyy-mm-dd",
        language:  'zh-CN',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0,
		initialDate: new Date()
    }).on('hide',function(e){
    	$('#fm-surveyExport').data('bootstrapValidator')
    	.updateStatus('evalEndDate','NOT_VALIDATED',null)
    	.validateField('evalEndDate');
    });
	
	$("#fm-surveyExport").bootstrapValidator({
        message: '输入值不合法',
        excluded: [":disabled"],
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	    evalBegDate: {
                trigger: "change",
                validators: {
                    notEmpty: {
                        message: '请选择开始时间'
                    }
                }
            }, evalEndDate: {
                trigger: "change",
                validators: {
                    notEmpty: {
                        message: '请选择结束时间'
                    },
                    callback: {
                        message: '开始时间必须小于结束时间',
                        callback: function (value, validator, $field) {
                            let other = validator.getFieldElements('evalBegDate').val();//获得另一个的值
 
                            let start = new Date(other.replace("-", "/").replace("-", "/"));
                            let end = new Date(value.replace("-", "/").replace("-", "/"));
 
                            if (start <= end) {
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
            
        }
    });
 

	
 	//$modal.find('.submit').click(function(){
    $('#btnSurveyExport').click(function(){ 
// 		$.ajax({
// 			url: '/custSatisSurvey/export',
// 			type: 'POST',
// 			dataType: 'json',
// 			data: $('#fm-surveyExport').serialize(),
// 			success: function(data,response,status){
// 				if(data.success==true){
// 					$modal.modal('hide');
// 				toastr.success("系统提示","下载成功！");
// 				}else{
// 					if(data.msg  != ""){
// 					toastr.warning("系统提示: ",data.msg);
// 					}else{
// 					toastr.warning("系统提示: ",  " 保存失败，请联系 xx@chinasoftinc.com");
// 					}
// 				}
// 			},	 
// 			error: function(){
// 				 $modal.modal('hide');
// 				 toastr.warning("下载出错！");
// 			}
// 		});	
// 	});
// 		var strEvalBegDate = $('#evalBegDate').val();
// 		console.log(strEvalBegDate);
// 		var strEvalEndDate = $('#evalEndDate').val();
// 		console.log(strEvalEndDate);
// 		var strLob = $('#lob').val();
// 		console.log(strLob);
// 		var form = $("<form>");
// 		form.attr("style","display:none");
// 		form.attr("target","");
// 		form.attr("method","post");
// 		form.attr("action", "/custSatisSurvey/export");
// 		var input1 = $("<input>");
// 		input1.attr("type","hidden");
// 		input1.attr("name","evalBegDate");
// 		input1.attr("value",strEvalBegDate);
// 		var input2 = $("<input>");
// 		input2.attr("type","hidden");
// 		input2.attr("name","evalEndDate");
// 		input2.attr("value",strEvalEndDate);
// 		var input3 = $("<input>");
// 		input3.attr("type","hidden");
// 		input3.attr("name","lob");
// 		input3.attr("value",strLob);
// 		$("body").append(form);
// 		form.append(input1);
// 		form.append(input2);
// 		form.append(input3);
// 		form.submit();
// 		form.remove();

    	var form = document.getElementById('fm-surveyExport');
    	$('#fm-surveyExport').bootstrapValidator('validate');
    	
	    if($('#fm-surveyExport').data('bootstrapValidator').isValid()){
	        form.submit();
	        $modal.on("hidden.bs.modal", function() {
	            $(this).removeData("bs.modal");
	        });
		 }

    	
 	});	
});

function initSelectOptions(selectId,login_user,isAll){
	
	var selectObj = $('#' + selectId);
	$.ajax({
		url : '/config/'+selectId,
		async: false,
		timeout: 100000,
		dataType:'json',
		type: 'POST',
		data:{
  		  loginUser: login_user,
  		  isAll: "true"
  	  	},
		success: function(result){
			if(result.success){
				var configs = result.data;
     			//selectObj.find("option:not(:first)").remove();
				selectObj.find("option").remove();
				for(var i in configs){
					var addressConfig = configs[i];
					var optionValue = addressConfig.optionValue;
					var optionText = addressConfig.optionText;
					selectObj.append(new Option(optionText, optionValue));
				}
				
				selectObj.selectpicker('refresh');
			}else {
//				toastr.error('获取[' + selectId +']信息失败，原因: ' + result.errorMessage);
			}
		},
		error: function(result){
//			toastr.error('获取[' + selectId +']信息失败，原因: ' + result.errorMessage);
		}
	})
}
