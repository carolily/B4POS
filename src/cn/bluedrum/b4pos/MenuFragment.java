package cn.bluedrum.b4pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MenuFragment extends Fragment 
implements android.view.View.OnClickListener ,android.view.View.OnKeyListener{
	//InvoiceFragment aaa=new InvoiceFragment();
	//protected static final Context Context = null;
			//protected static final Context context = (Activity)MenuFragment;
			//��ʾ���������HashMap��ArrayList����.
			// HashMap���±����ַ������洢������Object,��ʾ���������
			private ArrayList<HashMap<String,Object>> gridData  = null;
			String temp[];
			int tempctrl[];
			private GridView gridView = null;
			public Button btntaohao;
			public TextView txtpeople;
			public int aaa=0;
			
			SimpleAdapter adapter;
			public String name4[]={
					"������� 15Ԫ/��","���޷�Ƭ 35Ԫ/��","����ţ�� 35Ԫ/��","���躣��˿ 15Ԫ/��",
					"ˬ�ں�ľ�� 15Ԫ/��","����˿ 15Ԫ/��"	,"�⽷Ƥ�� 15Ԫ/��","����ƹ� 15Ԫ/��",
					"Ƥ�� 15Ԫ/��","Ƥ���� 25Ԫ/��","��Ƭ 35Ԫ/��","Ƥ���� 20Ԫ/��",
					"������� 28Ԫ/��","��ľ�� 20Ԫ/��","����ţ�� 35Ԫ/��","��Ƭ�� 38Ԫ/��",
					"����ţ�� 32Ԫ/��","����Ƥ�� 15Ԫ/��","��ƬС 20Ԫ/��","����� 38Ԫ/��","��Ƥ�� 15Ԫ/��",
				};
			public String name1[]={
					"��֭�۱��� 88Ԫ/��","�������� 1168Ԫ/��","��Ϻ���� 998Ԫ/��","��ʿ����Ϻ 1008Ԫ/��",
					"���������� 65Ԫ/��","������ 55Ԫ/��","���������� 30Ԫ/��","�����ƴ�� 98Ԫ/��",
					"���� 298Ԫ/��","ľ����ѩ�� 38Ԫ/��","����Ϻ 58Ԫ/��","����Ϻ 1108Ԫ/��",
					"���������� 68Ԫ/��","��������צ�� 48Ԫ/��","����Ϻ 68Ԫ/��","��֭������ 78Ԫ/��",
					"���׷�˿��Ԫ�� 88Ԫ/��","����ƴ�� 88Ԫ/��","����з 128Ԫ/��","����ƴ�� 58Ԫ/��","��բз 48Ԫ/��",
				};
			public String name3[]={
					"��ն� 28Ԫ/��","�ͼ������� 20Ԫ/��","��嶹�� 12Ԫ/��","���м� 28/��",
					"±ˮƴ�� 28Ԫ/��","��֭�� 20Ԫ/��","±ˮ��Ѽ 30Ԫ/��","���ն��� 32Ԫ/��",
					"±ˮ���� 20Ԫ/��","±ˮ���� 38Ԫ/��","±ˮ��ƴ 28Ԫ/��","���� 25Ԫ/��",
					"Ѽ�� 18Ԫ/��","±ˮ�� 28Ԫ/��","��꼦 28Ԫ/��","���� 18Ԫ/��",
					"�������� 22Ԫ/��","�ն���ֻ 18Ԫ/��","±Ѽ 38Ԫ/��","ƬƤѼ 58Ԫ/��","±ˮСƴ 28Ԫ/��",
					
				};
			
			
			public String name5[]={
					"������ 28Ԫ/��","ӡ�ȷɱ� 20Ԫ/��","С���� 12Ԫ/��","�Ϲϱ� 18Ԫ/��",
					"�㽶�� 28Ԫ/��","ӡ�ȱ��� 20Ԫ/��","���� 30Ԫ/��","������ 32Ԫ/��",
					"�Ϲϱ��� 20Ԫ/��","����� 38Ԫ/��","ƻ���� 28Ԫ/��","����� 25Ԫ/��",
					"������ 18Ԫ/��","���� 28Ԫ/��","С�� 28Ԫ/��","�����׷� 18Ԫ/��",
					"������ͷ 22Ԫ/��","����Сʳ 15Ԫ/��","��̢ 38Ԫ/��","���� 38Ԫ/��",
					"��� 38Ԫ/��",
				};
			
			public String name2[]={
					"ʥ�ӻ� 28Ԫ/��","������ 20Ԫ/��","ˮ��ţ�� 32Ԫ/��","ˮ���� 28Ԫ/��",
					"�㹽�Ϻ��� 28Ԫ/��","���Էʳ� 20Ԫ/��","С���� 30Ԫ/��","���޲� 32Ԫ/��",
					"ëѪ�� 38Ԫ/��","����з 58Ԫ/��","�����ļ��� 28Ԫ/��","����Ϻ 68Ԫ/��",
					"ˮ��ë�� 48Ԫ/��","�����Ｆ 28Ԫ/��","ơ��Ѽ 58Ԫ/��","���Ӽ� 32Ԫ/��",
					"�������� 22Ԫ/��","�������� 16Ԫ/��","������Ƭ 18Ԫ/��","�����ռ� 58Ԫ/��","�ع��� 48Ԫ/��",
					
				};
			
			//���ı�����Ӳ�Ʒ���ƺͼ۸�
			private TextView textview=null;
			public static void showMessage(Context context, String text)
			{
				Toast.makeText(context, text, Toast.LENGTH_LONG).show();
			
			}
			
			public void createGridData()
			{
				ArrayList<HashMap<String,Object>> gridData = new ArrayList<HashMap<String,Object>>();
			
				for(int i=0 ; i < 21 ;i++)
				{
					HashMap<String,Object> node = new HashMap<String,Object>();				
					node.put("image", R.drawable.lb1+i);
					node.put("text", name1[i]);
					gridData.add(node);
				}
				temp=new String[]{"image","text"};
			
				tempctrl=new int[]{R.id.foodImage,R.id.textView1};
				
			}
			
		
			
			
			
			
			public void createGridData1(int str)
			{
				//SimpleAdapter adapter;
				
				switch(str)
				{
					case 1:
						aaa=1;
						ArrayList<HashMap<String,Object>> gridData1 = new ArrayList<HashMap<String,Object>>();
						gridData1.clear();
						adapter.notifyDataSetChanged();
							for(int i=0 ; i < 21 ;i++)
							{
								HashMap<String,Object> node = new HashMap<String,Object>();				
								node.put("image", R.drawable.hx1+i);
								node.put("text", name1[i]);
								gridData1.add(node);
							}
							temp=new String[]{"image","text"};
						
							tempctrl=new int[]{R.id.foodImage,R.id.textView1};
							
							adapter = new SimpleAdapter(getActivity(), gridData1, R.layout.menu_item, 
									/* ��ָ����һ����������һ������id��Ӧ*/
									temp,tempctrl);
							
						
							//�Ѵ��������������õ�ListView�ؼ���.
							gridView.setAdapter(adapter);
							adapter.notifyDataSetChanged();
					break;
					case 2:
						aaa=2;
						ArrayList<HashMap<String,Object>> gridData2 = new ArrayList<HashMap<String,Object>>();
						gridData2.clear();
						adapter.notifyDataSetChanged();
						for(int i=0 ; i < 21 ;i++)
						{
							HashMap<String,Object> node = new HashMap<String,Object>();				
							node.put("image", R.drawable.cc1+i);
							node.put("text", name2[i]);
							gridData2.add(node);
						}
						temp=new String[]{"image","text"};
						tempctrl=new int[]{R.id.foodImage,R.id.textView1};
		
						adapter = new SimpleAdapter(getActivity(), gridData2, R.layout.menu_item, 
								/* ��ָ����һ����������һ������id��Ӧ*/
								temp,tempctrl);
						
					
						//�Ѵ��������������õ�ListView�ؼ���.
						gridView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						
						
						break;
					case 3:
						aaa=3;
						ArrayList<HashMap<String,Object>> gridData3 = new ArrayList<HashMap<String,Object>>();
						gridData3.clear();
						adapter.notifyDataSetChanged();
						for(int i=0 ; i < 21 ;i++)
						{
							HashMap<String,Object> node = new HashMap<String,Object>();				
							node.put("image", R.drawable.ls1+i);
							node.put("text", name3[i]);
							gridData3.add(node);
						}
						temp=new String[]{"image","text"};
					
						tempctrl=new int[]{R.id.foodImage,R.id.textView1};
						
						adapter = new SimpleAdapter(getActivity(), gridData3, R.layout.menu_item, 
								/* ��ָ����һ����������һ������id��Ӧ*/
								temp,tempctrl);
						
					
						//�Ѵ��������������õ�ListView�ؼ���.
						gridView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						
						break;
					case 4:
						aaa=4;
						ArrayList<HashMap<String,Object>> gridData4 = new ArrayList<HashMap<String,Object>>();
						gridData4.clear();
						adapter.notifyDataSetChanged();
						for(int i=0 ; i < 21 ;i++)
						{
							HashMap<String,Object> node = new HashMap<String,Object>();				
							node.put("image", R.drawable.lb1+i);
							node.put("text", name4[i]);
							gridData4.add(node);
						}
						temp=new String[]{"image","text"};
					
						tempctrl=new int[]{R.id.foodImage,R.id.textView1};
						
						adapter = new SimpleAdapter(getActivity(), gridData4, R.layout.menu_item, 
								/* ��ָ����һ����������һ������id��Ӧ*/
								temp,tempctrl);
						
					
						//�Ѵ��������������õ�ListView�ؼ���.
						gridView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						
						break;
					case 5:
						aaa=5;
					
						ArrayList<HashMap<String,Object>> gridData5 = new ArrayList<HashMap<String,Object>>();
						gridData5.clear();
						adapter.notifyDataSetChanged();
						
						for(int i=0 ; i < 21 ;i++)
						{
							HashMap<String,Object> node = new HashMap<String,Object>();				
							node.put("image", R.drawable.zs1+i);
							node.put("text", name5[i]);
							gridData5.add(node);
						}
						temp=new String[]{"image","text"};
					
						tempctrl=new int[]{R.id.foodImage,R.id.textView1};
						
						adapter = new SimpleAdapter(getActivity(), gridData5, R.layout.menu_item, 
								/* ��ָ����һ����������һ������id��Ӧ*/
								temp,tempctrl);
						
					
						//�Ѵ��������������õ�ListView�ؼ���.
						gridView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						break;
				
				
				
				}
				
			}
			
			
			
			
			
			
	//ͨ��������ʹ�ò����ļ���������
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//���ݲ����ļ�����Fragment����
    	View view  = inflater.inflate(R.layout.fragment_menu,container,false);
    	gridView = (GridView)view.findViewById(R.id.gridView1);
		textview=(TextView)view.findViewById(R.id.textView1);
		 btntaohao=(Button)view.findViewById(R.id.btnth);
		
		txtpeople=(TextView)view.findViewById(R.id.textpeo);
		Bundle bundle=this.getActivity().getIntent().getExtras();
		String str1=bundle.getString("zuohao");
		String str2=bundle.getString("people");
		btntaohao.setText(str1);
		txtpeople.setText(str2);
    	createGridData();
		
		/* ���������벼�ֹ�ϵ 
		 *  �ڶ������������ݣ��������Ƕ�Ӧ�Ĳ����ļ�
		 * */
    	
		//SimpleAdapter adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* ��ָ����һ����������һ������id��Ӧ*/
			//	new String[]{"image"},new int[]{R.id.foodImage});
		
	
    	
		adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* ��ָ����һ����������һ������id��Ӧ*/
				temp,tempctrl);
		
		
		//�Ѵ��������������õ�ListView�ؼ���.
		//gridView.setAdapter(adapter);
	
		//���ӶԵ�����Ƶļ���
		gridView.setOnItemClickListener(new OnItemClickListener(){

		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//����gridData
			
				//��ȡѡ����
				//node1=gridData.get(arg2);
			
				//Iterator<HashMap<String,Object>> it= gridData.iterator();
				//HashMap<String,Object> node1 = new HashMap<String,Object>();
				//int i=0;
				//String key;		
				//Object value;	
				//Iterator<String> set;
				
				 //while(it.hasNext()&&i++<=arg3){ 
					// node1=it.next();	
					 //set=node1.keySet().iterator();
					// while(set.hasNext()){
						
						// key=set.next();	
						// value=node1.get(key);	
						 //����Ϊ��key��value�ľ��崦��
						 //showMessage(getActivity(),"�ҵ���Ʒ"+value+    arg3);
						// }
					// }
					
				switch(aaa)
				{
				case 1:
				String str1=name1[(int) arg3];//��ȡѡ����
				InvoiceFragment frag1 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
				
				frag1.inertInvoice1(str1);
				break;
				case 2:
					String str2=name2[(int) arg3];//��ȡѡ����
					InvoiceFragment frag2 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag2.inertInvoice1(str2);
					break;
				case 3:
					String str3=name3[(int) arg3];//��ȡѡ����
					InvoiceFragment frag3 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag3.inertInvoice1(str3);
					break;
				case 4:
					String str4=name4[(int) arg3];//��ȡѡ����
					InvoiceFragment frag4 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag4.inertInvoice1(str4);
					break;
				
				
				case 5:
					String str5=name5[(int) arg3];//��ȡѡ����
					InvoiceFragment frag5 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag5.inertInvoice1(str5);
					break;
				}
				/*	
				int i=str.length();
				StringBuilder builder=new StringBuilder(str);
				int j=str.indexOf(' ');
				
				StringBuilder s1=builder.delete(j,i);
				String s2=s1.toString();//��Ʒ����
				
				//ȡ��Ʒ���Ƶļ۸�
				
				StringBuilder builder1=new StringBuilder(str);
				int k=str.indexOf('/');
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
				*/
			
				/*
				String str2=null;
				for(int ii=0;ii<ch.length;ii++)
				{
					if(Character.isDigit(ch[ii]))
					{
						str2=str2+ch[ii];
					}
					else
					{
						break;
					}
					
				}
				
				showMessage(getActivity(),"�ҵ��۸�"+s9);
				
				HashMap<String,Object> node31 = new HashMap<String,Object>();
				node31.put("name", s2);
				node31.put("price", s9);
				node31.put("unit", "1");
				node31.put("quatity", "��");
				frag.listData.add(node31);
				*/
				/*
				Intent intent=new Intent();
				intent.setClass(getActivity(),InvoiceFragment.class );
				Bundle bundle=new Bundle();
				bundle.putString("name", str);
				//bundle.putString("jiage", s9);
				intent.putExtras(bundle);
				startActivity(intent);
				*/
				  }
			
			});
		
    	return view;
    }
    private class MySimpleAdapter extends SimpleAdapter{

		

		public MySimpleAdapter(Context context,
				List<? extends Map<String, ?>> data, int resource,
				String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
		}
    	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v= super.getView(position, convertView, parent);
			  GridView btn=(GridView) v.findViewById(R.id.gridView1);
			   //Ϊÿһ�а�ť���ò�ͬ��tag���Ա��ڵ��ʱ�ж�����һ�С�
			   btn.setTag(position);
			   
			   //����GridView���¼�������
			   btn.setOnClickListener(MenuFragment.this);
			   
			   ImageView imageview =(ImageView)v.findViewById(R.id.foodImage);
			   imageview.setTag(position);
			   imageview.setOnClickListener(MenuFragment.this);
			   
			
			
			return v;
		}
    	
    	
    }
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		int id  = arg0.getId();
		switch(id)
		{
		case R.id.foodImage:
			
			break;
		
		}
		
	}
    
	
    
}