document.addEventListener('DOMContentLoaded', function () {
    console.log('页面已加载，执行初始主题切换');
    switchTheme('java-topics');  // 初始显示 Java Topics
});

function switchTheme(theme) {
    console.log(`切换到主题：${theme}`);

    // 隐藏所有主题内容
    const allContents = document.querySelectorAll('.theme-content');
    allContents.forEach(content => {
        content.style.display = 'none';
    });

    // 显示选定主题的内容
    const selectedContent = document.querySelector(`.${theme}`);
    if (selectedContent) {
        selectedContent.style.display = 'block';
    } else {
        console.error(`未找到类名为 ${theme} 的元素`);
    }
}
