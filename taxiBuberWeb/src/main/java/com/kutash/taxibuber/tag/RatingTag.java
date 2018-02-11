package com.kutash.taxibuber.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The type Rating tag.
 */
@SuppressWarnings("serial")
public class RatingTag extends TagSupport {

    private int mark;

    /**
     * Sets mark.
     *
     * @param mark the mark
     */
    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public int doStartTag() throws JspException {
        try{
            String to = String.valueOf(mark*20);
            pageContext.getOut().write(to);
        } catch(IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
}
