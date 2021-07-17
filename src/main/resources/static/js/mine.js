<!--移动端菜单按钮-->
$('.menu.toggle').click(function () {
    $('.m-item').toggleClass('m-mobile-hide');
});
$('.ui.dropdown').dropdown({
    on:'hover'
});

//page赋值
function page(obj){
    var page = $(obj).data("page");
    var current = $(obj).data("current");
    var max = $(obj).data("max");
    if(page != current && page > 0 && page <= max){
        $("[name='page']").val($(obj).data("page"));
        loaddata();
    }

}


//点击搜索发送ajax请求
$("#search-btn").click(function () {
    loaddata();
});

//表单发送ajax请求
function loaddata() {
    $("#table-container").load(/*[[@{/admin/blogs/search}]]*/"/admin/blogs/search",{
        title : $("[name='title']").val(),
        typeId : $("[name='typeId']").val(),
        recommend : $("[name='recommend']").prop('checked'),
        pageNum : $("[name='page']").val()
    });
}

//点击关闭提示
$('.message .close')
    .on('click',function () {
        $(this)
            .closest('.message')
            .transition('fade');
    });

//点击情空
$('#clear-btn').on('click',function(){
    $('.type.ui.dropdown').dropdown('clear');
});


//删除模态框:确认删除
$('#delete-btn').on('click',function () {
    window.location.href = $('#delete').val();
    $('.btn-close').trigger("click");
});








