package mvc.modelo;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FormatadorDeMoedas {

    public String formatar(String moeda, double valor) {
        DecimalFormatSymbols simbolosVirgula = new DecimalFormatSymbols(new Locale("pt", "BR"));
        simbolosVirgula.setDecimalSeparator(',');
        simbolosVirgula.setGroupingSeparator('.');
        DecimalFormat formatoVirgula = new DecimalFormat("0.00", simbolosVirgula);

        DecimalFormatSymbols simbolosPonto = new DecimalFormatSymbols(new Locale("pt", "BR"));
        simbolosPonto.setDecimalSeparator('.');
        simbolosPonto.setGroupingSeparator(',');
        DecimalFormat formatoPonto = new DecimalFormat("0.00", simbolosPonto);

        switch (moeda) {
            case "BRL": {
                return formatoVirgula.format(valor);
            }
            case "USD": {
                return formatoPonto.format(valor);
            }
            case "EUR": {
                return formatoVirgula.format(valor);
            }
            case "GBP": {
                return formatoPonto.format(valor);
            }
            case "YEN": {
                return formatoPonto.format(valor);
            }
            default:
                return formatoPonto.format(valor);
        }
    }
}
