'use strict';

async function initPage() {
    showHideBlocks('block','none','none');
    const urlController = 'index';
    const response = await fetch(urlController);
    if(response.ok){
        try{
            const json = await response.json();
            const userName = json.username;
            const error = json.error;
            const menu = document.getElementById("menu");
            let buttons;
            if(userName == null || userName === "") {
                buttons = [
                    ['User: Guest', './index.html',             'menuButtonLeft'],
                    ['Registration','./index.html?register=yes','menuButtonRight'],
                    ['Login',       './index.html?login=yes',   'menuButtonRight']
                ];
            }else{
                buttons = [
                    ['User: ', './index.html',               'menuButtonLeft'],
                    ['Tasks',  './main.html?list_type=today','menuButtonLeft'],
                    ['Logout', './logout',                   'menuButtonRight']
                ];
                buttons[0][0] += userName;
            }
            for(let i=0; i<buttons.length; i++){
                const li = document.createElement('li');
                li.setAttribute('id',   buttons[i][2]);
                menu.appendChild(li);
                const a = document.createElement('a');
                a.setAttribute('href', buttons[i][1]);
                li.appendChild(a);
                a.innerHTML = buttons[i][0];
            }
            showLogRegForms();
            printError(error);
        }catch (e) {
            alert("Server not response: " + e.message);
        }
    }else {
        alert("HTTP error: " + response.status);
    }
}

function showHideBlocks(infoBlock, loginBlock, reginBlock) {
    document.getElementById('index-form').style.display = infoBlock;
    document.getElementById('login-form').style.display = loginBlock;
    document.getElementById('regin-form').style.display = reginBlock;
}

function printError(errorMessage) {
    if(errorMessage != null){
        document.getElementById("error").innerHTML = errorMessage;
    }
}

function showLogRegForms() {
    const url = new URLSearchParams(location.search);
    const login = url.get("login");
    const register = url.get("register");
    if(login){
        showHideBlocks('none','block', 'none');
        return;
    }
    if(register){
        showHideBlocks('none','none', 'block');
        return;
    }
}

async function doLogin() {
    if(document.loginform.username.value === ""){
        document.getElementById("error").innerHTML = "Account is empty";
        return false;
    }
    if(document.loginform.password.value === ""){
        document.getElementById("error").innerHTML = "Password is empty";
        return false;
    }
    document.loginform.submit();
    const urlController = 'login';
    const response = await fetch(urlController);
    if(response.ok) {
        try {
            const json = await response.json();
            const userName = json.username;
            const error = json.error;
            console.log(userName + " " + error);
            if(userName == null || userName == ""){
                printError(error);
            }
        }catch (e) {
            alert("Server not response: " + e.message);
        }
    }else {
        alert("HTTP error: " + response.status);
    }
}

async function doRegistration() {
    if(document.regform.username.value === ""){
        document.getElementById("error").innerHTML = "Account is empty";
        return false;
    }
    if(document.regform.password.value === ""){
        document.getElementById("error").innerHTML = "Password is empty";
        return false;
    }
    if(document.regform.email.value === ""){
        document.getElementById("error").innerHTML = "EMail is empty";
        return false;
    }
    document.regform.submit();
    const urlController = 'regin';
    const response = await fetch(urlController);
    if(response.ok) {
        try {
            const json = await response.json();
            const userName = json.username;
            const error = json.error;
            console.log(userName + " " + error);
            if(userName == null || userName == ""){
                printError(error);
            }
        }catch (e) {
            alert("Server not response: " + e.message);
        }
    }else {
        alert("HTTP error: " + response.status);
    }
}