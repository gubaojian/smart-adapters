package io.nlopez.smartadapters.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import io.nlopez.smartadapters.builders.BindableLayoutBuilder;
import io.nlopez.smartadapters.builders.DefaultBindableLayoutBuilder;
import io.nlopez.smartadapters.utils.Mapper;
import io.nlopez.smartadapters.utils.ThreadHelper;
import io.nlopez.smartadapters.utils.ViewEventListener;
import io.nlopez.smartadapters.views.BindableLayout;

/**
 * Adapter for {@code AbsListView} based widgets
 */
public class MultiAdapter extends BaseAdapter {

    protected Mapper mapper;
    protected List listItems;
    protected ViewEventListener viewEventListener;
    protected BindableLayoutBuilder builder;

    public MultiAdapter(Mapper mapper, List listItems) {
        this(mapper, listItems, createDefaultBuilder());
    }

    public MultiAdapter(Mapper mapper, List listItems, BindableLayoutBuilder builder) {
        this.listItems = listItems;
        this.mapper = mapper;
        if (builder == null) {
            this.builder = createDefaultBuilder();
        } else {
            this.builder = builder;
        }
    }

    public void setItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems = items;
        notifyDataSetChanged();
    }

    public void addItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.add(item);
        notifyDataSetChanged();
    }

    public void delItem(Object item) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.remove(item);
        notifyDataSetChanged();
    }

    public void addItems(List items) {
        ThreadHelper.crashIfBackgroundThread();
        listItems.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems() {
        ThreadHelper.crashIfBackgroundThread();
        listItems.clear();
        notifyDataSetChanged();
    }

    public ViewEventListener getViewEventListener() {
        return viewEventListener;
    }

    public void setViewEventListener(ViewEventListener viewEventListener) {
        this.viewEventListener = viewEventListener;
    }

    public void notifyAction(int actionId, Object object, int position, View view) {
        if (viewEventListener != null) {
            viewEventListener.onViewEvent(actionId, object, position, view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (listItems == null) {
            return 0;
        }
        Object object = getItem(position);
        if (mapper.containsObjectClass(object.getClass())) {
            return mapper.position(object.getClass());
        } else {
            return 0;
        }
    }

    @Override
    public int getViewTypeCount() {
        return mapper.size();
    }

    @Override
    public int getCount() {
        return listItems == null ? 0 : listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems == null ? null : listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BindableLayout viewGroup = (BindableLayout) convertView;
        if (viewGroup == null) {
            viewGroup = builder.build(parent, mapper, getItem(position).getClass(), getItem(position));
        }

        if (viewGroup != null) {
            viewGroup.setViewEventListener(viewEventListener);
            viewGroup.bind(getItem(position), position);
        }
        return viewGroup;
    }

    private static BindableLayoutBuilder createDefaultBuilder() {
        return new DefaultBindableLayoutBuilder();
    }
}
