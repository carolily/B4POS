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
			//表示结点数据是HashMap的ArrayList链表.
			// HashMap的下标是字符串，存储类型是Object,表示是任意对象
			private ArrayList<HashMap<String,Object>> gridData  = null;
			String temp[];
			int tempctrl[];
			private GridView gridView = null;
			public Button btntaohao;
			public TextView txtpeople;
			public int aaa=0;
			
			SimpleAdapter adapter;
			public String name4[]={
					"凉拌青瓜 15元/份","夫妻肺片 35元/份","麻辣牛肉 35元/份","凉拌海带丝 15元/份",
					"爽口黑木耳 15元/份","海带丝 15元/份"	,"尖椒皮蛋 15元/份","凉拌黄瓜 15元/份",
					"皮蛋 15元/份","皮蛋大 25元/份","肺片 35元/份","皮蛋中 20元/份",
					"红油猪耳 28元/份","黑木耳 20元/份","凉拌牛肉 35元/份","肺片大 38元/份",
					"凉拌牛腩 32元/份","精美皮蛋 15元/份","肺片小 20元/份","猪耳朵 38元/份","凉皮蛋 15元/份",
				};
			public String name1[]={
					"鲍汁扣鲍鱼 88元/份","澳龙刺身 1168元/份","龙虾刺身 998元/份","波士顿龙虾 1008元/份",
					"刺身三文鱼 65元/份","冰镇花螺 55元/份","豉椒炒花甲 30元/份","刺身大拼盘 98元/份",
					"乳猪 298元/份","木瓜炖雪蛤 38元/份","白灼虾 58元/份","青龙虾 1108元/份",
					"北极贝刺身 68元/份","豉椒炒八爪鱼 48元/份","椒盐虾 68元/份","豉汁蒸鲍鱼 78元/份",
					"蒜茸粉丝蒸元贝 88元/份","刺身拼盘 88元/份","香辣蟹 128元/份","刺身拼盘 58元/份","大闸蟹 48元/份",
				};
			public String name3[]={
					"深井烧鹅 28元/份","客家靓豆腐 20元/份","香煎豆腐 12元/份","白切鸡 28/份",
					"卤水拼盘 28元/份","酱汁鸡 20元/份","卤水烧鸭 30元/份","红烧豆腐 32元/份",
					"卤水豆腐 20元/份","卤水掌亦 38元/份","卤水大拼 28元/份","鹅亦 25元/份",
					"鸭掌 18元/份","卤水鸡 28元/份","镇店鸡 28元/份","叉烧 18元/份",
					"麻辣豆腐 22元/份","烧鹅整只 18元/份","卤鸭 38元/份","片皮鸭 58元/份","卤水小拼 28元/份",
					
				};
			
			
			public String name5[]={
					"榴莲酥 28元/份","印度飞饼 20元/份","小笼包 12元/份","南瓜饼 18元/份",
					"香蕉酥 28元/份","印度薄饼 20元/份","麻球 30元/份","玉米烙 32元/份",
					"南瓜饼大 20元/份","金瓜烙 38元/份","苹果酥 28元/份","生煎包 25元/份",
					"酸辣粉 18元/份","点心 28元/份","小吃 28元/份","桂林米粉 18元/份",
					"金银馒头 22元/份","北方小食 15元/份","蛋挞 38元/份","蛋卷 38元/份",
					"肉卷 38元/份",
				};
			
			public String name2[]={
					"圣子皇 28元/份","炒花甲 20元/份","水煮牛肉 32元/份","水煮鱼 28元/份",
					"香菇上海青 28元/份","干煸肥肠 20元/份","小炒肉 30元/份","娃娃菜 32元/份",
					"毛血旺 38元/份","香辣蟹 58元/份","干煸四季豆 28元/份","香辣虾 68元/份",
					"水煮毛肚 48元/份","爆炒田鸡 28元/份","啤酒鸭 58元/份","辣子鸡 32元/份",
					"红烧茄子 22元/份","酸辣鸡杂 16元/份","腐竹肉片 18元/份","土豆烧鸡 58元/份","回锅肉 48元/份",
					
				};
			
			//用文本框添加菜品名称和价格
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
									/* 简单指明哪一个内容与哪一个界面id对应*/
									temp,tempctrl);
							
						
							//把创建的适配器设置到ListView控件上.
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
								/* 简单指明哪一个内容与哪一个界面id对应*/
								temp,tempctrl);
						
					
						//把创建的适配器设置到ListView控件上.
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
								/* 简单指明哪一个内容与哪一个界面id对应*/
								temp,tempctrl);
						
					
						//把创建的适配器设置到ListView控件上.
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
								/* 简单指明哪一个内容与哪一个界面id对应*/
								temp,tempctrl);
						
					
						//把创建的适配器设置到ListView控件上.
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
								/* 简单指明哪一个内容与哪一个界面id对应*/
								temp,tempctrl);
						
					
						//把创建的适配器设置到ListView控件上.
						gridView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
						
						break;
				
				
				
				}
				
			}
			
			
			
			
			
			
	//通过本方法使用布局文件创建界面
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container , Bundle save)
    {
    	//根据布局文件创建Fragment布局
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
		
		/* 建立数据与布局关系 
		 *  第二个参数是数据，第三个是对应的布局文件
		 * */
    	
		//SimpleAdapter adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* 简单指明哪一个内容与哪一个界面id对应*/
			//	new String[]{"image"},new int[]{R.id.foodImage});
		
	
    	
		adapter = new SimpleAdapter(getActivity(), gridData, R.layout.menu_item, 
				/* 简单指明哪一个内容与哪一个界面id对应*/
				temp,tempctrl);
		
		
		//把创建的适配器设置到ListView控件上.
		//gridView.setAdapter(adapter);
	
		//增加对点击控制的监听
		gridView.setOnItemClickListener(new OnItemClickListener(){

		
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				//遍历gridData
			
				//获取选中项
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
						 //以下为对key和value的具体处理
						 //showMessage(getActivity(),"找到商品"+value+    arg3);
						// }
					// }
					
				switch(aaa)
				{
				case 1:
				String str1=name1[(int) arg3];//获取选中项
				InvoiceFragment frag1 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
				
				frag1.inertInvoice1(str1);
				break;
				case 2:
					String str2=name2[(int) arg3];//获取选中项
					InvoiceFragment frag2 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag2.inertInvoice1(str2);
					break;
				case 3:
					String str3=name3[(int) arg3];//获取选中项
					InvoiceFragment frag3 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag3.inertInvoice1(str3);
					break;
				case 4:
					String str4=name4[(int) arg3];//获取选中项
					InvoiceFragment frag4 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag4.inertInvoice1(str4);
					break;
				
				
				case 5:
					String str5=name5[(int) arg3];//获取选中项
					InvoiceFragment frag5 = (InvoiceFragment) getFragmentManager().findFragmentById(R.id.fragment1);
					
					frag5.inertInvoice1(str5);
					break;
				}
				/*	
				int i=str.length();
				StringBuilder builder=new StringBuilder(str);
				int j=str.indexOf(' ');
				
				StringBuilder s1=builder.delete(j,i);
				String s2=s1.toString();//菜品名称
				
				//取菜品名称的价格
				
				StringBuilder builder1=new StringBuilder(str);
				int k=str.indexOf('/');
				StringBuilder s3=builder1.delete(k,i);
				String s4=s3.toString();
				//取价格
			
				//截取s4
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
				
				showMessage(getActivity(),"找到价格"+s9);
				
				HashMap<String,Object> node31 = new HashMap<String,Object>();
				node31.put("name", s2);
				node31.put("price", s9);
				node31.put("unit", "1");
				node31.put("quatity", "份");
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
			   //为每一行按钮设置不同的tag，以便在点击时判断是哪一行。
			   btn.setTag(position);
			   
			   //设置GridView的事件监听器
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