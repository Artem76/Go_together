package app.entityXML.Response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Олег on 25.08.2017.
 */
@XmlRootElement(name = "Response")
public class Response {
    private String status;

    private String parameter;

    public Response() {

    }

    public Response(String status, String parameter) {
        this.status = status;
        this.parameter = parameter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
