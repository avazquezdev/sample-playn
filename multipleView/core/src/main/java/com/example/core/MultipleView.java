package com.example.core;

import playn.core.Canvas;
import playn.core.Font;
import playn.core.Image;
import playn.core.Platform;
import playn.core.TextBlock;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.TextWrap;
import playn.core.Texture;
import playn.scene.ImageLayer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import playn.scene.Pointer;


public class MultipleView extends SceneGame {

  public final TextFormat BUTTON_FMT;
  public final TextFormat TEXT_FMT;
  public final Pointer pointer;
  private Font font;
  private TextFormat format;
  private ButtonListener btListener;
  private Platform platform;

  public MultipleView (Platform plat) {
    super(plat, 33); // update our "simulation" 33ms (30 times per second)
    platform = plat;
    // create and add background image layer
    Image bgImage = plat.assets().getImage("images/bg.png");
    ImageLayer bgLayer = new ImageLayer(bgImage);
    // scale the background to fill the screen
    bgLayer.setSize(plat.graphics().viewSize);

    BUTTON_FMT = new TextFormat(new Font("Droid Sans Fallback", 24));
    TEXT_FMT = new TextFormat(new Font("Droid Sans Fallback", 12));
    pointer = new Pointer(plat, rootLayer, true);
    ImageLayer playBtn = createButton("Play", new Runnable() {
      @Override
      public void run() {
        platform.log().info("click");
        btListener.click();
      }
    });
    font = new Font("Droid Sans Fallback", 21);
    format = new TextFormat(font, true);

    rootLayer.add(bgLayer);
    rootLayer.addAt(playBtn, 10, 30);

  }
  public Texture formatText (TextFormat format, String text, boolean border) {
    TextLayout layout = plat.graphics().layoutText(text, format);
    float margin = border ? 10 : 0;
    float width = layout.size.width()+2*margin, height = layout.size.height()+2*margin;
    Canvas canvas = plat.graphics().createCanvas(width, height);
    if (border) canvas.setFillColor(0xFFCCCCCC).fillRect(0, 0, canvas.width, canvas.height);
    canvas.setFillColor(0xFF000000).fillText(layout, margin, margin);
    if (border) canvas.setStrokeColor(0xFF000000).strokeRect(0, 0, width-1, height-1);
    return canvas.toTexture();
  }

  public Texture formatText (String text, boolean border) {
    return formatText(TEXT_FMT, text, border);
  }

  public Texture wrapText(String text, float width, TextBlock.Align align) {
    TextLayout[] layouts = plat.graphics().layoutText(text, TEXT_FMT, new TextWrap(width));
    Canvas canvas = new TextBlock(layouts).toCanvas(plat.graphics(), align, 0xFF000000);
    return canvas.toTexture();
  }

  public Texture formatButton (String label) {
    return formatText(BUTTON_FMT, label, true);
  }

  public ImageLayer createButton (String label, final Runnable onClick) {
    ImageLayer layer = new ImageLayer(formatButton(label));
    layer.events().connect(new playn.scene.Pointer.Listener() {
      @Override public void onStart (Pointer.Interaction iact) { onClick.run(); }
    });
    return layer;
  }
  public void setListener(ButtonListener btListener) {
    this.btListener = btListener;
  }
}
