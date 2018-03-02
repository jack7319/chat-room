var userValidate = {
    init:function () {
        this.save();
        // this.validate('');
    },
    validate:function (formId) {
        return $('#'+formId).validate({
            rules:{
                name:{
                    required:true
                },
                phone:{
                    required:true,
                    isPhone:true
                },
                age:{
                    required:true,
                    digits:true
                }
            },
            messages:{
                name:{
                    required:"<span style='color:red;'>姓名不能为空</span>"
                },
                phone:{
                    required:"<span style='color:red;'>手机号不能为空</span>",
                    isPhone:"<span style='color:red;'>手机号格式不正确</span>"
                },
                age:{
                    required:"<span style='color:red;'>年龄不能为空</span>",
                    digits:"<span style='color:red;'>请输入正确的年龄</span>"
                }
            }
        }).form();
    },
    save:function () {
        var _this = this;
        $(document).on('click','#_add_btn',function () {
            if (!_this.validate('testForm')){
                return;
            }
            alert('ok');
        })
    }
}