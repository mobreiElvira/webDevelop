import { Space, Table } from 'antd';
import React, { useState } from 'react'; // 修复：导入useState

const columns = [
    {
        title: '日期',
        dataIndex: 'date',
        key: 'date',
    },
    {
        title: '用户名',
        dataIndex: 'name',
        key: 'name',
        // 修复：a标签添加href并阻止默认行为
        render: (text) => <a href="#" onClick={(e) => e.preventDefault()}>{text}</a>,
    },
    {
        title: '密码',
        dataIndex: 'password',
        key: 'password',
    },
    {
        title: '操作',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                {/* 修复：a标签添加href + 点击逻辑 */}
                <a href="#" onClick={(e) => {
                    e.preventDefault();
                    console.log('编辑用户', record);
                }}>编辑</a>
                <a href="#" onClick={(e) => {
                    e.preventDefault();
                    console.log('删除用户', record);
                }}>删除</a>
            </Space>
        ),
    },
];

const data = [
    {
        key: '1',
        password: 'Admin@123456',
        name: 'zhangsan888',
        date: '2025-09-01'
    },
    {
        key: '2',
        password: 'LiSi_7890',
        name: 'lisi789',
        date: '2025-09-05'
    },
    {
        key: '3',
        password: 'WangWu@2025',
        name: 'wangwu2025',
        date: '2025-09-08'
    },
    {
        key: '4',
        password: 'ZhaoLiu#666',
        name: 'zhaoliu666',
        date: '2025-09-10'
    },
    {
        key: '5',
        password: 'QianQi_8888',
        name: 'qianqi8888',
        date: '2025-09-12'
    },
    {
        key: '6',
        password: 'SunBa999@abc',
        name: 'sunba999',
        date: '2025-09-15'
    },
    {
        key: '7',
        password: 'WuJiu_0000',
        name: 'wujiu0000',
        date: '2025-09-18'
    }
];

const UserTable = () => {
    const [currentPage, setCurrentPage] = useState(1);
    
    // 修复：删除未使用的onChange变量，或使用它
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

export default UserTable;