package simulador;

import java.io.Serializable;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

public class AquisicaoDTO implements Serializable{
    private String[] correntes;

    public String[] getCorrentes() {
        return correntes;
    }

    public void setCorrentes(String[] corrente) {
        this.correntes = corrente;
    }
    
    JsonObject objetoJSON;
    public JsonObject toJSON(){
        JsonArrayBuilder jsonArrayBuilderCorrentes = Json.createArrayBuilder();
        for (String corrente : correntes) {
            jsonArrayBuilderCorrentes.add(corrente);
        }

        objetoJSON = Json.createObjectBuilder()
                .add("correntes",jsonArrayBuilderCorrentes.build())
                .build();
        
        return objetoJSON;
    }
    
    @Override
    public String toString(){
        return toJSON().toString();
    }
}
