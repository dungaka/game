package com.wyd.exchange.servlet.exchange;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.wyd.exchange.server.factory.ServiceManager;
import com.wyd.exchange.utils.CryptionUtil;

/**
 * 兑换码发放
 * @author zgq
 */
public class ExtendServlet extends HttpServlet {
    private static final long serialVersionUID = 1911747458628093909L;

    // /private static final String CONTENT_TYPE = "text/html";
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ret = "success";
        try {
            byte[] data = CryptionUtil.inputStream2byte(req.getInputStream());
            String extendIds = new String(CryptionUtil.Decrypt(data, ServiceManager.getManager().getConfiguration().getString("deckey")), "utf-8");
            ServiceManager.getManager().getExchangeService().extend(extendIds);
        } catch (Exception e) {
            e.printStackTrace();
            ret = "fail";
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
        ServletOutputStream out = resp.getOutputStream();
        OutputStreamWriter os = new OutputStreamWriter(out, "utf-8");
        os.write(ret);
        os.flush();
        os.close();
    }
}
