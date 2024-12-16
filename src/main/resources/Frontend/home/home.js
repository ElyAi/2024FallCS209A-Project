$(window).on('load', function () {
    $(".loading").fadeOut(); // 页面加载完成后隐藏加载框
});

// 设置动态字体大小
$(document).ready(function () {
    var whei = $(window).width();
    $("html").css({ fontSize: whei / 20 });

    $(window).resize(function () {
        var whei = $(window).width();
        $("html").css({ fontSize: whei / 20 });
    });
});
