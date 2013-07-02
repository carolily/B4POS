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
		
		
		//�ֶ�����5������,ÿ�δ���һ��HashMap���뵽ArrayList����
		
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
	
	//ͨ��������ʹ�ò����ļ���������
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//���ݲ����ļ�����Fragment����
    	View view  = inflater.inflate(R.layout.fragment_invoice,container,false);
    	
    	listView = (ListView)view.findViewById(R.id.listView1);
    	
    	totalView = (TextView)view.findViewById(R.id.total);
		barcodeView = (EditText)view.findViewById(R.id.barcode);
		
		barcodeView.setOnKeyListener(this);
		
		
		((Button)view.findViewById(R.id.query)).setOnClickListener(this);
		((Button)view.findViewById(R.id.clear)).setOnClickListener(this);
    	
		listData = new ArrayList<HashMap<String,Object>>();
         // createListData();
		
		/* ���������벼�ֹ�ϵ 
		 *  �ڶ������������ݣ��������Ƕ�Ӧ�Ĳ����ļ�
		 * */
          
        /*
         * ��Fragment�ĵ��ø�Activity������ͬ
         *   ����ֱ�ӵ���findViewById();��û��ʵ���������������ͨ��Fragment����view.findViewById������
         *   Fragment����Content���࣬�����κ���Ҫʹ��content��������ʹ��
         *   getActivtiy()��ʵ��
         */
		listAdapter = new MySimpleAdapter(getActivity(), listData, R.layout.list_item, 
				/* ��ָ����һ����������һ������id��Ӧ*/
				new String[]{"name","unit","price","quatity"},new int[]{R.id.name,R.id.unit,R.id.price,R.id.quatity});
		
		
		listView = (ListView)view.findViewById(R.id.listView1);
		
		//�Ѵ��������������õ�ListView�ؼ���.
		listView.setAdapter(listAdapter);
		
		//���ӶԵ�����Ƶļ���
		//listView.setOnItemClickListener(this);
    	
    	return view;
    }
    
  //���������ҳ���Ʒ�����ӵ��嵥
  	public boolean inertInvoice(String code)
  	{
  		showMessage(getActivity(),"��ѯ����"+code);
  		Cursor result = MainActivity.database.queryProduct(code);
  		
  		
  		
  		if((result == null) || (result.getCount() <1))
  		{
  			showMessage(getActivity(),"û���ҵ���Ʒ"+code);
  			barcodeView.setText("");
  			return false;
  		}
  		
  		HashMap<String,Object> node1 = new HashMap<String,Object>();
  		
  		try{
  		
  			result.moveToFirst();
//  		  Log.i(TAG,"name idx"+result.getColumnIndex("Name") + "unit idx"+ result.getColumnIndex("Unit")
//  				   + "price idx "+ result.getColumnIndex("Price"));
  			//���ȸ��������ҳ��е������������ҳ�һ��ֵ����haspMap
  			Log.i(TAG,"name = "+result.getString(result.getColumnIndex("Name")));
  			Log.i(TAG,"unit = "+result.getString(result.getColumnIndex("Unit")));
  			
  			
  			node1.put("name", (String)result.getString(result.getColumnIndex("Name")));
  			node1.put("unit", (String)result.getString(result.getColumnIndex("Unit")));
  			
  			//node1.put("name", "��Ʒ1");
  			
  			node1.put("quatity", "1");
  			
  			
  			node1.put("price", result.getString(result.getColumnIndex("UnitPrice")));
              total += result.getFloat(result.getColumnIndex("UnitPrice"));
  			
  		//	Log.i(TAG,"count="+result.getCount()+","+ result.getColumnName(0));

  			
  			totalView.setText(Float.toString(total));
  		
  		
  		listData.add(node1);
  		listAdapter.notifyDataSetChanged(); //֪ͨ�������
  		
  	//	barcodeView.setText(""); //��շ�����һ��ɨ��
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
		{//ͬ����Ʒ����һ������
			int index = (Integer)v.getTag();
			HashMap<String,Object> node = listData.get(index);
			
			int q = Integer.parseInt((String) node.get("quatity"));
			q++;
			 total += Float.parseFloat((String) node.get("price"));
			 
			 totalView.setText(Float.toString(total));
		  		
		     node.put("quatity", Integer.toString(q));		
		  		
		  	 listAdapter.notifyDataSetChanged(); //֪ͨ�������
		}
			break;
		case R.id.fire:
		{//ɾ��ĳһ��
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
		{//��Ϊɨ��ǹ������������ϻس���,�Դ�Ϊ�������в�ѯ
			String tmp = barcodeView.getText().toString();
			if(tmp.length()<=1)
			{
				//ÿ������ɨ������ᷢ�������س������ڶ��λس���������ѯ
				barcodeView.setText("");
				return true;
			}
			inertInvoice(tmp);
			
			return true; //����ҪEditText����س�
			
		}
		else
			return false;
		
	}
    
    //���Ӷ԰����Ĵ���
    private class MySimpleAdapter extends SimpleAdapter{

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			
		}
		
		//ÿ���ڴ���һ��ʱ�����������
		@Override
		  public View getView(int position, View convertView, ViewGroup parent) {
		   // TODO Auto-generated method stub
			View v= super.getView(position, convertView, parent);
			   Button btn=(Button) v.findViewById(R.id.fire);
			   //Ϊÿһ�а�ť���ò�ͬ��tag���Ա��ڵ��ʱ�ж�����һ�С�
			   btn.setTag(position);
			   btn.setOnClickListener(InvoiceFragment.this);
			   
			   Button btnAdd=(Button) v.findViewById(R.id.add);
			   btnAdd.setTag(position);
			   btnAdd.setOnClickListener(InvoiceFragment.this);
			   
			   return v;
		  }

    }

}