package simulador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flanagan.complex.Complex;
import flanagan.math.FourierTransform;
import javax.json.JsonArrayBuilder;

public class InputServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static float teta = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        float delta = 0.1F;
        teta += delta;
        String json = request.getParameter("json");
        // Pegar o array json
        JsonReader jsonReader = Json.createReader(new StringReader(json));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        JsonArray jsonArray = jsonObject.getJsonArray("correntes");
        // Criar um array de doubles
        double[] vetorDouble = new double[4096];
        for(int k=0;k<4096;k++)vetorDouble[k]=0D;
        
        String[] vetorStr = new String[4096];
        Iterator<JsonValue> it = jsonArray.iterator();
        int i = 0;
        while(it.hasNext()){
            vetorStr[i] = it.next().toString().replace('"', ' ').trim();
            vetorDouble[i] = new Double(vetorStr[i]);
//            System.out.println(vetor[i]);
            i++;
        }
//System.out.println("Recebeu request =================\n");
//        double[] vetorAmostr = new double[600];
        String[] dadosAmostr = new String[600];
        for(int j=0;j<600;j++){
//            for(int m=0;m<2;m++){
//                dadosAmostr[2*j+m] = vetorStr[j];
                dadosAmostr[j] = vetorStr[j];
//                vetorAmostr[4*j+m] = vetor[j];
//System.out.println(vetorStr[j]);
//            }
        }
//System.out.println("----------------");
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
        for (int k=0;k<600;k++) {
//System.out.println(k+". "+dadosAmostr[k]);
            jsonArrayBuilder.add(dadosAmostr[k]);
        }

        
        double[][] componentes = calcularFFT(vetorDouble);
        
        JsonArrayBuilder jsonArrayBuilder2 = Json.createArrayBuilder();
        for (int kk=0;kk<600;kk++) {
//System.out.println(kk+". "+componentes[1][kk]);
            jsonArrayBuilder2.add(8D*componentes[1][kk]-1D);
        }
        
        jsonObject = Json.createObjectBuilder()
                            .add("correntes", jsonArrayBuilder.build())
                
                            .add("magnitudeEspectro", jsonArrayBuilder2.build())
                
                            .add("fundamental_mag",4D*componentes[1][20])
                            .add("fundamental_ang",8000D*componentes[3][20])
                
                            .add("terceira_mag",4D*componentes[1][61])
                            .add("terceira_ang",8000D*componentes[3][61])
                
                            .add("quinta_mag",4D*componentes[1][101])
                            .add("quinta_ang",8000D*componentes[3][101])
                            .build();
//System.out.println(jsonObject.toString());
        simulador.graficos.session.getBasicRemote().sendText(jsonObject.toString());

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("OK");
    }

    private double[][] calcularFFT(double[] ydata){
        int NRO_PONTOS_ABCISSA = 4096;
        double[][] data;
        // long inicio = System.currentTimeMillis();
        FourierTransform fft = new FourierTransform(ydata);
        fft.setHann();  //--- COMO AFETA O FATOR DE ESCALA?
//        ft1.setDeltaT(deltaT);  //--- O QUE ISTO?
//        ft1.setDeltaT(periodoAmostragem);
        fft.transform();
        Complex[] fftData = fft.getTransformedDataAsComplex();
            data = new double[4][NRO_PONTOS_ABCISSA];
            for (int i = 0; i < NRO_PONTOS_ABCISSA; i++) {
                data[0][i] = i;
                data[1][i] = 1*fftData[i].abs() / NRO_PONTOS_ABCISSA;  //--- FATOR DE ESCALA! DEPENDE DE QUE? COMO CALCULAR?
                data[2][i] = i;
                data[3][i] = 1*fftData[i].arg() / NRO_PONTOS_ABCISSA;   //  Deg() / 180;       //--- FATOR DE ESCALA! DEPENDE DE QUE? COMO CALCULAR?
                
//                System.out.println(data[0][i]+" - "+data[1][i]);
            }
            
            
        int N_FUND = 32;  //Constantes.NRO_DE_CICLOS_UTEIS;
        int N_3HARM = 3 * N_FUND;
        int N_5HARM = 5 * N_FUND;
        double[] mag = new double[3];
        double[] fase = new double[3];
        mag[0] = 2 * fftData[N_FUND].abs()/4096;
        mag[1] = 2 * fftData[N_3HARM].abs()/4096;
        mag[2] = 2 * fftData[N_5HARM].abs()/4096;
//System.out.println("=== Magnitudes: "+mag[0]+" , "+mag[1]+" , "+mag[2]);        
        fase[0] = fftData[N_FUND].arg();
        fase[1] = fftData[N_3HARM].arg();
        fase[2] = fftData[N_5HARM].arg();
            
/*
            for (int i = 0; i < NRO_PONTOS_ABCISSA; i++) {
                jTextArea1.append("    " + (new Double(c.getValor(i))).toString() +"        "+ data[1][i]+"        "+ data[3][i]+ "\n");
            }
*/
/*        
        PlotGraph pg = new PlotGraph(data);
        pg.setGraphTitle("FFT de uma corrente");
        pg.setXaxisLegend("Frequency");
        pg.setXaxisUnitsName("Hz");
        pg.setYaxisLegend("Amplitude");
        pg.setYaxisUnitsName("v/Hz");
        pg.setLine(3);
        pg.plot();
*/            
            
            
            
        // long tempo = System.currentTimeMillis() - inicio;
//System.out.println("----- Tempo para 1 iteração: " + tempo + " ms");
        return data;
    }
    
    // private String[] buildTensoes() {
    //     String[] vStr = new String[600];
    //     double[] vDouble = new double[600];
    //     double x = 0D;
    //     double deltax = 1D; //2*Math.PI;
    //     int k = 1000;
    //     double eps = 0.015D;
    //     for (int i = 0; i < 600; i++) {
    //         x += deltax;
    //         vDouble[i] = Math.sin((2D * Math.PI / 600) * x);
    //         if (Math.abs(vDouble[i]) < eps) {
    //             k = 0;
    //         }
    //         if (k < 70) {
    //             k++;
    //             vDouble[i] = 0D;
    //         }
    //         vStr[i] = Double.toString(vDouble[i]);
    //     }
    //     return vStr;
    // }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
