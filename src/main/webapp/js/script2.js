var $ = jQuery;

$( document ).ready(function() {
    function doThis() {
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
                    $('#tabelaMedidores').append("<tbody><tr><td contenteditable='true' id='" + i + "_nome_editavel'>" + serial + "</td><td contenteditable='true' id='" + i + "_nome_editavel'>" +  nome +
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


    doThis();
    $("#botaoSubmit").click(function (e) { doAdd(); })
    $(".fa-check-circle").click(function(e) { doEdit(); })
    $(".btn-danger").click(function(e) { doDelete(); })

});