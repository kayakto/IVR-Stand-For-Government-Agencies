export {setStateItem, getStateItem, clearStorage}
function setStateItem(name, item) {
    localStorage.setItem(name, JSON.stringify(item));
}

function getStateItem(name) {
    return JSON.parse(localStorage.getItem(name));
}

function clearStorage() {
    localStorage.clear()
}

