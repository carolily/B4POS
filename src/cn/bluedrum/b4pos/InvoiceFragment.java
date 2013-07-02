package cn.bluedrum.b4pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InvoiceFragment extends Fragment 
implements android.view.View.OnClickListener ,android.view.View.OnKeyListener{
	
private ArrayList<HashMap<String,Object>> listData  = null;



	public float total = 0;

	private ListView listView = null;
	private TextView totalView = null;
	private EditText barcodeView = null;
	private MySimpleAdapter listAdapter = null;
	
	public final static String TAG="transDatabse"; 
	
	
	
	
	public static void showMessage(Context context, String text)
	{
		Toast.makeText(context, text, Toast.LENGTH_LONG).show();
	
	}
	
	public void createListData()
	{
		
		
		//手动增加5个数据,每次创建一个HashMap加入到ArrayList当中
		
			HashMap<String,Object> node1 = new HashMap<String,Object>();
			
			node1.put("name", "Steak Tartar");
			node1.put("unit", "");
			node1.put("price", "$12.00");
			
			listData.add(node1);
			
           HashMap<String,Object> node2 = new HashMap<String,Object>();
			
			node2.put("name", "Onion Soup");
			node2.put("unit", "Cup");
			node2.put("price", "$13.00");
			
			listData.add(node2);
			
          HashMap<String,Object> node3 = new HashMap<String,Object>();
			
			node3.put("name", "Cucumember Radish Salad");
			node3.put("unit", "");
			node3.put("price","$12.00");
			
			listData.add(node3);
			
         HashMap<String,Object> node4 = new HashMap<String,Object>();
			
			node4.put("name", "Filet Mignon");
			node4.put("unit", "16 Oz,Medium");
			node4.put("price","$39.00");
			
			listData.add(node4);
			
			  HashMap<String,Object> node5 = new HashMap<String,Object>();
				
				node5.put("name", "Clos Du Bois Chardonnay");
				node5.put("unit", "glass");
				node5.put("price","$4.00");
				
				listData.add(node5);
	}
	
	//通过本方法使用布局文件创建界面
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//根据布局文件创建Fragment布局
    	View view  = inflater.inflate(R.layout.fragment_invoice,container,false);
    	
    	listView = (ListView)view.findViewById(R.id.listView1);
    	
    	totalView = (TextView)view.findViewById(R.id.total);
		barcodeView = (EditText)view.findViewById(R.id.barcode);
		
		barcodeView.setOnKeyListener(this);
		
		
		((Button)view.findViewById(R.id.query)).setOnClickListener(this);
		((Button)view.findViewById(R.id.clear)).setOnClickListener(this);
    	
		listData = new ArrayList<HashMap<String,Object>>();
         // createListData();
		
		/* 建立数据与布局关系 
		 *  第二个参数是数据，第三个是对应的布局文件
		 * */
          
        /*
         * 在Fragment的调用跟Activity有所不同
         *   不能直接调用findViewById();它没有实现这个方法，必须通过Fragment顶层view.findViewById来调用
         *   Fragment不是Content子类，所以任何需要使用content参数，将使用
         *   getActivtiy()来实现
         */
		listAdapter = new MySimpleAdapter(getActivity(), listData, R.layout.list_item, 
				/* 简单指明哪一个内容与哪一个界面id对应*/
				new String[]{"name","unit","price","quatity"},new int[]{R.id.name,R.id.unit,R.id.price,R.id.quatity});
		
		
		listView = (ListView)view.findViewById(R.id.listView1);
		
		//把创建的适配器设置到ListView控件上.
		listView.setAdapter(listAdapter);
		
		//增加对点击控制的监听
		//listView.setOnItemClickListener(this);
    	
    	return view;
    }
    
  //根据条件找出商品并增加到清单
  	public boolean inertInvoice(String code)
  	{
  		showMessage(getActivity(),"查询条码"+code);
  		Cursor result = MainActivity.database.queryProduct(code);
  		
  		
  		
  		if((result == null) || (result.getCount() <1))
  		{
  			showMessage(getActivity(),"没有找到商品"+code);
  			barcodeView.setText("");
  			return false;
  		}
  		
  		HashMap<String,Object> node1 = new HashMap<String,Object>();
  		
  		try{
  		
  			result.moveToFirst();
//  		  Log.i(TAG,"name idx"+result.getColumnIndex("Name") + "unit idx"+ result.getColumnIndex("Unit")
//  				   + "price idx "+ result.getColumnIndex("Price"));
  			//首先根据列名找出列的索引，进而找出一个值加入haspMap
  			Log.i(TAG,"name = "+result.getString(result.getColumnIndex("Name")));
  			Log.i(TAG,"unit = "+result.getString(result.getColumnIndex("Unit")));
  			
  			
  			node1.put("name", (String)result.getString(result.getColumnIndex("Name")));
  			node1.put("unit", (String)result.getString(result.getColumnIndex("Unit")));
  			
  			//node1.put("name", "商品1");
  			
  			node1.put("quatity", "1");
  			
  			
  			node1.put("price", result.getString(result.getColumnIndex("UnitPrice")));
              total += result.getFloat(result.getColumnIndex("UnitPrice"));
  			
  		//	Log.i(TAG,"count="+result.getCount()+","+ result.getColumnName(0));

  			
  			totalView.setText(Float.toString(total));
  		
  		
  		listData.add(node1);
  		listAdapter.notifyDataSetChanged(); //通知界面更新
  		
  	//	barcodeView.setText(""); //清空方便下一次扫描
  		result.close();
  		}
  		catch(Exception e)
  		{
  			e.printStackTrace();
  			return false;
  		}
  		
  		return true;
  		
  	}
    
    @Override
	public void onClick(View v) {
		
		int id  = v.getId();
		switch(id)
		{
		case R.id.query:
			inertInvoice(barcodeView.getText().toString());
	         break;
		case R.id.clear:
			barcodeView.setText("");
	         break; 
		case R.id.add:
		{//同样商品新增一个数量
			int index = (Integer)v.getTag();
			HashMap<String,Object> node = listData.get(index);
			
			int q = Integer.parseInt((String) node.get("quatity"));
			q++;
			 total += Float.parseFloat((String) node.get("price"));
			 
			 totalView.setText(Float.toString(total));
		  		
		     node.put("quatity", Integer.toString(q));		
		  		
		  	 listAdapter.notifyDataSetChanged(); //通知界面更新
		}
			break;
		case R.id.fire:
		{//删除某一项
			int index = (Integer)v.getTag();
			
			HashMap<String,Object> node = listData.get(index);
			
			int q = Integer.parseInt((String) node.get("quatity"));
			
			total -= q * Float.parseFloat((String) node.get("price"));
			totalView.setText(Float.toString(total));
			
			//Utils.showMessage(getActivity(),"remove line"+index);
			listData.remove(index) ;
			listAdapter.notifyDataSetChanged();
		}	
			break;
	     default:       
		}
    }
    
    @Override
	public boolean onKey(View view, int keyCode, KeyEvent KeyEvent) {
		if(KeyEvent.KEYCODE_ENTER == keyCode)
		{//因为扫描枪会在条码最后附上回车符,以此为条件进行查询
			String tmp = barcodeView.getText().toString();
			if(tmp.length()<=1)
			{
				//每次条码扫描结束会发送两个回车键，第二次回车键不作查询
				barcodeView.setText("");
				return true;
			}
			inertInvoice(tmp);
			
			return true; //不需要EditText处理回车
			
		}
		else
			return false;
		
	}
    
    //增加对按键的处理
    private class MySimpleAdapter extends SimpleAdapter{

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			
		}
		
		//每次在创建一行时调用这个方法
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		   // TODO Auto-generated method stub
			View v= super.getView(position, convertView, parent);
			   Button btn=(Button) v.findViewById(R.id.fire);
			   //为每一行按钮设置不同的tag，以便在点击时判断是哪一行。
			   btn.setTag(position);
			   btn.setOnClickListener(InvoiceFragment.this);
			   
			   Button btnAdd=(Button) v.findViewById(R.id.add);
			   btnAdd.setTag(position);
			   btnAdd.setOnClickListener(InvoiceFragment.this);
			   
			   return v;
		  }

    }

}