var $ = jQuery;

$( document ).ready(function() {
    function doGet() {
        return new Promise((resolve, reject) => {
            $.post({
                url: "/mavenproject2/Ajax",
                data: { "medidores": "medidores" },
                success: function(data) {
                    $('#tabelaMedidores tbody').remove();
                    $.each(data, function(i, data) {
                        $('#tabelaMedidores').append("<tbody><tr><td contenteditable='true' id='" + i + "_nome_editavel'>" + data["serialno_medidores"] + "</td><td contenteditable='true' id='" + i + "_nome_editavel'>" +  data["nome"] +
                        "</td><td>" + data["tabela"] + "</td><td>" + '<form >' +
                        '<input type="hidden" value="' + data["serialno_medidores"] + " id=" + i + '_serial" name="serialno_medidores" />' +
                        '<input type="hidden" value="' + data["nome"] + ' id="' + i + '_nome" name="nome" />' +
                        '<input type="hidden" value="' + data["tabela"] + '" id="' + i + '" name="tabela" />' +
                        '<input type="hidden" value="edit" name="operation" />' + '<button class="btn btn-success fas fa-check-circle"></button>' +
                        '<script>' +
                            'document.getElementById("' + i + '_serial_editavel").addEventListener("keyup", function() {' +
                                'document.getElementById("' + i + '_serial").value = document.getElementById("' +
                                i + '_serial_editavel").innerHTML;});' +
                            'document.getElementById("' + i + '_nome_editavel").addEventListener("keyup", function() {' +
                                'document.getElementById("' + i + '_nome").value = document.getElementById("' + i + '_nome_editavel").innerHTML;});' +
                        '</script></form></td><td><form >' +
                        '<input type="hidden" value="' + data["tabela"] + '" name="tabela" />' +
                        '<input type="hidden" value="delete" name="operation" /><button class="btn btn-danger fas fa-times-circle" ></button>' +
                        '</form></td></tr></tbody>');
                    });
                }
            });
        });
    }

    function doAdd() {
        return new Promise((resolve, reject) => {
            var serial = document.getElementById("serialno_medidores0").value;
            var nome = document.getElementById("nome0").value;
            var tabela = document.getElementById("tabela0").value;
            $.post({
                url: "/mavenproject2/requestcontroller",
                data: { 
                    "serialno_medidores0": serial,
                    "nome0": nome,
                    "tabela0": tabela,
                    "botaoSubmit": "enviar"
                },
                success: function(data) {
                    var d = JSON.parse(data);
                    if (!d["success"]) { return; }
                    var i = $("#tabelaMedidores").children().length;
                    $('#tabelaMedidores').append("<tbody><tr><td contenteditable='true' id='"
                                                 + i + "_nome_editavel'>" + serial
                                                 + "</td><td contenteditable='true' id='" + i + "_nome_editavel'>" +  nome +
                    "</td><td>" + tabela + "</td><td>" + '<form >' +
                    '<input type="hidden" value="' + serial + " id=" + i + '_serial" name="serialno_medidores" />' +
                    '<input type="hidden" value="' + nome + ' id="' + i + '_nome" name="nome" />' +
                    '<input type="hidden" value="' + tabela + '" id="' + i + '" name="tabela" />' +
                    '<input type="hidden" value="edit" name="operation" />' + '<button class="btn btn-success fas fa-check-circle"></button>' +
                    '<script>' +
                        'document.getElementById("' + i + '_serial_editavel").addEventListener("keyup", function() {' +
                            'document.getElementById("' + i + '_serial").value = document.getElementById("' +
                            i + '_serial_editavel").innerHTML;});' +
                        'document.getElementById("' + i + '_nome_editavel").addEventListener("keyup", function() {' +
                            'document.getElementById("' + i + '_nome").value = document.getElementById("' + i + '_nome_editavel").innerHTML;});' +
                    '</script></form></td><td><form >' +
                    '<input type="hidden" value="' + tabela + '" name="tabela" />' +
                    '<input type="hidden" value="delete" name="operation" /><button class="btn btn-danger fas fa-times-circle" ></button>' +
                    '</form></td></tr></tbody>');
                }
            });
        });
    }

    function doEdit() {
        return new Promise((resolve, reject) => {
            var serial = document.getElementById("serialno_medidores0").value;
            var nome = document.getElementById("nome0").value;
            var tabela = document.getElementById("tabela0").value;
            $.post({
                url: "/mavenproject2/requestcontroller",
                data: { 
                    "serialno_medidores0": serial,
                    "nome0": nome,
                    "tabela0": tabela,
                    "botaoSubmit": "enviar"
                },
                success: function(data) {
                    var d = JSON.parse(data);
                    if (!d["success"]) { return; }
                    var i = $("#tabelaMedidores").children().length;
                    $('#tabelaMedidores').append("<tbody><tr><td>" + serial + "</td><td>" +  nome +
                    "</td><td>" + tabela + "</td><td>" + '<form >' +
                    '<input type="hidden" value="' + serial + '" id="' + i + '_serial" name="serialno_medidores" />' +
                    '<input type="hidden" value="' + nome + '" id="' + i + '_nome" name="nome" />' +
                    '<input type="hidden" value="' + tabela + '" id="' + i + '" name="tabela" />' +
                    '<input type="hidden" value="edit" name="operation" />' + '<button class="btn btn-success fas fa-check-circle"></button>' +
                    '<script>' +
                        'document.getElementById("' + i + '_serial_editavel").addEventListener("keyup", function() {' +
                            'document.getElementById("' + i + '_serial").value = document.getElementById("' +
                            i + '_serial_editavel").innerHTML;});' +
                        'document.getElementById("' + i + '_nome_editavel").addEventListener("keyup", function() {' +
                            'document.getElementById("' + i + '_nome").value = document.getElementById("' + i + '_nome_editavel").innerHTML;});' +
                    '</script></form></td><td><form >' +
                    '<input type="hidden" value="' + tabela + '" name="tabela" />' +
                    '<input type="hidden" value="delete" name="operation" /><button class="btn btn-danger fas fa-times-circle" ></button>' +
                    '</form></td></tr></tbody>');
                }
            });
        });
    }

    function doDelete() {
        return new Promise((resolve, reject) => {
            var tabela = document.getElementById("tabela2").value;
            $.post({
                url: "/mavenproject2/requestcontroller",
                data: { 
                    "tabela2": tabela,
                    "operation": "delete"
                },
                success: function(data) {
                    $('#tabelaMedidores tbody').remove();
                    $.each(data, function(i, data) {
                        $('#tabelaMedidores').append("<tbody><tr><td contenteditable='true' id='" + i + "_nome_editavel'>" + data["serialno_medidores"] + "</td><td contenteditable='true' id='" + i + "_nome_editavel'>" +  data["nome"] +
                        "</td><td>" + data["tabela"] + "</td><td>" + '<form >' +
                        '<input type="hidden" value="' + data["serialno_medidores"] + " id=" + i + '_serial" name="serialno_medidores" />' +
                        '<input type="hidden" value="' + data["nome"] + ' id="' + i + '_nome" name="nome" />' +
                        '<input type="hidden" value="' + data["tabela"] + '" id="' + i + '" name="tabela" />' +
                        '<input type="hidden" value="edit" name="operation" />' + '<button class="btn btn-success fas fa-check-circle"></button>' +
                        '<script>' +
                            'document.getElementById("' + i + '_serial_editavel").addEventListener("keyup", function() {' +
                                'document.getElementById("' + i + '_serial").value = document.getElementById("' +
                                i + '_serial_editavel").innerHTML;});' +
                            'document.getElementById("' + i + '_nome_editavel").addEventListener("keyup", function() {' +
                                'document.getElementById("' + i + '_nome").value = document.getElementById("' + i + '_nome_editavel").innerHTML;});' +
                        '</script></form></td><td><form >' +
                        '<input type="hidden" value="' + data["tabela"] + '" name="tabela" />' +
                        '<input type="hidden" value="delete" name="operation" /><button class="btn btn-danger fas fa-times-circle" ></button>' +
                        '</form></td></tr></tbody>');
                    });
                }
            });
        });
    }

    // websocket API
    let socket = new WebSocket("ws://localhost:8080/mavenproject2/endpoint");

    socket.onopen = function(e) {
        socket.send('{"medidores": "medidores"}');
    };

    socket.onmessage = function(event) {
        var d = JSON.parse(event.data);
        try {
            $('#tabelaMedidores tbody').remove();
            for (var i = 0; i < d.length; i++) {
                $('#tabelaMedidores').append("<tbody><tr><td contenteditable='true' id='"
                                             + i + "_nome_editavel'>"
                                             + d[i]["serialno_medidores"]
                                             + "</td><td contenteditable='true' id='"
                                             + i + "_nome_editavel'>" +  d[i]["nome"]
                                             + "</td><td>" + d[i]["tabela"]
                                             + "</td><td>" + '<form >'
                                             + '<input type="hidden" value="'
                                             + d[i]["serialno_medidores"]
                                             + " id=" + i
                                             + '_serial" name="serialno_medidores" />'
                                             + '<input type="hidden" value="'
                                             + d[i]["nome"] + ' id="' + i
                                             + '_nome" name="nome" />'
                                             + '<input type="hidden" value="'
                                             + d[i]["tabela"] + '" id="'
                                             + i + '" name="tabela" />'
                                             + '<input type="hidden" value="edit" name="operation" />'
                                             + '<button class="btn btn-success fas fa-check-circle"></button>'
                                             + '<script>' + 'document.getElementById("'
                                             + i + '_serial_editavel").addEventListener("keyup", function() {'
                                             + 'document.getElementById("' + i
                                             + '_serial").value = document.getElementById("'
                                             + i + '_serial_editavel").innerHTML;});'
                                             + 'document.getElementById("' + i
                                             + '_nome_editavel").addEventListener("keyup", function() {'
                                             + 'document.getElementById("' + i
                                             + '_nome").value = document.getElementById("'
                                             + i + '_nome_editavel").innerHTML;});'
                                             + '</script></form></td><td><form >'
                                             + '<input type="hidden" value="'
                                             + d[i]["tabela"] + '" name="tabela" />'
                                             + '<input type="hidden" value="delete" name="operation" />'
                                             + '<button class="btn btn-danger fas fa-times-circle" ></button>'
                                             + '</form></td></tr></tbody>');
            }
        } catch (error) {
            console.log(error);
        }
    };

    socket.onclose = function(event) {
    if (event.wasClean) {
        console.log("Conexão fechada.");
        
    } else {
        console.log("Conexão interrompida.");
        
    }
    };

    socket.onerror = function(error) {
        console.log(error);
        
    };


    // doGet();
    $("#botaoSubmit").click(function (e) { doAdd(); })
    $(".fa-check-circle").click(function(e) { doEdit(); })
    $(".btn-danger").click(function(e) { doDelete(); })

});