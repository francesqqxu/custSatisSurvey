/**
 * 
 */
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
 


$(function(){
	
	 console.log("questionnaireLaborCh.js");
	 
	 
	if (window.history && window.history.pushState) { 
	     $(window).on('popstate', function () { 
	    	 window.history.pushState('forward', null, '#'); 
	    	 window.history.forward(1); 
	      }); 
	 } 
	 window.history.pushState('forward', null, '#'); //在IE中必须得有这两行 
	 window.history.forward(1); 
	 
	 $('#evalDate').datetimepicker({
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
	    	$('#fm_quesLaborCh').data('bootstrapValidator')
	    	.updateStatus('evalDate','NOT_VALIDATED',null)
	    	.validateField('evalDate');
	    });
	 
	 $("#evalDate").datetimepicker("setDate", new Date());
	 
	 $("#fm_quesLaborCh").bootstrapValidator({
		 
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
        	 custName: {
                validators: {
                      notEmpty: {
                    	  message: "客户名称不能为空"
                      }
                 }
             },
             comprehensiveEval1 :{
            	 validators: {
            		 notEmpty: {
            			 message: "综合评价1必选"
            		 }
            	 }
             },
             evalPersonDep: {
                 validators: {
                      notEmpty: {
                    	  message: "评价人部门不能为空"
                      }
                 }
             },
             evalPersonName: {
                 validators: {
                      notEmpty: {
                    	  message: "评价人姓名不能为空"
                      }
                 }
             },
             evalDate: {
                 validators: {
                      notEmpty: {
                    	  message: "调查日期不能为空"
                      },
                      date: {//验证指定的日期格式
                          format: 'yyyy-mm-dd',
                          message: '日期格式不正确'
                      }
                      
                 }
             }
         }
 
	 });
//	 
	 
	 $("#quesLaborCh_add").click(function(){
		 
		 Ewin.confirm({ message: "请确认是否提交问卷，提交问卷后问卷内容无法修复?" }).on(function (e) {
			 if(!e){
				 return;
			 }
			 $('#fm_quesLaborCh').bootstrapValidator('validate');
			 if($('#fm_quesLaborCh').data('bootstrapValidator').isValid()){
			
				$.ajax({
					url: "/custSatisSurvey/project/add",
					type: 'POST',
					timeout: 100000,
					dataType:'json',
					data:   $('#fm_quesLaborCh').serialize(),
								
					success: function(data,response,status){
						if(data.success==true){
							toastr.success("系统提示","保存成功！");
							setTimeout(function(){
								window.location.replace("/endMessage");
							},1000);
							
							window.history.forward(1);
						}
						else{
							if(data.msg  != ""){
								toastr.warning("系统提示: ",data.msg);
							}else{
								toastr.warning("系统提示: ",  " 保存失败，请联系 xx@chinasoftinc.com");
							}
						}
							
					},
					error: function(data){
				      	   toastr.warning("出错，请检查数据！");
					}
					
				}); //end ajax
			 } else{
				 toastr.warning("系统提示","验证不成功,请检查必填项目！");  
			 }
		 }); //if end confirm
	});
	
	
});

