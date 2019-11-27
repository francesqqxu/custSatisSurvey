
(function ($) {

    window.Ewin = function () {
        var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog modal-sm">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '<p>[Message]</p>' +
                                      '</div>' +
                                       '<div class="text-center" style="padding:15px;border-top:1px solid #e5e5e5;">' +
        '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
        '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
    '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';


        var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        var generateId = function () {
            var date = new Date();
            return 'mdl' + date.valueOf();
        }
        var init = function (options) {
            options = $.extend({}, {
                title: "操作提示",
                message: "提示内容",
                btnok: "确定",
                btncl: "取消",
                width: 200,
                auto: false
            }, options || {});
            var modalId = generateId();
            var content = html.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                    BtnCancel: options.btncl
                }[key];
            });
            $('body').append(content);
            $('#' + modalId).modal({
                width: options.width,
                backdrop: 'static'
            });
            $('#' + modalId).on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
            return modalId;
        }

        return {
            alert: function (options) {
                if (typeof options == 'string') {
                    options = {
                        message: options
                    };
                }
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                modal.find('.cancel').hide();

                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            confirm: function (options) {
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                modal.find('.cancel').show();
                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                            modal.find('.cancel').click(function () { callback(false); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            dialog: function (options) {
                options = $.extend({}, {
                    title: 'title',
                    url: '',
                    width: 800,
                    height: 550,
                    onReady: function () { },
                    onShown: function (e) { }
                }, options || {});
                var modalId = generateId();

                var content = dialogdHtml.replace(reg, function (node, key) {
                    return {
                        Id: modalId,
                        Title: options.title
                    }[key];
                });
                $('body').append(content);
                var target = $('#' + modalId);
                target.find('.modal-body').load(options.url);
                if (options.onReady())
                    options.onReady.call(target);
                target.modal();
                target.on('shown.bs.modal', function (e) {
                    if (options.onReady(e))
                        options.onReady.call(target, e);
                });
                target.on('hide.bs.modal', function (e) {
                    $('body').find(target).remove();
                });
            }
        }
    }();
})(jQuery);
 

/*var $table = $('#userTbl').bootstrapTable({
	  url: 'user/getUserList',
	  toolbar: '.toolbar', //工具按钮用哪个容器
      //striped: true, //是否显示行间隔色
      cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      pagination: true, //是否显示分页
      sortable: true, //是否启用排序
      sortOrder: "asc", //排序方式
      //queryParams: postQueryParams,//传递参数（*）
      //sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
      pageSize: 10, //每页的记录行数（*）
      pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
      strictSearch: true,
//  height: table_h, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度,设置了行高后编辑时标头宽度不会随着下面的行改变，且颜色也不会改变？？？？
      uniqueId: "id", //每一行的唯一标识，一般为主键列
      cardView: false, //是否显示详细视图
      detailView: false, //是否显示父子表
      paginationHAlign: "left",
      singleSelect: true,
      search: true,               //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
      //strictSearch: true,
      showColumns: true, //是否显示所有的列
      showRefresh: true, //是否显示刷新按钮
      clickToSelect: true, //是否启用点击选中行
      paginationPreText: "<<",
      paginationNextText: ">>"
}),*/
var $modal = $('#userModal').modal({show: false}),
$alert = $('.alert').hide();
$alertModal = $modal.find('.alert');

var url;
var $table;
var login_user;

function showModal(title,row){
	row = row || {
		id: 0,
		username: '',
		password: '',
		lob: '',
		userType: '',
		surveyType: '',
		custname: '',
		evalPersonDep: '',
		evalPersonName: ''
	};
	$modal.data('id',row.id);
	$modal.find('.modal-title').text(title);
	for(var name in row){
		//console.log(name + ", " + row[name]);
		$modal.find('input[name="' +name + '"]').val(row[name]);
		//console.log($modal.find('select[name="lob"]'));
		//$modal.find('select[name="' +name +'"]').val(row[name]);
		//$modal.find('select[name="'+name+ '"]').val($modal.find('select[name="' +name + '"]').find('option:selected').val());
		//$modal.find('select[name="'+name+ '"]').val($modal.find('select[name="' +name + '"]').get(0).value);
	}
	$modal.find('select[name="lob"]').val(row["lob"]).trigger("change");
	$modal.find('select[name="surveytype"]').val(row["surveytype"]).trigger("change");
	$modal.modal('show');
}

function showAlert(title,type){
	$alert.attr('class','alert alert-' +type || 'success')
	          .html('<i class="glyphicon glyphicon-check"></i>' + title).show();
	setTimeout(function(){
		$alert.hide();
	},3000);
}

function showAlertModal(title,type){
	$alertModal.attr('class','alert alert-' +type || 'success')
	          .html('<i class="glyphicon glyphicon-check"></i>' + title).show();
	setTimeout(function(){
		$alertModal.hide();
	},3000);
}
$("#fm_user").bootstrapValidator({
	 
	 excluded: [':disabled', ':not(:visible)'],

	 feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	 },
	 live: 'enabled',
	 message: 'This value is not valid',
	 submitButtons: 'button[type="submit"]',
	 submitHandler: null,
     trigger: null,
     threshold: null,
     fields: {
        //多个重复
    	 custname: {
           validators: {
                 notEmpty: {
               	  message: "客户名称不能为空"
                 }
            }
        },
//        password: {
//            validators: {
//                 notEmpty: {
//               	  message: "密码不能为空"
//                 }
//            }
//        },
        evalPersonDep:{
        	validators: {
                notEmpty: {
              	  message: "客户评价人部门"
                }
           }
        },
        evalPersonName:{
        	validators: {
                notEmpty: {
              	  message: "客户评价人姓名"
                }
           }
        }
    }

});

$(function(){
	
	console.log("user.js");
	console.log($alert);
	//console.log(window.parent.document.getElementById("login_name"));
	//console.log(window.parent.document.getElementById("login_name").innerText);
	login_user = window.parent.document.getElementById("login_name").innerText;
	//$('#usertype').selectpicker({width:120});
	$('#surveytype').selectpicker({width:120});
	$('#lob').selectpicker({width:120});
	
//	initSelectOptions("surveytype","");
//	initSelectOptions("usertype",login_user);
//	initSelectOptions("lob",login_user);
	
//	var usertype = $('#usertype').val();
//	console.log(usertype);
//	if(usertype == "集团QA"  || usertype=="业务线QA") {
//			$('#divSurveyType').hide();
//			$("#divEvalPersonDep").hide();
//			$('#divEvalPersonName').hide();
//	} 
//	
	$table = $('#userTbl').bootstrapTable({
		  url: 'user/getUserListById',
		  toolbar: '.toolbar',
		  queryParams: 'queryParams',
		  contentType: 'application/x-www-form-urlencoded',
		  striped: true, //是否显示行间隔色
	      cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
	      pagination: true, //是否显示分页
	      sortable: true, //是否启用排序
	      sortOrder: "asc", //排序方式
	      sidePagination: "client",      //分页方式：client客户端分页，server服务端分页（*）
	      pageSize: 10, //每页的记录行数（*）
	      pageList: [10, 20, 50, 100], //可供选择的每页的行数（*）
	     //  height: table_h, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度,设置了行高后编辑时标头宽度不会随着下面的行改变，且颜色也不会改变？？？？
	      height: $(window).height()-120,
	      //height: 460,
	      uniqueId: "id", //每一行的唯一标识，一般为主键列
	      cardView: false, //是否显示详细视图
	      detailView: false, //是否显示父子表
	      paginationHAlign: "right",
	      singleSelect: false,
	      search: true,               //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
	      strictSearch: true,
	      showColumns: true, //是否显示所有的列
	      showRefresh: true, //是否显示刷新按钮
	      clickToSelect: true, //是否启用点击选中行
	      paginationPreText: "<",
	      paginationNextText: ">",
	      exportDataType: 'all',
	      showExport: true,
	      buttonAlign: "right",
	      exportTypes:['excel'],
	      columns: [{
	    	   
	    	  checkbox: true,
	    	  visible:true//是否显示复选框  
	    	},{
	    		field: 'id',
	    		sortable: true,
	    		visible: false
	    	},
	    	{
	    	  field: 'custname',
	    	  title: '客户名称',
	    	  sortable : true
	      },{
	    	  field: 'password',
	    	  title: '客户密码',
	    	  sortable: true
	      },{
	    	  field: 'lob',
	          title: '业务线',
	    	  sortable: true
	      },{
	    	  field: 'surveytype',
	    	  title: '问卷类型',
	    	  sortable:true
	      },{
	    	  field: 'evalPersonDep',
	    	  title: '客户评价人部门',
	    	  sortable: true
	      },{
	    	  field: 'evalPersonName',
	    	  title: '客户评价人姓名',
	    	  sortable: true
	      },{
	    	  title: '操作',
	    	  formatter: actionFormatter,
	    	  events: actionEvents
	      }
	      
	      
	      ],
	      onLoadSuccess: function(data){  //加载成功时执行
	    	  if(data.success==true){
	            console.info("加载成功");
	    	  }else{
	    		  if(data.success==false){
		    		  if(data.msg  != ""){
							showAlert('系统提示:' + data.msg,'danger');
					 }else{
							showAlert('系统提示:  加载失败，请联系 xx@chinasoftinc.com','danger');
					}
	    		  }
	    	  }
	      }
	});
	
	
	$('.create').click(function(){
		console.log($(this).text());
		showModal($(this).text());
		console.log(login_user);
		url = "user/add";
		var bootstrapValidator = $("#fm_user").data('bootstrapValidator'); 
		bootstrapValidator.updateStatus('custname', 'NOT_VALIDATED').validateField('custname'); 
		//bootstrapValidator.updateStatus('password', 'NOT_VALIDATED').validateField('password'); 
		bootstrapValidator.updateStatus('evalPersonDep', 'NOT_VALIDATED').validateField('evalPersonDep'); 
		bootstrapValidator.updateStatus('evalPersonName', 'NOT_VALIDATED').validateField('evalPersonName'); 
		initSelectOptions("surveytype","","");
		//initSelectOptions("usertype",login_user);
		initSelectOptions("lob",login_user,"false");
	}); 
	
	$('.batchDel').click(function(){
		Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
	        if (!e) {
	            return;
	        }
             
	        var rows = $("#userTbl").bootstrapTable('getSelections');// 获得要删除的数据
	        if (rows.length == 0) {// rows 主要是为了判断是否选中，下面的else内容才是主要
	        	showAlertModal("请先选择要删除的记录!",'warning');
	            return;
	        } else {
	            var ids = new Array();// 声明一个数组
	            console.log(rows);
	            $(rows).each(function() {// 通过获得别选中的来进行遍历
	                ids.push(this.id);// cid为获得到的整条数据中的一列
	                console.log("id:", this.id);  
	            });
	            delUsers(ids);
	        }
		});
	});		
 
	function delUsers(ids) {
        $.ajax({
            url :'user/delByIds',
            data : "ids=" + ids,
            type : "post",
            dataType : "json",
            success : function(data) {
            	$table.bootstrapTable('refresh');
            }
        });
    }


//	$('#usertype').change(function(){
//		var usertype = $('#usertype').val();
//		console.log(usertype);
//	 	if(usertype == "集团QA" || usertype=="业务线QA" ){
//	 			$('#divSurveyType').hide();
//	 			$("#divEvalPersonDep").hide();
//				$('#divEvalPersonName').hide();
//	 	}else{
//	 		$('#divSurveyType').show();
//	 		$("#divEvalPersonDep").show();
//			$('#divEvalPersonName').show();
//	 	}
// 	})
		
	$modal.find('.submit').click(function(){
		var row = {};
		$modal.find('input[name]').each(function(){
			row[$(this).attr('name')] = $(this).val();
		});
		
		 $('#fm_user').bootstrapValidator('validate');
		 if($('#fm_user').data('bootstrapValidator').isValid()){
		
			$.ajax({
				url: url,
				type: 'POST',
				dataType: 'json',
				data: $('#fm_user').serialize(),
				success: function(data,response,status){
					if(data.success==true){
						setTimeout(function(){
							$modal.modal('hide');
						},3000);
						$table.bootstrapTable('refresh');
						showAlertModal(($modal.data('id')?'修改':'创建')+ ' 客户 成功，登录密码由系统自动创建！','success');
					}else{
						if(data.msg  != ""){
							showAlertModal('系统提示:' + data.msg,'danger');
						}else{
							showAlertModal('系统提示:  保存失败，请联系 xx@chinasoftinc.com','danger');
						}
					}
				},
				error: function(){
					$modal.modal('hide');
					showAlertModal(($modal.data('id')?'修改': '创建') + '用户出错!','danger');
				}
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
  		  isAll: "false"
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
				//document.getElementById('"'+ selectId +'"').options.selectedIndex = 0;
				//selectObj.options.selectedIndex = 0;
			}else {
//				toastr.error('获取[' + selectId +']信息失败，原因: ' + result.errorMessage);
			}
		},
		error: function(result){
//			toastr.error('获取[' + selectId +']信息失败，原因: ' + result.errorMessage);
		}
	})
}

function queryParams(params){
	return {};
}

function actionFormatter(value){
	
	return[
		
		'<a class="update" href="javascript:" title="修改用户"><i class="glyphicon glyphicon-edit"></i></a>',
		'<a class="remove" href="javascript:" title="删除用户"><i class="glyphicon glyphicon-remove-circle"></i></a>',
	].join('');
}

window.actionEvents = {
		'click .update': function(e, value, row){
			
			initSelectOptions("surveytype","","");
			initSelectOptions("lob",login_user,"false"); 
			showModal($(this).attr('title'),row);
			
			url = "user/update?id=" + row.id;
			console.log("id",row.id);
			console.log("url",url);
		},
		'click .remove': function(e, value,row){
			Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                if (!e) {
                    return;
                }
				$.ajax({
					url: 'user/del?id=' + row.id,
					type: 'delete',
					success: function(){
						$table.bootstrapTable('refresh');
						showAlert('成功删除用户!','success');
					},
					error: function() {
						showAlert('删除用户出错!','danger');
					}
				});
			});
			
		}	     
		 
};
 




 