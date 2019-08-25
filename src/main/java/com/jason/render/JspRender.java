package com.jason.render;

import com.jason.Const;
import com.jason.Mario;

import java.io.Writer;

/**
 * author: zhangkai
 * date: 2019-08-24
 * description:
 */
public class JspRender implements Render {
    @Override
    public void render(String view, Writer writer) {
        String viewPath = this.getViewPath(view);

    }

    private String getViewPath(String view) {
        Mario mario = Mario.me();
        String viewPrfix = mario.getConf(Const.VIEW_PREFIX_FIELD);
        String viewSuffix = mario.getConf(Const.VIEW_SUFFIX_FIELD);

        if (viewPrfix == null || viewPrfix.equals("")) {
            viewPrfix = Const.VIEW_PREFIX;
        }
        if (viewSuffix == null || viewSuffix.equals("")) {
            viewSuffix = Const.VIEW_SUFFIX;
        }
        String viewPath = viewPrfix + "/" + view;
        if (!view.endsWith(viewSuffix)) {
            viewPath += viewSuffix;
        }
        return viewPath.replaceAll("[/]+", "/");
    }
}
