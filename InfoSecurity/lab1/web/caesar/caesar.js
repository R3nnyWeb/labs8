$(document).ready(function () {
    hideError('encryptError');
    hideError('decryptError');
    hideError('tryDecryptError');

})

$('#encrypt').submit(function (event) {
    event.preventDefault();
    hideError('encryptError')

    let data = getFormObj('encrypt')

    console.log(data)
    if (data['end'] < data['start']) {
        showError('encryptError', 'Конец алфавита не может быть меньше начала алфавита')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'get',
        url: '/caesar/encrypt',
        params: data
    }).then(function (response) {
        showResult('encryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('encryptError', error.response.data)
    })
});

$('#decrypt').submit(function (event) {
    event.preventDefault();
    hideError('decryptError')

    let data = getFormObj('decrypt')

    if (data['end'] < data['start']) {
        showError('decryptError', 'Конец алфавита не может быть меньше начала алфавита')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'get',
        url: '/caesar/decrypt',
        params: data
    }).then(function (response) {
        showResult('decryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('decryptError', error.response.data)
    })
});

$('#tryDecrypt').submit(function (event) {
    event.preventDefault();
    hideError('tryDecryptError')

    $('#tryDecryptResult').empty()

    let data = getFormObj('tryDecrypt')

    console.log(data)
    if (data['end'] < data['start']) {
        showError('tryDecryptError', 'Конец алфавита не может быть меньше начала алфавита')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'get',
        url: '/caesar/decrypt-force',
        params: data
    }).then(function (response) {
        let ul = $('#tryDecryptResult')
        for (const i in response.data) {
            $('<li/>')
                .text(response.data[i])
                .addClass('list-group-item')
                .appendTo(ul)
        }
    }).catch(function (error) {
        console.log(error)
        showError('tryDecryptError', error.response.data)
    })
});

function getFormObj(formId) {
    var formObj = {};
    var inputs = $('#' + formId).serializeArray();
    $.each(inputs, function (i, input) {
        formObj[input.name] = input.value;
    });
    return formObj;
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