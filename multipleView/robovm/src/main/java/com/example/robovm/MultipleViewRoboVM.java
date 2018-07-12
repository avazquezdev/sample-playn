package com.example.robovm;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.dispatch.DispatchQueue;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSLocale;
import org.robovm.apple.uikit.UIApplication;
import org.robovm.apple.uikit.UIApplicationDelegateAdapter;
import org.robovm.apple.uikit.UIApplicationLaunchOptions;
import org.robovm.apple.uikit.UIDevice;
import org.robovm.apple.uikit.UIInterfaceOrientationMask;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIWindow;

import playn.robovm.RoboPlatform;
import playn.robovm.RoboViewController;

import com.example.core.ButtonListener;
import com.example.core.MultipleView;

public class MultipleViewRoboVM extends UIApplicationDelegateAdapter {

  private MultipleView playn;
  private RoboViewController playnViewController;
  private RoboPlatform plat;
  private MyViewController rootViewController;
  private UIWindow window;
  RoboPlatform.Config config;


  @Override
  public boolean didFinishLaunching (UIApplication app, UIApplicationLaunchOptions launchOpts) {
    // create a full-screen window
    CGRect bounds = UIScreen.getMainScreen().getBounds();
    window = new UIWindow(bounds);

    // configure and create the PlayN platform
    config = new RoboPlatform.Config();
    config.orients = UIInterfaceOrientationMask.All;
    //RoboPlatform plat = RoboPlatform.create(window, config);
    playnViewController = new RoboViewController(window.getBounds(),config);
    plat = playnViewController.plat;

    // create and initialize our game
    playn = new MultipleView(plat);
    playn.setListener(new ButtonListener() {

      @Override
      public void click() {
        // TODO Auto-generated method stub
        plat.log().info("Go to native!!!");
        // Set up the view controller.
        rootViewController = new MyViewController();
        rootViewController.setListener(new ButtonListener() {

          @Override
          public void click() {
            plat.log().info("Go to playn!!");
            DispatchQueue.getMainQueue().async(new Runnable() {

              @Override
              public void run() {
                // TODO Auto-generated method stub
                window.removeFromSuperview();

                window.setRootViewController(playnViewController);
                window.makeKeyAndVisible();
                window.reloadInputViews();
                addStrongRef(window);
                rootViewController.release();
                rootViewController = null;
              }
            });
          }
        });
        window.setRootViewController(rootViewController);
      }
    });
    // make our main window visible (this starts the platform)
    window.setRootViewController(playnViewController);
    window.makeKeyAndVisible();
    addStrongRef(window);
    return true;
  }

  public static void main (String[] args) {
    NSAutoreleasePool pool = new NSAutoreleasePool();
    UIApplication.main(args, null, MultipleViewRoboVM.class);
    pool.close();
  }
}
