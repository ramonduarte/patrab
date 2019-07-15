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

      
      function doThis() {
        return new Promise((resolve, reject) => {
            $.post({
                url: "/mavenproject2/Ajax",
                data: { "medidores": "medidores" },
                success: function(data) {
                    if ($('#selectMedida')) {
                        $('#selectMedida').empty();
                        $.each(data, function(i, data) {
                            $('#selectMedida').append('<option value="' + data["tabela"] + '">' + data["nome"] + '</option>');
                        });
                    } else {
                        $('#tabelaMedidores tbody').remove();
                        $.each(data, function(i, data) {
                            $('#tabelaMedidores').append("<tbody><tr><td>" + data["serialno_medidores"] + "</td><td>" +  data["nome"] +
                            "</td><td>" + data["tabela"] + "</td><td>" + '<form action="/mavenproject2/requestcontroller" method="post">' +
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
                                '</script></form></td><td><form action="/mavenproject2/requestcontroller" method="post">' +
                                '<input type="hidden" value="' + data["tabela"] + '" name="tabela" />' +
                                '<input type="hidden" value="delete" name="operation" /><button class="btn btn-danger fas fa-times-circle" ></button>' +
                                '</form></td></tr></tbody>');

                            });
                        }
                    }
                });
            });
        }
        
        function doThat() {
            return new Promise((resolve, reject) => {
                // e.preventDefault();
                var medidor = $("#selectMedida").children("option:selected")[0].value;
            var periodo = $("#selectPeriodo").children("option:selected")[0].value;
            var datafinal = $("#start")[0].value;
            // var tabela = $("#selectTabela")[0].value;
            $.post({
                url: "/mavenproject2/Ajax",
                data: {
                    "medidor": medidor,
                    "periodo": periodo,
                    "datafinal": datafinal,
                    "medidores": "others",
                    // "tabela": tabela
                },
                success: function(data){
                    window.trace1.x = [];
                    window.trace1.y = [];
                    window.trace2.x = [];
                    window.trace2.y = [];

                    $('#tabelaMedidas tbody').remove();
                    $.each(data, function(i, data) {
                        $('#tabelaMedidas').append("<tbody><tr><td>" + data["medidor"] + "</td><td>" +  + data["temperatura"] + "</td><td>" + data["umidade"] + "</td><td>" + data["datahora"] + "</td><td>" + data["serial"] + "</td></tr></tbody>");

                        window.trace1.x.push(Date.parse(data["datahora"]));
                        window.trace2.x.push(Date.parse(data["datahora"]));
                        window.trace1.y.push(parseInt(data["temperatura"]));
                        window.trace2.y.push(parseInt(data["umidade"]));
                    });
                    Plotly.newPlot('plotly', dataPlotly);

                }
            });
        });
    }
    
    doThis();
    $("#botaoLer").click(function (e) { doThat(); });
    $("#selectTabela").click(function (e) {
        if (document.getElementById("selectTabela").checked) {
            $("#tabelaMedidas").hide();
            $("#plotly").show();
        } else {
            $("#tabelaMedidas").show();
            $("#plotly").hide();

        }
        doThat(); 
    });

      var dataPlotly = [window.trace1, window.trace2];
      console.log(dataPlotly);
      
      Plotly.newPlot('plotly', dataPlotly);
      
});