var $ = jQuery;

var trace1 = {
    x: [],
    y: [],
    type: 'scatter',
    name: "temperatura (º C)"
  };
  
  var trace2 = {
    x: [],
    y: [],
    type: 'scatter',
    name: "umidade (%)"
  };



$( document ).ready(function() {
    document.getElementById('start').valueAsDate = new Date();
    // FIXME: this function 2019-07-18 22:19:44
    //   function doGet() {
    //     return new Promise((resolve, reject) => {
    //         $.post({
    //             url: "/mavenproject2/Ajax",
    //             data: { "medidores": "medidores" },
    //             success: function(data) {
    //                 if ($('#selectMedida')) {
    //                     $('#selectMedida').empty();
    //                     $.each(data, function(i, data) {
    //                         $('#selectMedida').append('<option value="' + data["tabela"] + '">' + data["nome"] + '</option>');
    //                     });
    //                 } else {
    //                     $('#tabelaMedidores tbody').remove();
    //                     $.each(data, function(i, data) {
    //                         $('#tabelaMedidores').append("<tbody><tr><td>"
    //                                                      + data["serialno_medidores"]
    //                                                      + "</td><td>" +  data["nome"]
    //                                                      + "</td><td>" + data["tabela"]
    //                                                      + "</td><td>"
    //                                                      + '<form action="/mavenproject2/requestcontroller" method="post">'
    //                                                      + '<input type="hidden" value="'
    //                                                      + data["serialno_medidores"]
    //                                                      + " id=" + i + '_serial" name="serialno_medidores" />'
    //                                                      + '<input type="hidden" value="'
    //                                                      + data["nome"] + ' id="'
    //                                                      + i + '_nome" name="nome" />'
    //                                                      + '<input type="hidden" value="'
    //                                                      + data["tabela"] + '" id="'
    //                                                      + i + '" name="tabela" />'
    //                                                      + '<input type="hidden" value="edit" name="operation" />'
    //                                                      + '<button class="btn btn-success fas fa-check-circle"></button>'
    //                                                      + '<script>'
    //                                                      + 'document.getElementById("'
    //                                                      + i + '_serial_editavel").addEventListener("keyup", function() {'
    //                                                      + 'document.getElementById("'
    //                                                      + i + '_serial").value = document.getElementById("'
    //                                                      + i + '_serial_editavel").innerHTML;});'
    //                                                      + 'document.getElementById("'
    //                                                      + i + '_nome_editavel").addEventListener("keyup", function() {'
    //                                                      + 'document.getElementById("' + i
    //                                                      + '_nome").value = document.getElementById("'
    //                                                      + i + '_nome_editavel").innerHTML;});'
    //                                                      + '</script></form></td><td>'
    //                                                      + '<form action="/mavenproject2/requestcontroller" method="post">'
    //                                                      + '<input type="hidden" value="'
    //                                                      + data["tabela"] + '" name="tabela" />'
    //                                                      + '<input type="hidden" value="delete" name="operation" />'
    //                                                      + '<button class="btn btn-danger fas fa-times-circle" ></button>'
    //                                                      + '</form></td></tr></tbody>');
    //                         });
    //                     }
    //                 }
    //             });
    //         });
    //     }
        
        function doEdit() {
            return new Promise((resolve, reject) => {
                var medidor = $("#selectMedida").children("option:selected")[0].value;
                var periodo = $("#selectPeriodo").children("option:selected")[0].value;
                var datafinal = $("#start")[0].value;
                $.post({
                    url: "/mavenproject2/Ajax",
                    data: {
                        "medidor": medidor,
                        "periodo": periodo,
                        "datafinal": datafinal,
                        "medidores": "others",
                    },
                    success: function(data){
                        window.trace1.x = [];
                        window.trace1.y = [];
                        window.trace2.x = [];
                        window.trace2.y = [];

                        $('#tabelaMedidas tbody').remove();
                        $.each(data, function(i, data) {
                            $('#tabelaMedidas').append("<tbody><tr><td>"
                                                    + data["medidor"]
                                                    + "</td><td>"
                                                    + data["temperatura"]
                                                    + "</td><td>"
                                                    + data["umidade"]
                                                    + "</td><td>"
                                                    + data["datahora"]
                                                    + "</td><td>"
                                                    + data["serial"]
                                                    + "</td></tr></tbody>");
                        });

                        data.sort(function(a, b){ 
                            return new Date(a["datahora"]) - new Date(b["datahora"]);
                        });
                        
                        $.each(data, function(i, data) {
                            window.trace1.x.push(data["datahora"].slice(0, 19));
                            window.trace2.x.push(data["datahora"].slice(0, 19));
                            window.trace1.y.push(parseInt(data["umidade"]));
                            window.trace2.y.push(parseInt(data["temperatura"]));
                        });
                        Plotly.newPlot('plotly', dataGraph);
                    }
                });
            });
        }

    // websocket API
    let socket = new WebSocket("ws://localhost:8080/mavenproject2/endpoint");
    socket.onopen = function(e) {
        socket.send('{"medidores": "others", "medidor": "medidor001", "periodo": "z"}');
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
    

    socket.onmessage = function(event) {
        if (!$("#selectWs")[0].checked) { return false; }
        var d = JSON.parse(event.data);
        try {
            window.trace1.x = [];
            window.trace1.y = [];
            window.trace2.x = [];
            window.trace2.y = [];

            $('#tabelaMedidas tbody').remove();
            for (var i = 0; i < d.length; i++) {
                $('#tabelaMedidas').append("<tbody><tr><td>"
                                            + d[i]["medidor"]
                                            + "</td><td>"
                                            + d[i]["temperatura"]
                                            + "</td><td>"
                                            + d[i]["umidade"]
                                            + "</td><td>"
                                            + d[i]["datahora"]
                                            + "</td><td>"
                                            + d[i]["serial"]
                                            + "</td></tr></tbody>");
            }
            d.sort(function(a, b){ 
                return new Date(a["datahora"]) - new Date(b["datahora"]);
            });

            for (var i = 0; i < d.length; i++) {
                window.trace1.x.push(d[i]["datahora"].slice(0, 19));
                window.trace2.x.push(d[i]["datahora"].slice(0, 19));
                window.trace1.y.push(parseInt(d[i]["umidade"]));
                window.trace2.y.push(parseInt(d[i]["temperatura"]));
            }
            Plotly.newPlot('plotly', dataGraph);
        } catch (error) {
            console.log(error);
        }
    };

    // doGet();
    $("#botaoLer").click(function (e) { 
        if ($("#selectWs")[0].checked) {
            socket.send('{"medidores": "others", "medidor": "medidor001", "periodo": "z"}');
        } else {
            doEdit(); 
        }
    });
    // $("#selectWs").click(function (e) { 
    //     socket.send('{"medidores": "others"}');
    //  });
    $("#selectWs").change(function (e) { 
        socket.send('{"medidores": "others", "medidor": "medidor001", "periodo": "z"}');
        });
    $("#selectTabela").click(function (e) {
        if (document.getElementById("selectTabela").checked) {
            $("#tabelaMedidas").hide();
            $("#plotly").show();
        } else {
            $("#tabelaMedidas").show();
            $("#plotly").hide();
        }
        doEdit(); 
    });
    $("#selectMedida, #selectPeriodo, #start").change(function (e) {
        if (!$("#selectWs")[0].checked) { return false; }
        socket.send('{"medidores": "others", "medidor": "'
                    + $("#selectMedida")[0].value
                    + '", "periodo": "'
                    + $("#selectPeriodo")[0].value
                    + '", "datafinal": "'
                    + $("#start")[0].value + '"}');
    });

      var dataGraph = [window.trace1, window.trace2];
      Plotly.newPlot('plotly', dataGraph);
});