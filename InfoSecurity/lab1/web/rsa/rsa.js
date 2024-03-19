$(document).ready(function () {
    hideError('generateKeyError');
    hideError('encryptError');
    hideError('decryptError');
})

let n;

$('#generateKey').submit(function (event) {
    event.preventDefault();
    hideError('generateKeyError')

    let data = getFormObj('generateKey')

    console.log(data)

    axios({
        baseURL: 'http://localhost:7070',
        method: 'get',
        url: '/rsa/generate',
        params: {
            size: data['size']
        }
    }).then(function (response) {
        showResult('openKey', response.data.publicKey64)
        showResult('openKeyEncrypt', response.data.publicKey64)
        showResult('privateKey', response.data.privateKey64)
        showResult('privateKeyDecrypt', response.data.privateKey64)
        n = response.data.n
    }).catch(function (error) {
        console.log(error)
        showError('generateKeyError', error.response.data)
    })
});


$('#encrypt').submit(function (event) {
    event.preventDefault();
    hideError('encryptError')

    let data = getFormObj('encrypt')

    console.log(data)
    console.log(n)
    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/rsa/encrypt',
        data: {
            n: n,
            input: data['encryptText'],
            key: $("#openKeyEncrypt").val(),
        }
    }).then(function (response) {
        showResult('encryptResult', response.data)
        showResult('decryptText', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('encryptError', error.response.data)
    })
});

$('#decrypt').submit(function (event) {
    event.preventDefault();
    hideError('decryptError')

    let data = getFormObj('decrypt')

    console.log(data)
    console.log(n)
    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/rsa/decrypt',
        data: {
            n: n,
            input: data['decryptText'],
            key: $("#privateKeyDecrypt").val(),
        }
    }).then(function (response) {
        showResult('decryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('decryptError', error.response.data)
    })
});


function getFormObj(formId) {
    var formObj = {};
    var inputs = $('#' + formId).serializeArray();
    console.log(inputs)
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
