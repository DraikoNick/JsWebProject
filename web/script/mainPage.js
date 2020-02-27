'use strict';

let tasks;
let btnEach;
let btnSection;
let sections;

//get data from server
async function initPage() {
    showHideBlocks('block');
    let url = new URL(window.location.href);
    let param = url.searchParams.get("list_type");
    const urlController = 'section?list_type=' + param;
    const response = await fetch(urlController);
    if (response.ok) {
        try {
            const json = await response.json();
            tasks = json.tasks;
            btnEach = json.btnEach;
            btnSection = json.btnSection;
            sections = json.sections;
            initSectionsMenu();
            printTasksButtons(param);
            printTasks();
        } catch (e) {
            alert("Server not response: " + e.message);
        }
    } else {
        alert("HTTP error: " + response.status);
    }
}
function showHideBlocks(sectionBlock) {
    document.getElementById('sectionBlock').style.display = sectionBlock;
}

//draw page
function initSectionsMenu() {
    const menu = document.getElementById("menu");
    for(let btn of sections){
        const li = document.createElement('li');
        li.setAttribute('id', 'menuButtonLeft');
        menu.appendChild(li);
        const a = document.createElement('a');
        a.setAttribute('href', `./main.html?list_type=${btn.key}`);
        li.appendChild(a);
        a.innerHTML = `${btn.value}`;
    }
    const li = document.createElement('li');
    li.setAttribute('id', 'menuButtonRight');
    menu.appendChild(li);
    const a = document.createElement('a');
    a.setAttribute('href', `./logout`);
    li.appendChild(a);
    a.innerHTML = `Logout`;
}
function printTasks() {
    const tasksBlock = document.getElementById("tasks");
    for(let task of tasks){
        const tr = document.createElement('div');
        tr.setAttribute('class', 'rTableRow');
        tasksBlock.appendChild(tr);

        const td = document.createElement('div');
        td.setAttribute('class', 'rTableCell');
        const input = document.createElement('input');
        input.setAttribute("type", "checkbox");
        input.setAttribute("id", `check${task.id}`);
        input.setAttribute("name", "idTask");
        input.setAttribute('value', `${task.id}`);
        tr.appendChild(td);
        td.appendChild(input);

        const tdName = document.createElement('div');
        tdName.setAttribute('class', 'rTableCell');
        tdName.innerHTML = `${task.name}`;
        tr.appendChild(tdName);

        const tdDate = document.createElement('div');
        tdDate.setAttribute('class', 'rTableCell');
        tdDate.innerHTML = `${task.date}`;
        tr.appendChild(tdDate);

        printButtonsForEachTask(task.id, tr);

        if(task.fileName != null){
            const tdFile = document.createElement('td');
            const aFile = document.createElement('a');
            aFile.setAttribute('href', `download?file=${task.fileName}`);
            aFile.innerHTML = `${task.fileName}`;
            tr.appendChild(tdFile);
            tdFile.appendChild(aFile);
        }
    }
}
function printButtonsForEachTask(taskId, tr) {
    const td = document.createElement('div');
    td.setAttribute('class', 'rTableCell');
    for(let btn of btnEach){
        const input = document.createElement('input');
        input.setAttribute('type', 'submit');
        input.setAttribute('onclick', `doAction(` + taskId + `, \"${btn.value}\")`);
        input.setAttribute('name', 'action');
        input.setAttribute('value', `${btn.key}`);
        td.appendChild(input);
    }
    tr.appendChild(td);
}
function printTasksButtons(param) {
    const buttons = document.getElementById("buttons");
    const hidInput = document.createElement('input');
    hidInput.setAttribute('type', 'hidden');
    hidInput.setAttribute('name', 'pressed_button');
    buttons.appendChild(hidInput);

    const trBtn = document.createElement('div');
    trBtn.setAttribute('class', 'rTableRow');
    const tdBtn = document.createElement('div');
    tdBtn.setAttribute('class','rTableCell');
    trBtn.appendChild(tdBtn);

    if(tasks.length >=1 ){
        for(let btn of btnSection){
            const input = document.createElement('input');
            input.setAttribute('type', 'button');
            input.setAttribute('onclick', `doActions(\"${btn.value}\")`);
            input.setAttribute('name', 'action');
            input.setAttribute('value', `${btn.key}`);
            buttons.appendChild(input);
        }
    }

    const formAdd = document.getElementById("form_add");
    const trBtns = document.createElement('div');
    trBtns.setAttribute('class','rTableCell');
    formAdd.appendChild(trBtns);
    if(param != "FIXED" && param != "RECBYN"){
        const inputAdd = document.createElement('input');
        inputAdd.setAttribute('type', 'button');
        inputAdd.setAttribute('onclick', `addTask(\"ADD0\")`);
        inputAdd.setAttribute('name', 'action');
        inputAdd.setAttribute('value', "Add");
        trBtns.appendChild(inputAdd);
    }

}

//actions
function doClearChecks() {
    var checks = document.getElementsByName('idTask');
    var isChecked = false;
    for(var i=0; i<checks.length; i++){
        if(checks[i].checked){
            checks[i].checked = false;
        }
    }
}
function submitForm(value) {
    document.form_tasks = document.getElementById('form_tasks');
    document.form_tasks.pressed_button = document.getElementById('pressed_button');
    document.form_tasks.pressed_button.value = value;
    document.form_tasks.action = "/web/action?action=" + value;
    document.form_tasks.submit();
}
function doAction(id, value) {
    doClearChecks();
    const currentCheck = document.getElementById('check' + id);
    currentCheck.checked = true;
    submitForm(value);
}
function doActions(value) {
    const checks = document.getElementsByName('idTask');
    let isChecked = false;
    for(var i=0; i<checks.length; i++){
        if(checks[i].checked){
            isChecked = true;
            break;
        }
    }
    if(isChecked == true){
        submitForm(value);
    }else{
        const error = document.getElementById('error');
        error.innerHTML = "Please check any task and try again.";
    }
}
function addTask(value) {
    document.form_add = document.getElementById('form_add');
    /*document.form_add.action = document.getElementById('pressed_button');
    document.form_add.action.value = value;*/
    document.getElementById('actionAdd').setAttribute('value','ADD0');
    document.form_add.action = "/web/action.html?action=" + value;
    document.form_add.submit();
}