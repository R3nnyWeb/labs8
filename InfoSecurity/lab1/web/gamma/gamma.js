$(document).ready(() => {
    hideError('error')
})

$('#form').submit((event) => {
    event.preventDefault()
})

function doCrypt(url) {
    hideError('error')
    const data = getFormObj('form')
    console.log(data)
    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: url,
        data: {
            input: data.text,
            key: stringToBooleanArray(data.key.toString()),
            lsr: stringToBooleanArray(data.LSR.toString())
        }
    }).then(function (response) {
        showResult('result', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('error', error.response.data)
    })
}

$('#crypt').on('click', (event) => {
    doCrypt('/gamma/crypt');
})

$('#encryptHex').on('click', (event) => {
    doCrypt('/gamma/encrypt-hex');
})

$('#decryptHex').on('click', (event) => {
    doCrypt('/gamma/decrypt-hex');
})

function stringToBooleanArray(str) {
    console.log(str.split('').map(char => char === '1' ? 'true' : 'false'))
    return str.split('').map(char => char === '1' ? 'true' : 'false');
}

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
    $('#form').submit()
    var formObj = {};
    var inputs = $('#' + formId).serializeArray();
    $.each(inputs, function (i, input) {
        formObj[input.name] = input.value;
    });
    return formObj;
}