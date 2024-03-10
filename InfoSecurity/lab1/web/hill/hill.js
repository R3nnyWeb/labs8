$(document).ready(() => {
    redrawGrid()
    hideError('encryptError')
    hideError('decryptError')
})

$('#size').on('input', () => {
    redrawGrid()
})


function redrawGrid() {
    const size = $('#size').val()
    let container = $('#hill-container')
    container.empty()
    container.css('--grid-rows', size)
    container.css('--grid-cols', size)

    for (let i = 0; i < size; i++) {
        for (let j = 0; j < size; j++) {
            let cell = $('<input type="number" min="0" value="1" max="37">')
            cell.addClass('grid-item')
            cell.attr('set', '')
            cell.attr('i', i)
            cell.attr('j', j)
            container.append(cell)
        }
    }
}

function create2Darr(size, startValue) {
    let arr = [];
    for (let i = 0; i < size; i++) {
        arr[i] = [];
        for (let j = 0; j < size; j++) {
            arr[i][j] = startValue;
        }
    }
    return arr
}


function getGrid() {
    const size = Number($('#size').val())
    let grid = create2Darr(size, 0)
    $('.grid-item').each((index, cell) => {
        const i = Number($(cell).attr('i'))
        const j = Number($(cell).attr('j'))
        grid[i][j] = Number($(cell).val())
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
        url: '/hill/encrypt',
        data: {
            key: grid,
            input: source
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
        url: '/hill/decrypt',
        data: {
            key: grid,
            input: source
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

