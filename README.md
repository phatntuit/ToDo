# ToDo
Đây là project mình làm Android đầu tiên

TODO
30-10-2016 Bat dau customize Appdapter

         activity 1 -> activity 2
         
         * Tại activity 1
         
         * //Tạo Intent để mở ResultActivity
         
         
         Intent myIntent=new Intent(MainActivity.this, ResultActivity.class);
         
         //Khai báo Bundle
         
         Bundle bundle=new Bundle();
         
         int a=Integer.parseInt(txta.getText().toString());
         
         int b=Integer.parseInt(txtb.getText().toString());
         
         //đưa dữ liệu riêng lẻ vào Bundle
         
         bundle.putInt("soa", a);
         
         bundle.putInt("sob", b);
         
         //Đưa Bundle vào Intent
         
         myIntent.putExtra("MyPackage", bundle);
         
         //Mở Activity ResultActivity
         
         startActivity(myIntent);
         
         */
         
        /**
        
        
         * Tại activity 2
         
         * //lấy intent gọi Activity này
         
         Intent callerIntent=getIntent();
         
         //có intent rồi thì lấy Bundle dựa vào MyPackage
         
         Bundle packageFromCaller=
         callerIntent.getBundleExtra("MyPackage");
         
         //Có Bundle rồi thì lấy các thông số dựa vào soa, sob
         
         int a=packageFromCaller.getInt("soa");
         
         int b=packageFromCaller.getInt("sob"); 
