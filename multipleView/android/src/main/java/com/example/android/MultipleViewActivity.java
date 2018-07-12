package com.example.android;

import playn.android.GameActivity;

import com.example.core.MultipleView;

public class MultipleViewActivity extends GameActivity {

  @Override public void main () {
    new MultipleView(platform());
  }
}
