package mvc;

/**
 * Message
 */
public class Message {
    private String from;
    private String to;
    private String content;
    private String medidores;

    /**
     * @return the from
     */
    public String getFrom() {
        return from;
    }
    /**
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }
    /**
     * @return the to
     */
    public String getTo() {
        return to;
    }
    /**
     * @param to the to to set
     */
    public void setTo(String to) {
        this.to = to;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return the medidores
     */
    public String getMedidores() {
        return medidores;
    }
    /**
     * @param medidores the medidores to set
     */
    public void setMedidores(String medidores) {
        this.medidores = medidores;
    }
}
