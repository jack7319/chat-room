//表单格式化json
(function($) {
	$.fn.serializeJson = function(arrlist) {
		var serializeObj = {};
		var array = this.serializeArray();
		$(array).each(
				function() {
					// 是否存在键名
					if (serializeObj[this.name]) {
						// 是否为数组
						if ($.isArray(serializeObj[this.name])) {
							serializeObj[this.name].push(this.value);
						} else {
							serializeObj[this.name] = [ serializeObj[this.name], this.value ];
						}
					} else if (this.name != "") {
						if (this.name == arrlist) {
							serializeObj[this.name] = [ this.value ];
						} else {
							serializeObj[this.name] = this.value;
						}
					}
				});
		return serializeObj;
	};
})(jQuery);


var contentpath = $("#contextPath").val();
//判断是否是在手机微信上打开url
var ua = navigator.userAgent.toLowerCase();
var isWeixin = ua.indexOf('micromessenger') != -1;

var singupurl = "http://m.bizideal.cn/#/meetSignUp";