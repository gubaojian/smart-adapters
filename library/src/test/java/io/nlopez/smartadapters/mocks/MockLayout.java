package io.nlopez.smartadapters.mocks;

import android.content.Context;
import android.util.AttributeSet;

import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Simple view layout
 */
public class MockLayout extends BindableLayout<MockModel> {

    public MockLayout(Context context) {
        super(context);
    }

    public MockLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MockLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void bind(MockModel item) {

    }
}
