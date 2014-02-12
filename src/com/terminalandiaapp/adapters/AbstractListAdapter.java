package com.terminalandiaapp.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *  An abstract implementatio of list adapter to be used by all adapters
 *  Author : David Montecillo 
 */
public abstract class AbstractListAdapter<E> extends BaseAdapter{

	
	private Context context;
	private List<E> lists ;
	
	public AbstractListAdapter(Context context , List<E> lists){
		this.context = context;
		this.lists = lists;
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lists.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return getOverridedView(arg0 , arg1, arg2);
	}
	/**
	 *  returns the Override view provided by its subclass
	 */
	public abstract View getOverridedView(int position , View child, ViewGroup root);
	
	
	public Context getContext(){
		return context;
	}
	
	public List<E> getList(){
		return lists;
	}
	


}
