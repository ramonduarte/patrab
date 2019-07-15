package mvc.pagehandlers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mvc.IFTratadorDePaginas;

public class Tratador_pagina2_jsp implements IFTratadorDePaginas{
    @Override
    public String processar(HttpServletRequest request, HttpServletResponse response){
        
        return "pagina1.jsp";
    }
}
