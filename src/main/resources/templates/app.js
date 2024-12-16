document.addEventListener('DOMContentLoaded', () => {
    const refreshButton = document.getElementById('refreshButton');
    const visualizationTypeSelect = document.getElementById('visualizationType');
    const topNInput = document.getElementById('topN');
    const loadingSection = document.getElementById('loadingSection');
    const themeToggle = document.getElementById('themeToggle');

    let currentChart = null;

    // 事件监听：刷新数据按钮
    refreshButton.addEventListener('click', () => {
        const visualizationType = visualizationTypeSelect.value;
        const topN = topNInput.value;
        const apiEndpoint = `/api/${visualizationType}?topN=${topN}`;

        fetchAndRenderChart(apiEndpoint, 'dataChart', getChartTitle(visualizationType));
    });

    // 事件监听：切换主题按钮
    themeToggle.addEventListener('click', () => {
        document.body.classList.toggle('dark-mode');
    });

    // 初始化加载第一个图表
    fetchAndRenderChart('/api/topics?topN=10', 'dataChart', '热门Java主题');

    // 根据类型获取图表标题
    function getChartTitle(type) {
        const titles = {
            'topics': '热门Java主题',
            'engagement': '用户参与度',
            'errors': '常见错误和异常',
            'answer-quality': '答案质量分析'
        };
        return titles[type] || '数据可视化';
    }

    // 获取数据并渲染图表
    function fetchAndRenderChart(apiEndpoint, canvasId, chartTitle) {
        loadingSection.classList.remove('hidden');

        fetch(apiEndpoint)
            .then(response => response.json())
            .then(data => {
                loadingSection.classList.add('hidden');
                const labels = data.map(item => item.label);
                const values = data.map(item => item.value);

                const ctx = document.getElementById(canvasId).getContext('2d');
                if (currentChart) {
                    currentChart.destroy();
                }

                currentChart = new Chart(ctx, {
                    type: 'bar',
                    data: {
                        labels: labels,
                        datasets: [{
                            label: chartTitle,
                            data: values,
                            backgroundColor: 'rgba(75, 192, 192, 0.6)',
                            borderColor: 'rgba(75, 192, 192, 1)',
                            borderWidth: 1
                        }]
                    },
                    options: {
                        responsive: true,
                        scales: {
                            y: {
                                beginAtZero: true
                            }
                        }
                    }
                });

                document.getElementById('chartTitle').textContent = chartTitle;
            })
            .catch(error => {
                loadingSection.classList.add('hidden');
                console.error('Error fetching data:', error);
            });
    }
});
