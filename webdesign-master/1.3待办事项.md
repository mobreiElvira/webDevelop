## html+css+js开发

#### 开发设置：

1、visual studio；

2、vs上安装live server插件；实时查看网页效果；

3、源文件：index.html, style.css, script.js;

4、一般的开发流程：index.html->style.css->script.js

5、参考开发手册：[面向开发者的 Web 技术 | MDN](https://developer.mozilla.org/zh-CN/docs/Web)



#### 项目3：待办事项表

最终效果图：

<img src="img/image-20250622092409409.png" alt="image-20250622092409409" style="zoom:33%;" />

<img src="img/image-20250622091814344.png" alt="image-20250622091814344" style="zoom: 33%;" />

第三方组件库：[font-awesome - Libraries - cdnjs - The #1 free and open source CDN built to make life easier for developers](https://cdnjs.com/libraries/font-awesome)

```html
#复制引入到html文件中；
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css" integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
```

使用的html组件：

```hmtl
<header>,<footer>
```

使用到的font-awesome的有：

```css
圆中V：<i class="fa-solid fa-check-circle"></i>
加号+：<i class="fa-solid fa-plus"></i>
清单：<i class="fa-solid fa-clipboard-list"></i>
×号：<i class="fa-solid fa-times"></i>
```



js获取组件代码：

```js
// DOM Elements
const taskInput = document.getElementById("task-input");
const addTaskBtn = document.getElementById("add-task");
const todosList = document.getElementById("todos-list");
const itemsLeft = document.getElementById("items-left");
const clearCompletedBtn = document.getElementById("clear-completed");
const emptyState = document.querySelector(".empty-state");
const dateElement = document.getElementById("date");
const filters = document.querySelectorAll(".filter");

//数据初始化：
let todos = [];
let currentFilter = "all";
```

触发addTodo的两种方法：点击添加按钮或者回车：

```js
//点击添加按钮；
addTaskBtn.addEventListener("click", () => {
  addTodo(taskInput.value);
});
//敲击回车：
taskInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") addTodo(taskInput.value);
});
```

给删除按钮添加事件：

```js
clearCompletedBtn.addEventListener("click", clearCompleted);
//对应函数：
function clearCompleted() {
  todos = todos.filter((todo) => !todo.completed);
  saveTodos();
  renderTodos();
}
```

js添加新任务主要通过addTodo()函数实现：

<img src="img/deepseek_mermaid_20250622_f760eb.png" alt="deepseek_mermaid_20250622_f760eb" style="zoom: 25%;" />

```js
//addTodo()函数的主要代码逻辑：
function addTodo(text) {
  if (text.trim() === "") return;
  const todo = {
    id: Date.now(),       // 生成唯一ID
    text: text,           // 任务文本内容
    completed: false      // 默认未完成状态
  };
  todos.push(todo);       // 添加到任务数组
  saveTodos();            // 持久化存储
  renderTodos();          // 重新渲染列表
  taskInput.value = "";   // 清空输入框
}
```

将任务数据持久化存储：

```js
function saveTodos() {
  localStorage.setItem("todos", JSON.stringify(todos));
  updateItemsCount();    // 更新底部计数
  checkEmptyState();     // 检查空状态显示
}

function updateItemsCount() {
  const uncompletedTodos = todos.filter((todo) => !todo.completed);
  itemsLeft.textContent = `${uncompletedTodos?.length} item${
    uncompletedTodos?.length !== 1 ? "s" : ""
  } left`;
}

function checkEmptyState() {
  const filteredTodos = filterTodos(currentFilter);
  if (filteredTodos?.length === 0) emptyState.classList.remove("hidden");
  else emptyState.classList.add("hidden");
}
```

重新渲染任务列表：

```js
function renderTodos() {
  todosList.innerHTML = "";
  const filteredTodos = filterTodos(currentFilter); //根据当前的filter先进行过滤;

  filteredTodos.forEach((todo) => {
    const todoItem = document.createElement("li");
    todoItem.classList.add("todo-item");
    if (todo.completed) todoItem.classList.add("completed");

    const checkboxContainer = document.createElement("label");
    checkboxContainer.classList.add("checkbox-container");

    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.classList.add("todo-checkbox");
    checkbox.checked = todo.completed;
    checkbox.addEventListener("change", () => toggleTodo(todo.id));

    const checkmark = document.createElement("span");
    checkmark.classList.add("checkmark");

    checkboxContainer.appendChild(checkbox);
    checkboxContainer.appendChild(checkmark);

    const todoText = document.createElement("span");
    todoText.classList.add("todo-item-text");
    todoText.textContent = todo.text;

    const deleteBtn = document.createElement("button");
    deleteBtn.classList.add("delete-btn");
    deleteBtn.innerHTML = '<i class="fa-solid fa-times"></i>';
    deleteBtn.addEventListener("click", () => deleteTodo(todo.id));

    todoItem.appendChild(checkboxContainer);
    todoItem.appendChild(todoText);
    todoItem.appendChild(deleteBtn);

    todosList.appendChild(todoItem);
  });
}

function filterTodos(filter) {
  switch (filter) {
    case "active":
      return todos.filter((todo) => !todo.completed);
    case "completed":
      return todos.filter((todo) => todo.completed);
    default:
      return todos;
  }
}
```

点击checkbox后的切换函数toggleTodo():

```js
function toggleTodo(id) {
  todos = todos.map((todo) => {
    if (todo.id === id) {
      return { ...todo, completed: !todo.completed };
    }
    return todo;
  });
  saveTodos();
  renderTodos();
}
```

点击check后的删除函数deleteTodo():

```js
function deleteTodo(id) {
  todos = todos.filter((todo) => todo.id !== id);
  saveTodos();
  renderTodos();
}
```

浏览器加载时的初始化设定：

```js
function loadTodos() {
  const storedTodos = localStorage.getItem("todos");
  if (storedTodos) todos = JSON.parse(storedTodos);
  renderTodos();
}

filters.forEach((filter) => {
  filter.addEventListener("click", () => {
    setActiveFilter(filter.getAttribute("data-filter"));
  });
});

function setActiveFilter(filter) {
  currentFilter = filter;

  filters.forEach((item) => {
    if (item.getAttribute("data-filter") === filter) {
      item.classList.add("active");
    } else {
      item.classList.remove("active");
    }
  });
  renderTodos();
}

function setDate() {
  const options = { weekday: "long", month: "short", day: "numeric" };
  const today = new Date();
  dateElement.textContent = today.toLocaleDateString("en-US", options);
}

window.addEventListener("DOMContentLoaded", () => {
  loadTodos();
  updateItemsCount();
  setDate();
});
```





