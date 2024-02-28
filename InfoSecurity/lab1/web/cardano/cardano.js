$(document).ready(() => {
    redrawGrid()
    normalizeInput('encryptText')
    normalizeInput('decryptText')
    hideError('encryptError')
    hideError('decryptError')
})

function normalizeInput(id) {
    const size = Number($('#size').val())
    const item = $(`#${id}`)
    item.val(item.val().slice(0, size * size - 1))
    item.attr('maxLength', size * size - 1)
}

$('#size').on('input', () => {
    redrawGrid()
    normalizeInput('encryptText')
    normalizeInput('decryptText')
})


function redrawGrid() {
    const size = $('#size').val()
    let container = $('#cardano-container')
    container.empty()
    container.css('--grid-rows', size)
    container.css('--grid-cols', size)

    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            let cell = $('<div>')
            cell.addClass('grid-item')
            cell.attr('set', '')
            cell.attr('i', i)
            cell.attr('j', j)
            cell.click(updateGrid)
            container.append(cell)
        }
    }
}

function updateGrid(event) {
    const cell = $(event.target)
    const isSet = cell.attr('set')
    cell.attr('set', isSet === '1' ? '' : '1')
    if (isSet) {
        cell.removeClass('grid-item-selected')
    } else {
        cell.addClass('grid-item-selected')
    }
}

function create2Darr(size, startValue) {
    let arr = [];
    for (let i = 0; i < size; i++) {
        arr[i] = [];
        for (let j = 0; j < size; j++) {
            arr[i][j] = false;
        }
    }
    return arr
}


function getGrid() {
    const size = Number($('#size').val())
    let grid = create2Darr(size, false)
    $('.grid-item').each((index, cell) => {
        if ($(cell).attr('set') === '1') {
            const i = Number($(cell).attr('i'))
            const j = Number($(cell).attr('j'))
            grid[i][j] = true
        }
    })
    return grid
}

$('#encrypt').submit(function (event) {
    event.preventDefault();
    hideError('encryptError')
    let grid = getGrid()
    let source = getFormObj('encrypt').source

    const size = Number($('#size').val())
    source = source.padEnd(size * size, ' ')

    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/cardano/encrypt',
        data: {
            grid: grid,
            source: source
        }
    }).then(function (response) {
        showResult('encryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('encryptError', error.response.data)
    })
})

$('#decrypt').submit(function (event) {
    event.preventDefault();
    hideError('decryptError')
    let grid = getGrid()
    let source = getFormObj('decrypt').source

    const size = Number($('#size').val())
    source = source.padEnd(size * size, ' ')

    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/cardano/decrypt',
        data: {
            grid: grid,
            source: source
        }
    }).then(function (response) {
        showResult('decryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('decryptError', error.response.data)
    })
})

function showResult(id, result) {
    const resultField = $(`#${id}`);
    resultField.val(result);
    resultField.show();
}

function showError(id, message) {
    const errorField = $(`#${id}`);
    errorField.text(message);
    errorField.show();
}

function hideError(id) {
    const errorField = $(`#${id}`);
    errorField.hide();
}

function getFormObj(formId) {
    var formObj = {};
    var inputs = $('#' + formId).serializeArray();
    $.each(inputs, function (i, input) {
        formObj[input.name] = input.value;
    });
    return formObj;
}

