package mvc.modelo;

public class ConversorDeCambio {
    public float converterReaisEmDolares(float v){
        return v * 0.48F;
    }
    
    public double converterReaisEmOutraMoeda(String moeda, double v){
        switch(moeda){
            case "BRL":{
                return v;
            }
            case "USD":{
                return v * 0.33D;
            }
            case "EUR":{
                return v * 0.31D;
            }
            case "YEN":{
                return v * 39.23D;
            }
            default: return 0D;
        }
    }
}
