
import { Form, Input, Button } from 'antd';
import React from 'react';

const formItemLayout = {
  labelCol: { xs: { span: 24 }, sm: { span: 8 } },
  wrapperCol: { xs: { span: 24 }, sm: { span: 16 } },
};
const tailFormItemLayout = {
  wrapperCol: { xs: { span: 24, offset: 0 }, sm: { span: 16, offset: 8 } },
};

const App = () => {
  const [form] = Form.useForm();
  const onFinish = (values) => {
    console.log('Received values of form: ', values);
  };
  return (
      <Form
        {...formItemLayout}
        form={form}
        name="register"
        onFinish={onFinish}
        scrollToFirstError
      >
        <Form.Item
          name="question"
          label="题目"
          rules={[{
            required: true,
            message: 'Please input the question!',
          }]}
        >
          <Input/>
        </Form.Item>

        <Form.Item
          name="optionA"
          label="选项A"
          rules={[{
            required: true,
            message: 'Please input optionA!',
          }]}
        >
          <Input/>
        </Form.Item>

        <Form.Item
          name="optionB"
          label="选项B"
          rules={[{
            required: true,
            message: 'Please input optionB!',
          }]}
        >
          <Input/>
        </Form.Item>

        <Form.Item
          name="optionC"
          label="选项C"
          rules={[{
            required: true,
            message: 'Please input optionC!',
          }]}
        >
          <Input/>
        </Form.Item>

        <Form.Item
          name="optionD"
          label="选项D"
          rules={[{
            required: true,
            message: 'Please input optionD!',
          }]}
        >
          <Input/>
        </Form.Item>

        <Form.Item
          name="answer"
          label="答案"
          rules={[{
            required: true,
            message: 'Please input the answer!',
          }]}
        >
          <Input/>
        </Form.Item>


        <Form.Item {...tailFormItemLayout}>
          <Button type="primary" htmlType="submit">
            提交
          </Button>
        </Form.Item>
      </Form>
    );
  };
export default App;