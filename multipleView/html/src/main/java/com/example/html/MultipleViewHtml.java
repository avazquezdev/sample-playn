package com.example.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import com.example.core.MultipleView;

public class MultipleViewHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("multipleView/");
    new MultipleView(plat);
    plat.start();
  }
}
