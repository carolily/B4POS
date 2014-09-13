package cn.bluedrum.b4pos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class InvoiceFragment extends Fragment 
implements android.view.View.OnClickListener ,android.view.View.OnKeyListener{
	
	String str3=null;	
	private static DB_helper mDbHelper;
	private static SQLiteDatabase mdb;
	private Context context;
	String str4=null;
	public ArrayList<HashMap<String,Object>> listData  = null;
	//ָ��IP��ַ
	private static final String SERVERIP = "192.168.1.113";//"127.0.0.1"��������
	//ָ���˿�
	private static final int SERVERPORT = 13401;
	//����һ���߳�
	private Thread m_thread = null;
	//����һ��SOCKET
	private Socket m_sock= null;
	String temp=null;
	//����һ���ļ����������
	private BufferedReader m_reader= null;
	private PrintWriter m_writer = null;

	
	
	public  float total = 0;
	private float temptotal=0;
	private GridView taihaoGridView=null;
	private ListView listView = null;
	private TextView totalView = null;
	private EditText barcodeView = null;
	private MySimpleAdapter listAdapter = null;
	private int b_taihao=0,b_people=0;
	String s_str1=null,s_str2=null;
	
	String str_name=null;
	int str_number=0;
	float str_danjia=0;
	float str_jine=0;
	float str_zhekou=0;
	private ArrayList<HashMap<String,Object>> gridData1  = null;
	public final static String TAG="transDatabse"; 
	public String name10[]={
			"̨��1 3��","̨��2 5��","̨��3 2��","̨��4 5��",
			"̨��5 12��","̨��6 8��"	,"̨��7 3��","̨��8 9��",
			"Ƥ�� 15Ԫ/��","Ƥ�� 15Ԫ/��","���޷�Ƭ 35Ԫ/��","Ƥ�� 15Ԫ/��",
			"̨��9 15��","̨��10 2��","̨��11 6��","̨��12 4��",
			"̨��13 5��","̨��14 8��","̨��15 10��","̨��16 15��",
		};
	
	
	
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
	
	/*
	
	public void createListData1()
	{
		SimpleAdapter adpter;
		
		for(int i=0;i<60;i++)
		{
			HashMap<String,Object> node10=new HashMap<String,Object>();
			//node10.put("taihao", R.id.);
			node10.put("text11", name10[i]);
			gridData1.add(node10);
		
		}
		String temp[]=new String[]{"taihao","text11"};
		int tempctrl[]=new int[]{R.id.btntaihao,R.id.txtdesc};
		
		adpter=new SimpleAdapter(getActivity(),gridData1,R.layout.show_func,temp,tempctrl);
		taihaoGridView.setAdapter(adpter);
		
		
	}
	*/
	//ͨ��������ʹ�ò����ļ���������
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//���ݲ����ļ�����Fragment����
    	View view  = inflater.inflate(R.layout.fragment_invoice,container,false);
    	context=(Context)getActivity();
    	Bundle bundle=this.getActivity().getIntent().getExtras();
    	s_str1=bundle.getString("zuohao");
    
    	s_str2=bundle.getString("people");
    	listView = (ListView)view.findViewById(R.id.listView1);
    	//taihaoGridView=(GridView)view.findViewById(R.id.gridView1);
    	totalView = (TextView)view.findViewById(R.id.total);
		barcodeView = (EditText)view.findViewById(R.id.barcode);
		mDbHelper = new DB_helper(context);
		
	
		barcodeView.setOnKeyListener(this);
		
		
		((Button)view.findViewById(R.id.query)).setOnClickListener(this);
		((Button)view.findViewById(R.id.clear)).setOnClickListener(this);
		((Button)view.findViewById(R.id.pay)).setOnClickListener(this);
		((Button)view.findViewById(R.id.saveInvoice)).setOnClickListener(this);
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
  	
  	
  	
  	
  	
  	
  	
	public boolean inertInvoice1(String name)
  	{
  		//showMessage(getActivity(),"��ѯ����"+name);
  		HashMap<String,Object> node31 = new HashMap<String,Object>();
  		listView = (ListView)getView().findViewById(R.id.listView1);
  		
  		
  			int i=name.length();
  			StringBuilder builder=new StringBuilder(name);
  			int j=name.indexOf(' ');
  			
  			StringBuilder s1=builder.delete(j,i);
  			String s2=s1.toString();//��Ʒ����
  			
  			//ȡ��Ʒ���Ƶļ۸�
  			
  			StringBuilder builder1=new StringBuilder(name);
  			int k=name.indexOf('/');
  			StringBuilder s3=builder1.delete(k,i);
  			String s4=s3.toString();
  			//ȡ�۸�
  		
  			//��ȡs4
  			StringBuilder builder2=new StringBuilder(s4);
  			int m=s4.indexOf(' ');
  			StringBuilder s7=builder1.delete(0,m);
  			String s8=s7.toString();
  			
  			char ch[]=s8.toCharArray();
  			StringBuilder builder3=new StringBuilder(s8);
  			int y=s8.length();
  			StringBuilder s9=builder3.delete(y-1,y);
  			String s10=s9.toString();
  			
  			node31.put("name", s2);
  			node31.put("unit", "��");
  			node31.put("quatity", "1");
  			node31.put("price", s10);
  			
  		
  			float f=Float.parseFloat(s10);
  			temptotal=f;
  			m_thread=new Thread(m_runnable1);
  			m_thread.start();
  			total +=f;
  			totalView.setText(Float.toString(total));
  			
  		
  			listData.add(node31);
  			listAdapter.notifyDataSetChanged(); //֪ͨ�������
  			return true;
  		
  	}
  	
  	
  	
 	public void createDialog()
  	{
  		 new AlertDialog.Builder(getActivity())
 	   //.setMessage(Html.fromHtml(tmp))
 	   .setMessage("Pay")
 	   .setPositiveButton("OK", null)
 	  .create().show();
 	
  	}
  	
    
    @Override
	public void onClick(View v) {
		
		int id  = v.getId();
		switch(id)
		{
		case R.id.pay:
			//�����ύ��ť���������ݵ�������
				mdb = mDbHelper.getWritableDatabase();
				String tmp_sql=null,tmpsql1;
				Date d=new Date();
				SimpleDateFormat fmt= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				String str=fmt.format(d);
				int i=s_str1.length();
				StringBuilder builder=new StringBuilder(s_str1);
				StringBuilder s9=builder.delete(0,2);
		  		String s10=s9.toString();
				
		  		int j=s_str2.length();
		  		//StringBuilder builder1=new StringBuilder(s_str2);
		  		//StringBuilder builder2=builder1.delete(j-1,j);
		  		//String s11=builder2.toString();
		    	b_taihao=Integer.parseInt(s10);
		    	//b_people=Integer.parseInt(s11);
				m_thread=new Thread(m_runnable4);
				m_thread.start();
			
				
				/*
				//�ѿ��˵�Ĳ˷��͵�����������ListView�е����ݣ�����ListView
				Iterator<HashMap<String,Object>> it= listData.iterator();		
				HashMap<String,Object> hash;		
				String key;		
				Object value;		
				Iterator<String> set;	
					while(it.hasNext()){		
						hash=it.next();			
						set=hash.keySet().iterator();			
						while(set.hasNext()){
							
							key=set.next();	
							
							value=hash.get(key);	
							temp=temp+'\n'+value;
				//����Ϊ��key��value�ľ��崦��	
						}		
					}
					
					showMessage(getActivity(),"������Ϣ"+temp);
				*/
					if(!listData.isEmpty()){
					Iterator<HashMap<String,Object>> it1= listData.iterator();		
					HashMap<String,Object> hash1;		
					//String key;		
					Object value1=new Object();	
					
					Object value2=new Object();
					
					Object value3=new Object();	
					
					Object value4=new Object();
					String value5=null;
				
				   // showMessage(getActivity(),"������Ϣ"+temp);
				    while(it1.hasNext()) {
						hash1 = it1.next();
								
						value1 = hash1.get("name");
						temp+=value1.toString() +"   ";		
						value2 = hash1.get("price");
						temp+=value2.toString() +"   ";
						value3=hash1.get("quatity");
						temp+=value3.toString() +"   ";		
						value4=hash1.get("unit");
						temp+=value4.toString() +"   \n";
						
						str3="#TRP:REC,001,1,"+value1+","+value4+","+value3+","+value2+","+100+",0\n";
						
						
					//	showMessage(getActivity(),"������Ϣ"+temp);
						showMessage(getActivity(),"������Ϣ"+str3);
						//tmp_sql =
								//"INSERT INTO tyinfo_table(id,name,price,number,uint,datetimeinfo) values ('"+b_taihao+"', '"+value1.toString()+"', '"+value2.toString()+"','"+value3.toString()+"','"+value4.toString()+"','"+str+"')";
					
						//md/b.execSQL(tmp_sql);
						
					}
					}
					
					//tmpsql1=
					//		 "INSERT INTO tongji_table(id,number,money) values('"+b_taihao+"','"+b_people+"','"+total+"')";
				//	mdb.execSQL(tmpsql1);
					//total=0;
					//Toast.makeText(getActivity(), "��˳ɹ�", Toast.LENGTH_SHORT);
					
			//createDialog();
			break;
			
		case R.id.saveInvoice:
			
			if(!listData.isEmpty()){
				Iterator<HashMap<String,Object>> it1= listData.iterator();		
				HashMap<String,Object> hash1;		
				//String key;		
				Object value1=new Object();	
				
				Object value2=new Object();
				
				Object value3=new Object();	
				
				Object value4=new Object();
				String value5=null;
			
			   // showMessage(getActivity(),"������Ϣ"+temp);
			    while(it1.hasNext()) {
					hash1 = it1.next();
							
					value1 = hash1.get("name");
					temp+=value1.toString() +"   ";		
					value2 = hash1.get("price");
					temp+=value2.toString() +"   ";
					value3=hash1.get("quatity");
					temp+=value3.toString() +"   ";		
					value4=hash1.get("unit");
					temp+=value4.toString() +"   \n";
					
					str3+="#TRP:REC,001,1,"+value1+","+value4+","+value3+","+value2+","+100+",0\n";
					
					
				//	showMessage(getActivity(),"������Ϣ"+temp);
					showMessage(getActivity(),"������Ϣ"+str3);
					//tmp_sql =
							//"INSERT INTO tyinfo_table(id,name,price,number,uint,datetimeinfo) values ('"+b_taihao+"', '"+value1.toString()+"', '"+value2.toString()+"','"+value3.toString()+"','"+value4.toString()+"','"+str+"')";
				
					//md/b.execSQL(tmp_sql);
					
				}
				}
			
			
			
			
			
			Intent intent=new Intent();
			intent.setClass(getActivity(), ShouMoney.class);
			Bundle bundle=new Bundle();
			
			bundle.putFloat("money", total);
			bundle.putString("bao", str3);
			//m_thread=new Thread(m_runnable);
		//	m_thread.start();
			
			//bundle.putString("jine",a );
			//bundle.putString("status", b);
			//����״̬��
			listData.clear();
			listAdapter.notifyDataSetChanged();
			intent.putExtras(bundle);
			this.startActivity(intent);
			
			//getActivity().startActivity(getActivity(),ShouMoney.class);
			
			break;
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
    
    private Runnable m_runnable= new Runnable() {
		//String line;
		public void run(){
			String a1="#CPD:NUM,123,001,"+total+",2\n";
																									
			
			
		
			//��������ӡ��
			//#TRP:STR,<���к�>,<���뷽ʽ>,<�ַ������� >\n
			//String str1="#TRP:STR,1001,<���뷽ʽ>,+s_str1+��̨\n";
		
			//#TRP:DLI,<���к�>\n
			//String str2="#TRP:DLI,001\n";
			
			//#TRP:BEG,<����>,<ģ������>,<�ն˺�>,<����>,<������>,<��ˮ��>\n
			//Stirng str3="#TRP:BEG,1100,000,<�ն˺�>,<�㵥Ա����>,listData.size(),<��ˮ��>"
					
			//String str4="#TRP:REC,<����>,<�к�>,<����>,<���>,<����>,<����>,<���>,<�ۿ�>\n";
			//String str4="TRP:REC,<����>,1,";
			
			
			//#TRP:END,<����>,<��ˮ��>,<Ӧ��>,<ʵ��>,<����>,<˰��>\n
			//String str5="TRP:END,<����>,<��ˮ��>,total,"
					
			//String a1="#CPD:NUM,123,001,"+total+",2\n";
			try {
				m_sock=new Socket(SERVERIP,SERVERPORT);
				
				m_reader=new BufferedReader(new InputStreamReader(m_sock.getInputStream()));
				//������д������
				m_writer=new PrintWriter(m_sock.getOutputStream());
				
				//��ȡ�����������
				 m_writer.print(a1);
				// m_writer.print(str1);
				// m_writer.print(str2);
				// m_writer.print(str2);
				
				    m_writer.flush();
				
			} catch (UnknownHostException e) {
			
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
			
	
		}
	};
	private Handler m_handler= new Handler() {
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			//m_etContent.append(msg.getData().getString("msg"));//�������¼��ӽ���
		}
	};
    
	 private Runnable m_runnable1= new Runnable() {
			//String line;
			public void run(){
				
				//String a="#CPD:NUM,123,001,2333.4,2\n";
				
				//���ͼ۸�
				String a1="#CPD:NUM,123,001,"+temptotal+",1\n";
				//String a2="#cpd:num,123,001,1,";	
				//String b="#CPD:STX,123,001,2\n";
				//�������

				try {
					m_sock=new Socket(SERVERIP,SERVERPORT);
					
					m_reader=new BufferedReader(new InputStreamReader(m_sock.getInputStream()));
					//������д������
					m_writer=new PrintWriter(m_sock.getOutputStream());
					//��ȡ�����������
					 m_writer.print(a1);
					
					    m_writer.flush();
					} catch (UnknownHostException e) {
				
					e.printStackTrace();
					} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
			}
			
		};
		
		void showMessageInThread(Context context,String msg)
		{
			 Looper.prepare();
			 Toast.makeText(context,msg ,Toast.LENGTH_SHORT).show();
			
				Looper.loop();
		}
		
		
	
		 private Runnable m_runnable4= new Runnable() {
				//String line;
			 
			
			
			 	String str5=null;
				public void run(){
					
					try {
						
						int in=listData.size();
						String str1="#TRP:BMP,001,logo\n";
						String str2="#TRP:BEG,001,001,0001,0001,1,12334\n"; 
						
						m_sock=new Socket(SERVERIP,SERVERPORT);
						m_reader=new BufferedReader(new InputStreamReader(m_sock.getInputStream()));
						//������д������
						m_writer=new PrintWriter(m_sock.getOutputStream());
						//��ȡ�����������
						 m_writer.print(str1);
						 m_writer.print(str2);
						
							if(!listData.isEmpty()){
								Iterator<HashMap<String,Object>> it1= listData.iterator();		
								HashMap<String,Object> hash1;		
								//String key;		
								Object value5=new Object();	
							
								Object value6=new Object();
								
								Object value7=new Object();	
								
								Object value8=new Object();
							
							   // showMessage(getActivity(),"������Ϣ"+temp);
							    while(it1.hasNext()) {
									hash1 = it1.next();
									Object value10=new Object();			
									value5 = hash1.get("name");
									str4+=value5.toString() +"   ";
									value6 = hash1.get("price");
									str4+=value6.toString() +"   ";
									value7=hash1.get("quatity");
									str4+=value7.toString() +"   ";
									value8=hash1.get("unit");
									str4+=value8.toString() +"   ";
									value10=Float.parseFloat((String) (value6))*(Integer.parseInt((String) value7));
									str4+=value10.toString() +"   \n";
									
									
									
									
						
									str5="#TRP:REC,001,1,"+value5+","+value8+","+value7+","+value6+","+value10+",0\n";
						//showMessageInThread(context,str3);
						//showMessageInThread(context,str4);
						//Toast.makeText(getActivity(),str3 ,Toast.LENGTH_SHORT).show();
						//Toast.makeText(getActivity(), str4, Toast.LENGTH_SHORT).show();
						
						 m_writer.print(str3);
						 m_writer.flush();
						}
							}
						 
						 
						 
						 
						 
						 
					
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
						
					
						    						
				
				}
				
			};
			
	
    
		
}
   