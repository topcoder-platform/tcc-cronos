package com.topcoder.web.ball.tag;

import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.common.CachedDataAccess;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Map;

public class HandleTag extends TagSupport {
    private static final Logger log = Logger.getLogger(HandleTag.class);
    private long userId = 0;
    private String cssclass = "cDef";

    public void setId(long userId) {
        this.userId = userId;
    }

    public void setStyleClass(String cssclass) {
        this.cssclass = cssclass;
    }

    public int doStartTag() throws JspException {
        try {
            StringBuffer output = new StringBuffer();
            CachedDataAccess da = new CachedDataAccess("orpheus/MSSQLDS");
            
            Request r = new Request();
            r.setContentHandle("user_handle");
            r.setProperty("uid", String.valueOf(userId));

            Map m = da.getData(r);

            ResultSetContainer rsc = (ResultSetContainer) m.get("user_handle");
            if (rsc.isEmpty()) {
                output.append("UNKNOWN USER");
            } else {
                output.append("<span class=\"");
                if (rsc.getIntItem(0, "is_admin") == 1) {
                    output.append("cOrange");
                } else {
                    output.append(cssclass);
                }
                output.append("\">");
                output.append(rsc.getStringItem(0, "handle"));
                output.append("</span>");
            }
            pageContext.getOut().print(output.toString());
        } catch (Exception e) {
            log.error("on user id " + userId);
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    /**
     * Because the app server (JBoss) is caching the tags,
     * we have to clear out all the instance variables at the
     * end of execution.
     */
    public int doEndTag() throws JspException {
        userId = 0;
        cssclass = "cDef";
        return super.doEndTag();
    }


}