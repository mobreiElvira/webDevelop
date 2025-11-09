// DOM Elements
const taskInput = document.getElementById("todo-input");
const addTaskBtn = document.getElementById("add-task");
const todosList = document.getElementById("todo-list");
const itemsLeft = document.getElementById("items-left");
const clearCompletedBtn = document.getElementById("clear-completed");
const emptyState = document.querySelector(".empty-state");
const dateElement = document.getElementById("date");
const filters = document.querySelectorAll(".filter");

//数据初始化：
let todos = [];
let currentFilter = "all";

//点击添加按钮；
addTaskBtn.addEventListener("click", () => {
  addTodo(taskInput.value);
});
//敲击回车：
taskInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") addTodo(taskInput.value);
});

clearCompletedBtn.addEventListener("click", clearCompleted);

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

  checkEmptyState();
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

function deleteTodo(id) {
  todos = todos.filter((todo) => todo.id !== id);
  saveTodos();
  renderTodos();
}

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

//对应函数：
function clearCompleted() {
  todos = todos.filter((todo) => !todo.completed);
  saveTodos();
  renderTodos();
}
