package com.example.java;

import playn.java.LWJGLPlatform;

import com.example.core.MultipleView;

public class MultipleViewJava {

  public static void main (String[] args) {
    LWJGLPlatform.Config config = new LWJGLPlatform.Config();
    // use config to customize the Java platform, if needed
    LWJGLPlatform plat = new LWJGLPlatform(config);
    new MultipleView(plat);
    plat.start();
  }
}
