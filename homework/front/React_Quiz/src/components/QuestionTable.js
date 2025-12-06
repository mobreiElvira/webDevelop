import { Space, Table } from 'antd';
import React, { useState } from 'react';

const columns = [
    {
        title: '序号',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: '问题',
        dataIndex: 'question',
        key: 'question',
        // 修复：a标签添加href（#表示空链接，或用button替代）
        render: (text) => <a href="#" onClick={(e) => e.preventDefault()}>{text}</a>,
    },
    {
        title: '选项',
        dataIndex: 'options',
        key: 'options',
        render: (options) => options.join(', ')
    },
    {
        title: '答案',
        dataIndex: 'answer',
        key: 'answer',
    },
    {
        title: '操作',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                {/* 修复：a标签添加href + 阻止默认行为，或替换为button */}
                <a href="#" onClick={(e) => {
                    e.preventDefault();
                    // 编辑逻辑可写在这里
                    console.log('编辑', record);
                }}>编辑</a>
                <a href="#" onClick={(e) => {
                    e.preventDefault();
                    // 删除逻辑可写在这里
                    console.log('删除', record);
                }}>删除</a>
            </Space>
        ),
    },
];

const data = [
    {
        key: '1',
        id: '1',
        question: 'React中useState和useRef的主要区别是什么？',
        options: [
            'A. useState触发重渲染，useRef不触发',
            'B. useRef只能存储DOM元素，useState可以存储任意类型',
            'C. useState是异步的，useRef是同步的',
            'D. 两者没有本质区别'
        ],
        answer: 'A'
    },
    {
        key: '2',
        id: '2',
        question: '以下哪种方式可以有效避免React组件的不必要重渲染？',
        options: [
            'A. 使用useState替代useReducer',
            'B. 使用React.memo包裹组件',
            'C. 减少组件内部的state数量',
            'D. 将所有状态提升到父组件'
        ],
        answer: 'B'
    },
    {
        key: '3',
        id: '3',
        question: 'React Hooks中，useEffect的第二个参数为空数组时，表示什么含义？',
        options: [
            'A. 每次渲染都执行副作用',
            'B. 仅在组件挂载和卸载时执行',
            'C. 永不执行副作用',
            'D. 仅在组件更新时执行'
        ],
        answer: 'B'
    },
    {
        key: '4',
        id: '4',
        question: 'Redux的核心原则不包括以下哪一项？',
        options: [
            'A. 单一数据源',
            'B. 状态是只读的',
            'C. 使用异步操作修改状态',
            'D. 使用纯函数执行修改'
        ],
        answer: 'C'
    },
    {
        key: '5',
        id: '5',
        question: 'React Router v6中，以下哪个组件替代了v5中的Switch组件？',
        options: [
            'A. Routes',
            'B. RouteGroup',
            'C. RouterSwitch',
            'D. Navigator'
        ],
        answer: 'A'
    },
    {
        key: '6',
        id: '6',
        question: '以下关于React虚拟DOM的描述，错误的是？',
        options: [
            'A. 虚拟DOM是真实DOM的内存表示',
            'B. 虚拟DOM可以提高DOM操作的性能',
            'C. 每次状态变化都会重新渲染整个虚拟DOM树',
            'D. React通过diff算法计算虚拟DOM的差异'
        ],
        answer: 'C'
    },
    {
        key: '7',
        id: '7',
        question: '在React中，什么是JSX？',
        options: [
            'A. 一种新的编程语言',
            'B. JavaScript的扩展语法，用于描述UI',
            'C. React的状态管理工具',
            'D. React的路由配置格式'
        ],
        answer: 'B'
    }
];

const QuestionTable = () => {
    const [currentPage, setCurrentPage] = useState(1);
    
    const handleTableChange = (pagination) => {
        setCurrentPage(pagination.current);
        console.log('当前页码：', pagination.current);
    };

    return (
        <Table
            columns={columns}
            dataSource={data}
            pagination={{
                current: currentPage,
                pageSize: 5,
                total: data.length,
                showSizeChanger: false,
                position: ['bottomLeft'],
                showQuickJumper: false,
                showTotal: false
            }}
            onChange={handleTableChange}
            rowKey="key"
        />
    );
};

export default QuestionTable;