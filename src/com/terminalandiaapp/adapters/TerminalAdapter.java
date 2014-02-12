package com.terminalandiaapp.adapters;

import java.util.List;

import com.example.terminalandiaapp.R;
import com.terminalandiaapp.entities.Airplane;
import com.terminalandiaapp.entities.Bus;
import com.terminalandiaapp.entities.Vehicle;
import com.terminalandiaapp.entities.Vessel;
import com.terminalandiaapp.iface.IAdapterActions;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TerminalAdapter extends AbstractListAdapter<Vehicle> implements IAdapterActions<Vehicle>{

	private LayoutInflater inflater;
	public TerminalAdapter(Context context, List<Vehicle> lists) {
		super(context, lists);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(context);
		
	}

	@Override
	public View getOverridedView(int position, View child, ViewGroup root) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(child == null){
			holder = new ViewHolder();
			child = inflater.inflate(R.layout.search_row, root, false);
			holder.image = (ImageView) child.findViewById(R.id.img_vehicle);
			holder.name = (TextView) child.findViewById(R.id.vehicle_name);
			holder.phone = (TextView) child.findViewById(R.id.vehicle_phone);
			holder.email = (TextView) child.findViewById(R.id.vehicle_email);
			holder.address = (TextView) child.findViewById(R.id.vehicle_address);
			
			child.setTag(holder);
		}else{
			holder = (ViewHolder) child.getTag();
		}
		int width = holder.image.getWidth();
		int height = holder.image.getHeight();
		
		Bitmap image = null;
		Vehicle v = getList().get(position);
		if(v instanceof Vessel){
			v = (Vessel)v;
			image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.vessel);
			//holder.image.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.vessel));
		}else if(v instanceof Bus){
			v = (Bus)v;
			image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bus);
			//holder.image.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.bus));
		}else if(v instanceof Airplane){
			v = (Airplane)v;
			image = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.plane);
			//holder.image.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.plane));
		}

		if(width <= 0){
			width = 120;
		}
		if(height <= 0){
			height = 120;
		}
		holder.image.setImageBitmap(Bitmap.createScaledBitmap(image, width, height, false));
		
		holder.name.setText(v.getName());
		holder.phone.setText(v.getPhone());
		holder.email.setText(v.getEmail());
		holder.address.setText(v.getAddress());
		
		return child;
	}
	
	private static class ViewHolder {
		private ImageView image;
		private TextView name;
		private TextView phone;
		private TextView email;
		private TextView address;
	}

	@Override
	public void add(Vehicle e) {
		// TODO Auto-generated method stub
		getList().add(e);
		notifyDataSetChanged();
		
	}

	@Override
	public void delete(int index) {
		// TODO Auto-generated method stub
		getList().remove(index);
		notifyDataSetChanged();
	}

	@Override
	public void update(int pos, Vehicle object) {
		// TODO Auto-generated method stub
		getList().set(pos, object);
		notifyDataSetChanged();
	}

	@Override
	public List<Vehicle> getAll() {
		// TODO Auto-generated method stub
		return getList();
	}

}
