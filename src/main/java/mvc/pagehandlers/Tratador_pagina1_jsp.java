package mvc.pagehandlers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.modelo.ConversorDeCambio;
import mvc.IFTratadorDePaginas;
import mvc.modelo.ParMoedaValor;

public class Tratador_pagina1_jsp implements IFTratadorDePaginas{
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
        String jspURL = "pagina2.jsp";

        String valorOriginal = request.getParameter("VALOR_EM_REAIS");
        String valor = valorOriginal;
        if(valor==null || valor.isEmpty()){
            valor = "0";
        }
        Locale brasil = new Locale("pt", "BR");
        NumberFormat format = NumberFormat.getInstance(brasil);
        double numerario = 0F;
        try{
            numerario = format.parse(valor).doubleValue();
        }catch(Exception e){
            request.setAttribute("MENSAGEM_DE_ERRO","Erro de formato...");
            request.setAttribute("VALOR_EM_REAIS",valorOriginal);
            jspURL = "pagina1.jsp";
            System.out.println("==== "+e);
            numerario = 0F;
            return jspURL;
        }
        
        ConversorDeCambio conv = new ConversorDeCambio();
        
        //--- preparação da resposta
        ArrayList<ParMoedaValor> listaDeValoresConvertidos = new ArrayList();
        ParMoedaValor  par = new ParMoedaValor();
        
        par = new ParMoedaValor();
        par.setMoeda("BRL");
        par.setValor(conv.converterReaisEmOutraMoeda("BRL", numerario));
        listaDeValoresConvertidos.add(par);
        
        par = new ParMoedaValor();
        par.setMoeda("USD");
        par.setValor(conv.converterReaisEmOutraMoeda("USD", numerario));
        listaDeValoresConvertidos.add(par);
        
        par = new ParMoedaValor();
        par.setMoeda("EUR");
        par.setValor(conv.converterReaisEmOutraMoeda("EUR", numerario));
        listaDeValoresConvertidos.add(par);
        
        par = new ParMoedaValor();
        par.setMoeda("JPY");
        par.setValor(conv.converterReaisEmOutraMoeda("YEN", numerario));
        listaDeValoresConvertidos.add(par);
        
        request.setAttribute("LISTA_DE_RECORDS",listaDeValoresConvertidos);

        return jspURL;
    }
}
