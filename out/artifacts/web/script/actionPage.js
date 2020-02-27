'use strict';

let tasksToChange;
let sections;
let todayDate;

async function initPage() {
    todayDate = new Date().toLocaleDateString('en-CA');
    const urlController = 'action';
    const response = await fetch(urlController);
    if (response.ok) {
        try {
            const json = await response.json();
            tasksToChange = json.tasksToChange;
            sections = json.sections;
            initSectionsMenu();
        } catch (e) {
            alert("Server not response: " + e.message);
        }
    } else {
        alert("HTTP error: " + response.status);
    }

    let url = new URL(window.location.href);
    let param = url.searchParams.get("action");
    if(param === 'CHANGE0'){     //changeBlock
        showHideBlocks('block', 'none');
        initChangeBlock();
    }else if (param === 'ADD0'){  //addBlock
        showHideBlocks('none', 'block');
        const taskDate = document.getElementById('taskDate');
        taskDate.setAttribute('value', todayDate);
        taskDate.setAttribute('min', todayDate);
    }else{
        console.log('param - ' + param);
    }
}

function showHideBlocks(changeBlock, addBlock) {
    document.getElementById('taskToChange').style.display = changeBlock;
    document.getElementById('taskToAdd').style.display = addBlock;
}

async function initChangeBlock() {
    const taskToChange = document.getElementById('taskToChange');
    for(let task of tasksToChange){
        const tr = document.createElement('div');
        tr.setAttribute('class', 'rTableRow');
        taskToChange.appendChild(tr);

        const inputHidden = document.createElement('input');
        inputHidden.setAttribute("type", "hidden");
        inputHidden.setAttribute("id", `taskid${task.id}`);
        inputHidden.setAttribute("name", "taskid");
        inputHidden.setAttribute('value', `${task.id}`);
        tr.appendChild(inputHidden);

        const tdName = document.createElement('div');
        tdName.setAttribute('class', 'rTableCell');
        const inputName = document.createElement('input');
        inputName.setAttribute("type", "text");
        inputName.setAttribute("id", `taskName${task.id}`);
        inputName.setAttribute("name", `taskName${task.id}`);
        inputName.setAttribute('value', `${task.name}`);
        tr.appendChild(tdName);
        tdName.appendChild(inputName);

        const tdDate = document.createElement('div');
        tdDate.setAttribute('class', 'rTableCell');
        const inputDate = document.createElement('input');
        inputDate.setAttribute("type", "date");
        inputDate.setAttribute("id", `taskDate${task.id}`);
        inputDate.setAttribute("name", `taskDate${task.id}`);
        inputDate.setAttribute('value', task.date);
        inputDate.setAttribute('min', todayDate);
        tr.appendChild(tdDate);
        tdDate.appendChild(inputDate);

        const tdSelect = document.createElement('div');
        tdSelect.setAttribute('class', 'rTableCell');
        const select = document.createElement('select');
        select.setAttribute('id', `statusList${task.id}`);
        select.setAttribute('name', `taskStatus${task.id}`);
        tr.appendChild(tdSelect);
        tdSelect.appendChild(select);
        getOptionSelect(true, task.status, select);
        getOptionSelect(false, 'TODO', select);
        getOptionSelect(false, 'FIXED', select);
        getOptionSelect(false, 'DELETED', select);

        const tdFile = document.createElement('div');
        tdFile.setAttribute('class', 'rTableCell');
        const inputFile = document.createElement('input');
        inputFile.setAttribute("type", "file");
        inputFile.setAttribute("name", "fileName");
        if(task.fileName != null){
            tdFile.innerHTML = task.fileName;
        }
        tr.appendChild(tdFile);
        tdFile.appendChild(inputFile);
    }
    const btn = document.createElement('input');
    btn.setAttribute('type','button');
    btn.setAttribute('id', 'change');
    btn.setAttribute('name', 'savebtn');
    btn.setAttribute('value', 'Change');
    btn.setAttribute('onclick', 'confirmChanges(\"Change\")');
    taskToChange.appendChild(btn);
}

function getOptionSelect(selectedBoolean, statusName, select) {
    const taskStat = document.createElement('option');
    taskStat.setAttribute('value', statusName);
    if(selectedBoolean){
        taskStat.setAttribute('selected', 'selected');
    }
    taskStat.innerHTML = statusName;
    select.appendChild(taskStat);
}

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

function confirmAdding(value) {
    var name = document.getElementById('taskName');
    var error = document.getElementById('error');
    if(name === ''){
        error.innerHTML = "Please enter task";
        return;
    }
    //need to add check date
    document.form_change = document.getElementById('form_change');
    document.form_change.pressed_button = document.getElementById('pressed_button');
    document.form_change.pressed_button.value = value;
    document.form_change.action = '/web/action' + value;
    document.form_change.submit();
}
function confirmChanges(value) {
    document.form_change = document.getElementById('form_change');
    document.form_change.pressed_button = document.getElementById('pressed_button');
    document.form_change.pressed_button.value = value;
    document.form_change.action = '/web/action' + value;
    document.form_change.submit();
}