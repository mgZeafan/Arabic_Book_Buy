package com.app.mohamedgomaa.arabic_books.main;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.app.mohamedgomaa.arabic_books.R;
import com.app.mohamedgomaa.arabic_books.model.CheckConnection_Internet;
import com.app.mohamedgomaa.arabic_books.model.item;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
public class MainActivityAdapter extends BaseAdapter{
    private ArrayList<item> myList;
    private IMainView context;
    public MainActivityAdapter(ArrayList<item> myList, IMainView myCon) {
        this.myList = myList;
        this.context = myCon;
    }
    ProgressDialog progressDialog;
    void Initiazation_PBar()
    {
        progressDialog=new ProgressDialog((Context) context);
        progressDialog.setMessage("جارى التحميل .. ");
        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(true);
        progressDialog.setIndeterminate(true);
    }
    public File mkFolder(String file_name) {
        File  Folder =new File("sdcard/ArabicBook");
        if(!Folder.exists())
        {
            Folder.mkdir();
        }
        File file=new File(Folder,file_name);
        return file;
    }
    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup viewGroup)
    {
        View view=v;
        if(v==null) {
            view=LayoutInflater.from((Context) context).inflate(R.layout.album_card, null);
        }
        final int pos=position;
        ImageView img;
        TextView txtTitle;
        com.borjabravo.readmoretextview.ReadMoreTextView txtDetails;
        TextView txtPrice;
        TextView txtAuthor;
        Button rvw,dwn;
        img =  view.findViewById(R.id.imgbook);
        txtTitle =  view.findViewById(R.id.titleName);
        txtDetails = view.findViewById(R.id.details);
        txtPrice =  view.findViewById(R.id.price);
        txtAuthor =  view.findViewById(R.id.author);
        rvw =  view.findViewById(R.id.review);
        dwn=view.findViewById(R.id.download);
        Picasso.with((Context) context).load(myList.get(position).pth_photo).error(R.drawable.file_wait).into(img);
        txtPrice.setText(txtPrice.getText()+ String.valueOf(myList.get(position).price));
        if(Locale.getDefault().getLanguage().equals("ar")) {
            txtDetails.setText(myList.get(position).details_ar);
            txtTitle.setText(myList.get(position).title_ar);
            txtAuthor.setText(myList.get(position).author_ar);
        }else {
            txtDetails.setText(myList.get(position).details_en);
            txtTitle.setText(myList.get(position).title_en);
            txtAuthor.setText(myList.get(position).author_en);
        }
        dwn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.SendRequestToPresentet(myList.get(pos).book_id);
            }
        });
        rvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new CheckConnection_Internet((Context) context).IsConnection()) {
                    File file=mkFolder("Review_" + pos + ".pdf");
                    if(!file.exists()) {
                        final Download_file download_file = new Download_file((Context) context,file , pos);
                        Initiazation_PBar();
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                download_file.cancel(true);
                            }
                        });
                        download_file.execute(myList.get(pos).pth_review);
                    }else {
                        OpenFile(pos);
                    }
                }
            }
        });
        return view;
    }
    void OpenFile(int position)
    {
        try {
            File file = mkFolder("Review_" + position + ".pdf");
            Uri path = Uri.fromFile(file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }catch (Exception e)
        {
            context.ShowMsg("download Adobe Reader");
        }
    }
    private class  Download_file extends AsyncTask<String,Integer,String>
    {
        int position;
        private Context con;
        private File _file;
        PowerManager.WakeLock myWakeLock;
        public Download_file(Context con,File file,int pos)
        {
            this._file=file;
            this.con = con;
            this.position=pos;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            PowerManager pm= (PowerManager) con.getSystemService(con.POWER_SERVICE);
            myWakeLock=pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,getClass().getName());
            myWakeLock.acquire();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("ok")) {
                OpenFile(position);
                progressDialog.dismiss();

        }
        }
        @Override
        protected String doInBackground(String... params) {

            InputStream inputStream=null;
            OutputStream outputStream=null;
            HttpURLConnection connection=null;
            try {
                URL url=new URL(params[0]);
                connection=(HttpURLConnection)url.openConnection();
                connection.connect();
                if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK)
                {
                    return "Server return hTTP "+connection.getResponseCode()+""+connection.getResponseMessage();
                }
                int fileLenght=connection.getContentLength();
                inputStream=connection.getInputStream();
                outputStream=new FileOutputStream(_file);
                byte[]data=new byte[500];
                long total=0;
                int count;
                while ((count=inputStream.read(data))!=-1)
                {
                    total+=count;
                    if(fileLenght>0)
                    {
                        publishProgress((int)(total*100/fileLenght));
                        outputStream.write(data,0,count);
                    }
                }



            } catch (Exception e) {
                return e.getMessage();
            } finally {

                try {
                    if(outputStream!=null)
                        outputStream.close();
                    if (inputStream!=null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(connection!=null)
                {
                    connection.disconnect();
                }


            }
            return "ok";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgress(values[0]);
        }
    }

}
