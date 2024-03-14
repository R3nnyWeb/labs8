$(document).ready(function () {
    hideError('encryptError');
    hideError('encryptError2');
    hideError('decryptError');
})

$('#fillKey').click((e) => {
    let data = getFormObj('encrypt')
    if(data.key.length < data.input.length) {
        let multiply = data.input.length / data.key.length + 1
        data.key = data.key.repeat(multiply)
        $('#encryptKey').val(data.key)
    }
    $('#encryptKey').val(data.key.slice(0, data.input.length))

})

$('#fillKey2').click((e) => {
    let data = getFormObj('encrypt2')
    if(data.key.length < data.input.length) {
        let multiply = data.input.length / data.key.length + 1
        data.key = data.key.repeat(multiply)
        $('#encryptKey2').val(data.key)
    }
    $('#encryptKey2').val(data.key.slice(0, data.input.length))

})

$('#generateKey').click((e) => {
    let data = getFormObj('encrypt2')
    let key = $('#encryptKey2')
    axios({
        baseURL: 'http://localhost:7070',
        method: 'get',
        url: '/vernam/generate',
        params: {
            size : data['input'].length
        }
    }).then(function (response) {
        key.val(response.data)
    })
})

$('#encrypt').submit(function (event) {
    event.preventDefault();
    hideError('encryptError')

    let data = getFormObj('encrypt')

    console.log(data)
    if (data['input'].length !== data['key'].length) {
        showError('encryptError', 'Ключ и текст должны быть одинаковой длины')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/vizhener/encrypt',
        data: {
            input: data['input'],
            key: data['key']
        }
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

     console.log(data)
    if (data['input'].length !== data['key'].length) {
        showError('decryptError', 'Ключ и текст должны быть одинаковой длины')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/vizhener/decrypt',
        data: {
            input: data['input'],
            key: data['key']
        }
    }).then(function (response) {
        showResult('decryptResult', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('decryptError', error.response.data)
    })
});

$('#encrypt2').submit(function (event) {
    event.preventDefault();
    hideError('encryptError2')

    let data = getFormObj('encrypt2')

    console.log(data)
    if (data['input'].length !== data['key'].length) {
        showError('encryptError2', 'Ключ и текст должны быть одинаковой длины')
        return
    }

    axios({
        baseURL: 'http://localhost:7070',
        method: 'post',
        url: '/vernam/crypt',
        data: {
            input: data['input'],
            key: data['key']
        }
    }).then(function (response) {
        showResult('encryptResult2', response.data)
    }).catch(function (error) {
        console.log(error)
        showError('encryptError2', error.response.data)
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